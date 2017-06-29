package com.mthaler.xmlconfect

import com.mthaler.xmlconfect.ProductFormat._
import scala.reflect.{ ClassTag, classTag }
import scala.xml.Node

object CustomProductFormat {

  // just for testing product formats, so we make them package private
  // the actual product formats are generated using boilerplate when building the project
  private[xmlconfect] def xmlFormat1[P1, T <: Product: ClassTag](formats: String => XmlFormat[_], construct: (P1) => T): SimpleXmlElemFormat[T] = {
    val Array(p1) = extractFieldNames(classTag[T])
    xmlFormat(formats, construct, p1)
  }
  private[xmlconfect] def xmlFormat[P1, T <: Product: ClassTag](formats: String => XmlFormat[_], construct: (P1) => T, fieldName1: String): SimpleXmlElemFormat[T] =
    new SimpleXmlElemFormat[T] {

      protected override def writeElem(p: T, name: String = "") = {
        val fields = new collection.mutable.ListBuffer[XML]
        fields.sizeHint(1 * 2)
        fields ++= productElement2Field[P1](fieldName1, p, 0)(formats(fieldName1).asInstanceOf[XmlWriter[P1]])
        elem(if (name.isEmpty) p.productPrefix else name, metaData(fields), children(fields))
      }

      protected def readElem(node: Node, name: String = "") = {
        val defaultArgs = DefaultArgsCache.get(classTag[T].runtimeClass)
        if (defaultArgs.size == 1) {
          val p1V = fromField[P1](node, fieldName1, Some(defaultArgs(0).asInstanceOf[P1]))(formats(fieldName1).asInstanceOf[XmlReader[P1]])
          construct(p1V)
        } else {
          val p1V = fromField[P1](node, fieldName1)(formats(fieldName1).asInstanceOf[XmlReader[P1]])
          construct(p1V)
        }
      }
    }

  private[xmlconfect] def xmlFormat2[P1, P2, T <: Product: ClassTag](formats: String => XmlFormat[_], construct: (P1, P2) => T): SimpleXmlElemFormat[T] = {
    val Array(p1, p2) = extractFieldNames(classTag[T])
    xmlFormat(formats, construct, p1, p2)
  }
  private[xmlconfect] def xmlFormat[P1, P2, T <: Product: ClassTag](formats: String => XmlFormat[_], construct: (P1, P2) => T, fieldName1: String, fieldName2: String): SimpleXmlElemFormat[T] =
    new SimpleXmlElemFormat[T] {

      protected override def writeElem(p: T, name: String = "") = {
        val fields = new collection.mutable.ListBuffer[XML]
        fields.sizeHint(2 * 3)
        fields ++= productElement2Field[P1](fieldName1, p, 0)(formats(fieldName1).asInstanceOf[XmlWriter[P1]])
        fields ++= productElement2Field[P2](fieldName2, p, 1)(formats(fieldName2).asInstanceOf[XmlWriter[P2]])
        elem(if (name.isEmpty) p.productPrefix else name, metaData(fields), children(fields))
      }
      protected def readElem(node: Node, name: String = "") = {
        val defaultArgs = DefaultArgsCache.get(classTag[T].runtimeClass)
        if (defaultArgs.size == 2) {
          val p1V = fromField[P1](node, fieldName1, Some(defaultArgs(0).asInstanceOf[P1]))(formats(fieldName1).asInstanceOf[XmlReader[P1]])
          val p2V = fromField[P2](node, fieldName2, Some(defaultArgs(1).asInstanceOf[P2]))(formats(fieldName2).asInstanceOf[XmlReader[P2]])
          construct(p1V, p2V)
        } else {
          val p1V = fromField[P1](node, fieldName1)(formats(fieldName1).asInstanceOf[XmlReader[P1]])
          val p2V = fromField[P2](node, fieldName2)(formats(fieldName2).asInstanceOf[XmlReader[P2]])
          construct(p1V, p2V)
        }
      }
    }
}
