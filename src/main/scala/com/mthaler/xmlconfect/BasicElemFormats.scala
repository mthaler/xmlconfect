package com.mthaler.xmlconfect

import scala.xml.{ Node, Null, Text }

/**
 * Provides the XmlElemFormats for the most important Scala types.
 */
object BasicElemFormats {

  implicit object BooleanXmlElemFormat extends XmlElemFormat[Boolean] {
    protected def readElem(node: Node, name: String = ""): Boolean = node.text.toBoolean
  }

  implicit object ByteXmlElemFormat extends XmlElemFormat[Byte] {
    protected def readElem(node: Node, name: String = ""): Byte = node.text.toByte
  }

  implicit object ShortXmlElemFormat extends XmlElemFormat[Short] {
    protected def readElem(node: Node, name: String = ""): Short = node.text.toShort
  }

  implicit object IntXmlElemFormat extends XmlElemFormat[Int] {
    protected def readElem(node: Node, name: String = ""): Int = node.text.toInt
  }

  implicit object LongXmlElemFormat extends XmlElemFormat[Long] {
    protected def readElem(node: Node, name: String = ""): Long = node.text.toLong
  }

  implicit object FloatXmlElemFormat extends XmlElemFormat[Float] {
    protected def readElem(node: Node, name: String = ""): Float = node.text.toFloat
  }

  implicit object DoubleXmlElemFormat extends XmlElemFormat[Double] {
    protected def readElem(node: Node, name: String = ""): Double = node.text.toDouble
  }

  implicit object StringXmlElemFormat extends XmlElemFormat[String] {
    protected def readElem(node: Node, name: String = ""): String = node.text
  }

  implicit object CharXmlElemFormat extends XmlElemFormat[Char] {
    protected def readElem(node: Node, name: String = ""): Char = {
      val txt = node.text
      if (txt.length == 1) txt.charAt(0) else deserializationError("Expected Char as single-character string, but got " + txt)
    }
  }

  implicit object SymbolXmlElemFormat extends XmlElemFormat[Symbol] {
    protected def readElem(node: Node, name: String = ""): Symbol = Symbol(node.text)
    protected override def writeElem(obj: Symbol, name: String = ""): Node = elem(name, Null, Seq(Text(obj.name)))
  }

  implicit object BigIntXmlElemFormat extends XmlElemFormat[BigInt] {
    protected def readElem(node: Node, name: String = ""): BigInt = BigInt(node.text)
  }

  implicit object BigDecimalXmlElemFormat extends XmlElemFormat[BigDecimal] {
    protected def readElem(node: Node, name: String = ""): BigDecimal = BigDecimal(node.text)
  }
}
