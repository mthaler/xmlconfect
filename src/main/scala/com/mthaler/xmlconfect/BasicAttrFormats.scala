package com.mthaler.xmlconfect

import scala.reflect._
import scala.xml.MetaData

/**
 * Provides the XmlAttrFormats for the most important Scala types.
 */
object BasicAttrFormats {

  implicit object BooleanXmlAttrFormat extends XmlAttrFormat[Boolean] {
    protected def readAttr(metaData: MetaData, name: String = ""): Boolean = metaData(name).text.toBoolean
  }

  implicit object ByteXmlAttrFormat extends XmlAttrFormat[Byte] {
    protected def readAttr(metaData: MetaData, name: String = ""): Byte = metaData(name).text.toByte
  }

  implicit object ShortXmlAttrFormat extends XmlAttrFormat[Short] {
    protected def readAttr(metaData: MetaData, name: String = ""): Short = metaData(name).text.toShort
  }

  implicit object IntXmlAttrFormat extends XmlAttrFormat[Int] {
    protected def readAttr(metaData: MetaData, name: String = ""): Int = metaData(name).text.toInt
  }

  implicit object LongXmlAttrFormat extends XmlAttrFormat[Long] {
    protected def readAttr(metaData: MetaData, name: String = ""): Long = metaData(name).text.toLong
  }

  implicit object FloatXmlAttrFormat extends XmlAttrFormat[Float] {
    protected def readAttr(metaData: MetaData, name: String = ""): Float = metaData(name).text.toFloat
  }

  implicit object DoubleXmlAttrFormat extends XmlAttrFormat[Double] {
    protected def readAttr(metaData: MetaData, name: String = ""): Double = metaData(name).text.toDouble
  }

  implicit object StringXmlAttrFormat extends XmlAttrFormat[String] {
    protected def readAttr(metaData: MetaData, name: String = ""): String = metaData(name).text
  }

  implicit object CharXmlAttrFormat extends XmlAttrFormat[Char] {
    protected def readAttr(metaData: MetaData, name: String = ""): Char = {
      val txt = metaData(name).text
      if (txt.length == 1) txt.charAt(0) else deserializationError("Expected Char as single-character string, but got " + txt)
    }
  }

  implicit object BigIntXmlAttrFormat extends XmlAttrFormat[BigInt] {
    protected def readAttr(metaData: MetaData, name: String = ""): BigInt = BigInt(metaData(name).text)
  }

  implicit object BigDecimalXmlAttrFormat extends XmlAttrFormat[BigDecimal] {
    protected def readAttr(metaData: MetaData, name: String = ""): BigDecimal = BigDecimal(metaData(name).text)
  }

  implicit object SymbolXmlAttrFormat extends XmlAttrFormat[Symbol] {
    protected def readAttr(metaData: MetaData, name: String = ""): Symbol = Symbol(metaData(name).text)
    protected override def writeAttr(obj: Symbol, name: String = ""): MetaData = attribute(name, obj.name)
  }

  implicit def enumFormat[T <: Enum[T]: ClassTag] = new XmlAttrFormat[T] {
    protected def readAttr(metaData: MetaData, name: String = ""): T = {
      val c = classTag[T].runtimeClass.asInstanceOf[Class[T]]
      Enum.valueOf(c, metaData(name).text)
    }
  }
}
