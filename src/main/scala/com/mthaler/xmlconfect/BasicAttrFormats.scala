package com.mthaler.xmlconfect

import scala.xml.MetaData

/**
 * Provides the XmlAttrFormats for the most important Scala types.
 */
object BasicAttrFormats {

  implicit object BooleanXmlAttrFormat extends XmlAttrFormat[Boolean] {
    def readAttr(metaData: MetaData, name: String = ""): Boolean = metaData(name).text.toBoolean
    def write(value: Boolean, name: String = "") = attribute(name, value.toString)
  }

  implicit object ByteXmlAttrFormat extends XmlAttrFormat[Byte] {
    def readAttr(metaData: MetaData, name: String = ""): Byte = metaData(name).text.toByte
    def write(value: Byte, name: String = "") = attribute(name, value.toString)
  }

  implicit object ShortXmlAttrFormat extends XmlAttrFormat[Short] {
    def readAttr(metaData: MetaData, name: String = ""): Short = metaData(name).text.toShort
    def write(value: Short, name: String = "") = attribute(name, value.toString)
  }

  implicit object IntXmlAttrFormat extends XmlAttrFormat[Int] {
    def readAttr(metaData: MetaData, name: String = ""): Int = metaData(name).text.toInt
    def write(value: Int, name: String = "") = attribute(name, value.toString)
  }

  implicit object LongXmlAttrFormat extends XmlAttrFormat[Long] {
    def readAttr(metaData: MetaData, name: String = ""): Long = metaData(name).text.toLong
    def write(value: Long, name: String = "") = attribute(name, value.toString)
  }

  implicit object FloatXmlAttrFormat extends XmlAttrFormat[Float] {
    def readAttr(metaData: MetaData, name: String = ""): Float = metaData(name).text.toFloat
    def write(value: Float, name: String = "") = attribute(name, value.toString)
  }

  implicit object DoubleXmlAttrFormat extends XmlAttrFormat[Double] {
    def readAttr(metaData: MetaData, name: String = ""): Double = metaData(name).text.toDouble
    def write(value: Double, name: String = "") = attribute(name, value.toString)
  }

  implicit object StringXmlAttrFormat extends XmlAttrFormat[String] {
    def readAttr(metaData: MetaData, name: String = ""): String = metaData(name).text
    def write(value: String, name: String = "") = attribute(name, value.toString)
  }

  implicit object CharXmlAttrFormat extends XmlAttrFormat[Char] {
    def readAttr(metaData: MetaData, name: String = ""): Char = {
      val txt = metaData(name).text
      if (txt.length == 1) txt.charAt(0) else deserializationError("Expected Char as single-character string, but got " + txt)
    }
    def write(value: Char, name: String = "") = attribute(name, value.toString)
  }

  implicit object BigIntXmlAttrFormat extends XmlAttrFormat[BigInt] {
    def readAttr(metaData: MetaData, name: String = ""): BigInt = BigInt(metaData(name).text)
    def write(value: BigInt, name: String = "") = attribute(name, value.toString)
  }

  implicit object BigDecimalXmlAttrFormat extends XmlAttrFormat[BigDecimal] {
    def readAttr(metaData: MetaData, name: String = ""): BigDecimal = BigDecimal(metaData(name).text)
    def write(value: BigDecimal, name: String = "") = attribute(name, value.toString)
  }

  implicit object SymbolXmlAttrFormat extends XmlAttrFormat[Symbol] {
    def readAttr(metaData: MetaData, name: String = ""): Symbol = Symbol(metaData(name).text)
    def write(value: Symbol, name: String = "") = attribute(name, value.name)
  }
}
