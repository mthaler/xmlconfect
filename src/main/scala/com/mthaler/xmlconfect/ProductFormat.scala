package com.mthaler.xmlconfect

import scala.annotation.tailrec
import scala.reflect.{ ClassTag, classTag }
import scala.util.control.NonFatal
import scala.xml.{ MetaData, Node, Null }

/**
 * Provides the helpers for constructing custom XmlFormat implementations for types implementing the Product trait
 * (especially case classes)
 */
object ProductFormat {

  type XF[T] = XmlFormat[T] // simple alias for reduced verbosity

  def extractFieldNames(classManifest: ClassTag[_]): Array[String] = {
    val clazz = classManifest.runtimeClass
    try {
      // copy methods have the form copy$default$N(), we need to sort them in order, but must account for the fact
      // that lexical sorting of ...8(), ...9(), ...10() is not correct, so we extract N and sort by N.toInt
      val copyDefaultMethods = clazz.getMethods.filter(_.getName.startsWith("copy$default$")).sortBy(
        _.getName.drop("copy$default$".length).takeWhile(_ != '(').toInt
      )
      val fields = clazz.getDeclaredFields.filterNot { f =>
        import java.lang.reflect.Modifier._
        (f.getModifiers & (TRANSIENT | STATIC | 0x1000 /* SYNTHETIC*/ )) > 0
      }
      if (copyDefaultMethods.length != fields.length)
        sys.error("Case class " + clazz.getName + " declares additional fields")
      if (fields.zip(copyDefaultMethods).exists { case (f, m) => f.getType != m.getReturnType })
        sys.error("Cannot determine field order of case class " + clazz.getName)
      fields.map(f => ProductFormat.unmangle(f.getName))
    } catch {
      case NonFatal(ex) => throw new RuntimeException("Cannot automatically determine case class field names and order " +
        "for '" + clazz.getName + "', please use the 'jsonFormat' overload with explicit field name specification", ex)
    }
  }

  private def unmangle(name: String) = {
    import java.lang.{ StringBuilder => JStringBuilder }
    @tailrec def rec(ix: Int, builder: JStringBuilder): String = {
      val rem = name.length - ix
      if (rem > 0) {
        var ch = name.charAt(ix)
        var ni = ix + 1
        val sb = if (ch == '$' && rem > 1) {
          def c(offset: Int, ch: Char) = name.charAt(ix + offset) == ch
          ni = name.charAt(ix + 1) match {
            case 'a' if rem > 3 && c(2, 'm') && c(3, 'p') => { ch = '&'; ix + 4 }
            case 'a' if rem > 2 && c(2, 't') => { ch = '@'; ix + 3 }
            case 'b' if rem > 4 && c(2, 'a') && c(3, 'n') && c(4, 'g') => { ch = '!'; ix + 5 }
            case 'b' if rem > 3 && c(2, 'a') && c(3, 'r') => { ch = '|'; ix + 4 }
            case 'd' if rem > 3 && c(2, 'i') && c(3, 'v') => { ch = '/'; ix + 4 }
            case 'e' if rem > 2 && c(2, 'q') => { ch = '='; ix + 3 }
            case 'g' if rem > 7 && c(2, 'r') && c(3, 'e') && c(4, 'a') && c(5, 't') && c(6, 'e') && c(7, 'r') => { ch = '>'; ix + 8 }
            case 'h' if rem > 4 && c(2, 'a') && c(3, 's') && c(4, 'h') => { ch = '#'; ix + 5 }
            case 'l' if rem > 4 && c(2, 'e') && c(3, 's') && c(4, 's') => { ch = '<'; ix + 5 }
            case 'm' if rem > 5 && c(2, 'i') && c(3, 'n') && c(4, 'u') && c(5, 's') => { ch = '-'; ix + 6 }
            case 'p' if rem > 7 && c(2, 'e') && c(3, 'r') && c(4, 'c') && c(5, 'e') && c(6, 'n') && c(7, 't') => { ch = '%'; ix + 8 }
            case 'p' if rem > 4 && c(2, 'l') && c(3, 'u') && c(4, 's') => { ch = '+'; ix + 5 }
            case 'q' if rem > 5 && c(2, 'm') && c(3, 'a') && c(4, 'r') && c(5, 'k') => { ch = '?'; ix + 6 }
            case 't' if rem > 5 && c(2, 'i') && c(3, 'l') && c(4, 'd') && c(5, 'e') => { ch = '~'; ix + 6 }
            case 't' if rem > 5 && c(2, 'i') && c(3, 'm') && c(4, 'e') && c(5, 's') => { ch = '*'; ix + 6 }
            case 'u' if rem > 2 && c(2, 'p') => { ch = '^'; ix + 3 }
            case 'u' if rem > 5 =>
              def hexValue(offset: Int): Int = {
                val c = name.charAt(ix + offset)
                if ('0' <= c && c <= '9') c - '0'
                else if ('a' <= c && c <= 'f') c - 87
                else if ('A' <= c && c <= 'F') c - 55 else -0xFFFF
              }
              val ci = (hexValue(2) << 12) + (hexValue(3) << 8) + (hexValue(4) << 4) + hexValue(5)
              if (ci >= 0) { ch = ci.toChar; ix + 6 } else ni
            case _ => ni
          }
          if (ni > ix + 1 && builder == null) new JStringBuilder(name.substring(0, ix)) else builder
        } else builder
        rec(ni, if (sb != null) sb.append(ch) else null)
      } else if (builder != null) builder.toString else name
    }
    rec(0, null)
  }

  def productElement2Field[T](fieldName: String, p: Product, ix: Int, rest: List[XML] = Nil)(implicit writer: XmlWriter[T]): List[XML] = {
    val value = p.productElement(ix).asInstanceOf[T]
    writer match {
      //case _: OptionFormat[_] if (value == None) => rest
      case _ => writer.write(value, fieldName) :: rest
    }
  }

  def fromField[T](node: Node, fieldName: String, defaultValue: Option[T] = None)(implicit reader: XmlReader[T]): T = reader match {
    case root: XmlElemReader[T] =>
      val elem = Left(TNode(node, n => (n \ fieldName).head))
      reader.read(elem, fieldName)
    case _ =>
      try {
        val attr = Right(node.attributes)
        reader.read(attr, fieldName)
      } catch {
        case ex: Exception =>
          defaultValue match {
            case Some(v) => v
            case None => deserializationError("Could not read attribute " + fieldName, ex)
          }
      }
  }

  def metaData(fields: Seq[XML]): MetaData = {
    val metaDataList = fields.collect { case Right(metaData) => metaData }
    if (metaDataList.isEmpty) Null else metaDataList.reduce((m1, m2) => m1.copy(m2))
  }

  def children(fields: Seq[XML]): Seq[Node] = fields.collect { case Left(node) => node.apply }

  def xmlFormat1[P1: XF, T <: Product: ClassTag](construct: (P1) => T): XmlElemFormat[T] = {
    val Array(p1) = extractFieldNames(classTag[T])
    xmlFormat(construct, p1)
  }
  def xmlFormat[P1: XF, T <: Product: ClassTag](construct: (P1) => T, fieldName1: String): XmlElemFormat[T] = new XmlElemFormat[T] {
    protected override def writeElem(p: T, name: String = "") = {
      val fields = new collection.mutable.ListBuffer[XML]
      fields.sizeHint(1 * 2)
      fields ++= productElement2Field[P1](fieldName1, p, 0)
      elem(if (name.isEmpty) p.productPrefix else name, metaData(fields), children(fields))
    }
    protected def readElem(node: Node, name: String = "") = {
      val defaultArgs = DefaultArgsCache.get(classTag[T].runtimeClass)
      if (defaultArgs.size == 1) {
        val p1V = fromField[P1](node, fieldName1, Some(defaultArgs(0).asInstanceOf[P1]))
        construct(p1V)
      } else {
        val p1V = fromField[P1](node, fieldName1)
        construct(p1V)
      }
    }
  }

  def xmlFormat2[P1: XF, P2: XF, T <: Product: ClassTag](construct: (P1, P2) => T): XmlElemFormat[T] = {
    val Array(p1, p2) = extractFieldNames(classTag[T])
    xmlFormat(construct, p1, p2)
  }
  def xmlFormat[P1: XF, P2: XF, T <: Product: ClassTag](construct: (P1, P2) => T, fieldName1: String, fieldName2: String): XmlElemFormat[T] = new XmlElemFormat[T] {
    protected override def writeElem(p: T, name: String = "") = {
      val fields = new collection.mutable.ListBuffer[XML]
      fields.sizeHint(2 * 3)
      fields ++= productElement2Field[P1](fieldName1, p, 0)
      fields ++= productElement2Field[P2](fieldName2, p, 1)
      elem(if (name.isEmpty) p.productPrefix else name, metaData(fields), children(fields))
    }
    protected def readElem(node: Node, name: String = "") = {
      val defaultArgs = DefaultArgsCache.get(classTag[T].runtimeClass)
      if (defaultArgs.size == 2) {
        val p1V = fromField[P1](node, fieldName1, Some(defaultArgs(0).asInstanceOf[P1]))
        val p2V = fromField[P2](node, fieldName2, Some(defaultArgs(1).asInstanceOf[P2]))
        construct(p1V, p2V)
      } else {
        val p1V = fromField[P1](node, fieldName1)
        val p2V = fromField[P2](node, fieldName2)
        construct(p1V, p2V)
      }
    }
  }
}
