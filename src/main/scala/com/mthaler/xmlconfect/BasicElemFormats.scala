package com.mthaler.xmlconfect

import scala.xml.{ Node, Null, Text }

/**
 * Provides the XmlElemFormats for the most important Scala types.
 */
object BasicElemFormats {

  implicit object BooleanXmlElemFormat extends XmlElemFormat[Boolean] {
    protected def readElem(node: Node, name: String = ""): Boolean = node.text.toBoolean
    def write(value: Boolean, name: String = "") = elem(name, Null, Seq(Text(value.toString)))
  }

  implicit object ByteXmlElemFormat extends XmlElemFormat[Byte] {
    protected def readElem(node: Node, name: String = ""): Byte = node.text.toByte
    def write(value: Byte, name: String = "") = elem(name, Null, Seq(Text(value.toString)))
  }

  implicit object ShortXmlElemFormat extends XmlElemFormat[Short] {
    protected def readElem(node: Node, name: String = ""): Short = node.text.toShort
    def write(value: Short, name: String = "") = elem(name, Null, Seq(Text(value.toString)))
  }

  implicit object IntXmlElemFormat extends XmlElemFormat[Int] {
    protected def readElem(node: Node, name: String = ""): Int = node.text.toInt
    def write(value: Int, name: String = "") = elem(name, Null, Seq(Text(value.toString)))
  }

  implicit object LongXmlElemFormat extends XmlElemFormat[Long] {
    protected def readElem(node: Node, name: String = ""): Long = node.text.toLong
    def write(value: Long, name: String = "") = elem(name, Null, Seq(Text(value.toString)))
  }

  implicit object FloatXmlElemFormat extends XmlElemFormat[Float] {
    protected def readElem(node: Node, name: String = ""): Float = node.text.toFloat
    def write(value: Float, name: String = "") = elem(name, Null, Seq(Text(value.toString)))
  }

  implicit object DoubleXmlElemFormat extends XmlElemFormat[Double] {
    protected def readElem(node: Node, name: String = ""): Double = node.text.toDouble
    def write(value: Double, name: String = "") = elem(name, Null, Seq(Text(value.toString)))
  }

  implicit object StringXmlElemFormat extends XmlElemFormat[String] {
    protected def readElem(node: Node, name: String = ""): String = node.text
    def write(value: String, name: String = "") = elem(name, Null, Seq(Text(value.toString)))
  }

  implicit object CharXmlElemFormat extends XmlElemFormat[Char] {
    protected def readElem(node: Node, name: String = ""): Char = {
      val txt = node.text
      if (txt.length == 1) txt.charAt(0) else deserializationError("Expected Char as single-character string, but got " + txt)
    }
    def write(value: Char, name: String = "") = elem(name, Null, Seq(Text(value.toString)))
  }

  implicit object SymbolXmlElemFormat extends XmlElemFormat[Symbol] {
    protected def readElem(node: Node, name: String = ""): Symbol = Symbol(node.text)
    def write(value: Symbol, name: String = "") = elem(name, Null, Seq(Text(value.name)))
  }

  implicit object BigIntXmlElemFormat extends XmlElemFormat[BigInt] {
    protected def readElem(node: Node, name: String = ""): BigInt = BigInt(node.text)
    def write(value: BigInt, name: String = "") = elem(name, Null, Seq(Text(value.toString)))
  }

  implicit object BigDecimalXmlElemFormat extends XmlElemFormat[BigDecimal] {
    protected def readElem(node: Node, name: String = ""): BigDecimal = BigDecimal(node.text)
    def write(value: BigDecimal, name: String = "") = elem(name, Null, Seq(Text(value.toString)))
  }
}
