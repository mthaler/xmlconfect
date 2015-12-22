package com.mthaler.xmlconfect

import scala.xml.{ Node, Null, Text }

/**
 * Provides the XmlElemFormats for the most important Scala types.
 */
object BasicElemFormats {

  implicit object BooleanXmlElemFormat extends SimpleXmlElemFormat[Boolean] {
    protected def readElem(node: Node, name: String = ""): Boolean = node.text.toBoolean
  }

  implicit object ByteXmlElemFormat extends SimpleXmlElemFormat[Byte] {
    protected def readElem(node: Node, name: String = ""): Byte = node.text.toByte
  }

  implicit object ShortXmlElemFormat extends SimpleXmlElemFormat[Short] {
    protected def readElem(node: Node, name: String = ""): Short = node.text.toShort
  }

  implicit object IntXmlElemFormat extends SimpleXmlElemFormat[Int] {
    protected def readElem(node: Node, name: String = ""): Int = node.text.toInt
  }

  implicit object LongXmlElemFormat extends SimpleXmlElemFormat[Long] {
    protected def readElem(node: Node, name: String = ""): Long = node.text.toLong
  }

  implicit object FloatXmlElemFormat extends SimpleXmlElemFormat[Float] {
    protected def readElem(node: Node, name: String = ""): Float = node.text.toFloat
  }

  implicit object DoubleXmlElemFormat extends SimpleXmlElemFormat[Double] {
    protected def readElem(node: Node, name: String = ""): Double = node.text.toDouble
  }

  implicit object StringXmlElemFormat extends SimpleXmlElemFormat[String] {
    protected def readElem(node: Node, name: String = ""): String = node.text
  }

  implicit object CharXmlElemFormat extends SimpleXmlElemFormat[Char] {
    protected def readElem(node: Node, name: String = ""): Char = {
      val txt = node.text
      if (txt.length == 1) txt.charAt(0) else deserializationError("Expected Char as single-character string, but got " + txt)
    }
  }

  implicit object SymbolXmlElemFormat extends SimpleXmlElemFormat[Symbol] {
    protected def readElem(node: Node, name: String = ""): Symbol = Symbol(node.text)
    protected override def writeElem(obj: Symbol, name: String = ""): Node = elem(name, Null, Seq(Text(obj.name)))
  }

  implicit object BigIntXmlElemFormat extends SimpleXmlElemFormat[BigInt] {
    protected def readElem(node: Node, name: String = ""): BigInt = BigInt(node.text)
  }

  implicit object BigDecimalXmlElemFormat extends SimpleXmlElemFormat[BigDecimal] {
    protected def readElem(node: Node, name: String = ""): BigDecimal = BigDecimal(node.text)
  }
}
