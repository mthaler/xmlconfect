package com.mthaler.xmlconfect

import scala.xml.MetaData

/**
 * Provides the XmlAttrFormats for the most important Scala types.
 */
object BasicAttrFormats {

  implicit object BooleanXmlAttrFormat extends XmlAttrFormat[Boolean] {
    def readAttr(metaData: MetaData, name: String = ""): Boolean = metaData(name).text.toBoolean
  }

  implicit object ByteXmlAttrFormat extends XmlAttrFormat[Byte] {
    def readAttr(metaData: MetaData, name: String = ""): Byte = metaData(name).text.toByte
  }

  implicit object ShortXmlAttrFormat extends XmlAttrFormat[Short] {
    def readAttr(metaData: MetaData, name: String = ""): Short = metaData(name).text.toShort
  }

  implicit object IntXmlAttrFormat extends XmlAttrFormat[Int] {
    def readAttr(metaData: MetaData, name: String = ""): Int = metaData(name).text.toInt
  }

  implicit object LongXmlAttrFormat extends XmlAttrFormat[Long] {
    def readAttr(metaData: MetaData, name: String = ""): Long = metaData(name).text.toLong
  }

  implicit object FloatXmlAttrFormat extends XmlAttrFormat[Float] {
    def readAttr(metaData: MetaData, name: String = ""): Float = metaData(name).text.toFloat
  }

  implicit object DoubleXmlAttrFormat extends XmlAttrFormat[Double] {
    def readAttr(metaData: MetaData, name: String = ""): Double = metaData(name).text.toDouble
  }

  implicit object StringXmlAttrFormat extends XmlAttrFormat[String] {
    def readAttr(metaData: MetaData, name: String = ""): String = metaData(name).text
  }

  implicit object CharXmlAttrFormat extends XmlAttrFormat[Char] {
    def readAttr(metaData: MetaData, name: String = ""): Char = {
      val txt = metaData(name).text
      if (txt.length == 1) txt.charAt(0) else deserializationError("Expected Char as single-character string, but got " + txt)
    }
  }

  implicit object BigIntXmlAttrFormat extends XmlAttrFormat[BigInt] {
    def readAttr(metaData: MetaData, name: String = ""): BigInt = BigInt(metaData(name).text)
  }

  implicit object BigDecimalXmlAttrFormat extends XmlAttrFormat[BigDecimal] {
    def readAttr(metaData: MetaData, name: String = ""): BigDecimal = BigDecimal(metaData(name).text)
  }

  implicit object SymbolXmlAttrFormat extends XmlAttrFormat[Symbol] {
    def readAttr(metaData: MetaData, name: String = ""): Symbol = Symbol(metaData(name).text)
    protected override def writeAttr(obj: Symbol, name: String = ""): MetaData = attribute(name, obj.name)
  }
}
