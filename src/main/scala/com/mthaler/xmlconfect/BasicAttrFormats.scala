package com.mthaler.xmlconfect

/**
 * Provides the XmlAttrFormats for the most important Scala types.
 */
object BasicAttrFormats {

  implicit object BooleanXmlAttrFormat extends XmlAttrFormat[Boolean] {
    def read(xml: XML, name: String = ""): Boolean = xml match {
      case Left(node) => deserializationError("Reading nodes not supported")
      case Right(metaData) => metaData(name).text.toBoolean
    }
    def write(value: Boolean, name: String = "") = attribute(name, value.toString)
  }

  implicit object ByteXmlAttrFormat extends XmlAttrFormat[Byte] {
    def read(xml: XML, name: String = ""): Byte = xml match {
      case Left(node) => deserializationError("Reading nodes not supported")
      case Right(metaData) => metaData(name).text.toByte
    }
    def write(value: Byte, name: String = "") = attribute(name, value.toString)
  }

  implicit object ShortXmlAttrFormat extends XmlAttrFormat[Short] {
    def read(xml: XML, name: String): Short = xml match {
      case Left(node) => deserializationError("Reading nodes not supported")
      case Right(metaData) => metaData(name).text.toShort
    }
    def write(value: Short, name: String = "") = attribute(name, value.toString)
  }

  implicit object IntXmlAttrFormat extends XmlAttrFormat[Int] {
    def read(xml: XML, name: String = ""): Int = xml match {
      case Left(node) => deserializationError("Reading nodes not supported")
      case Right(metaData) => metaData(name).text.toInt
    }
    def write(value: Int, name: String = "") = attribute(name, value.toString)
  }

  implicit object LongXmlAttrFormat extends XmlAttrFormat[Long] {
    def read(xml: XML, name: String = ""): Long = xml match {
      case Left(node) => deserializationError("Reading nodes not supported")
      case Right(metaData) => metaData(name).text.toLong
    }
    def write(value: Long, name: String = "") = attribute(name, value.toString)
  }

  implicit object FloatXmlAttrFormat extends XmlAttrFormat[Float] {
    def read(xml: XML, name: String = ""): Float = xml match {
      case Left(node) => deserializationError("Reading nodes not supported")
      case Right(metaData) => metaData(name).text.toFloat
    }
    def write(value: Float, name: String = "") = attribute(name, value.toString)
  }

  implicit object DoubleXmlAttrFormat extends XmlAttrFormat[Double] {
    def read(xml: XML, name: String = ""): Double = xml match {
      case Left(node) => deserializationError("Reading nodes not supported")
      case Right(metaData) => metaData(name).text.toDouble
    }
    def write(value: Double, name: String = "") = attribute(name, value.toString)
  }

  implicit object StringXmlAttrFormat extends XmlAttrFormat[String] {
    def read(xml: XML, name: String = ""): String = xml match {
      case Left(node) => deserializationError("Reading nodes not supported")
      case Right(metaData) => metaData(name).text
    }
    def write(value: String, name: String = "") = attribute(name, value.toString)
  }

  implicit object CharXmlAttrFormat extends XmlAttrFormat[Char] {
    def read(xml: XML, name: String = ""): Char = xml match {
      case Left(node) => deserializationError("Reading nodes not supported")
      case Right(metaData) =>
        val txt = metaData(name).text
        if (txt.length == 1) txt.charAt(0) else deserializationError("Expected Char as single-character string, but got " + txt)
    }
    def write(value: Char, name: String = "") = attribute(name, value.toString)
  }

  implicit object BigIntXmlAttrFormat extends XmlAttrFormat[BigInt] {
    def read(xml: XML, name: String = ""): BigInt = xml match {
      case Left(node) => deserializationError("Reading nodes not supported")
      case Right(metaData) => BigInt(metaData(name).text)
    }
    def write(value: BigInt, name: String = "") = attribute(name, value.toString)
  }

  implicit object BigDecimalXmlAttrFormat extends XmlAttrFormat[BigDecimal] {
    def read(xml: XML, name: String = ""): BigDecimal = xml match {
      case Left(node) => deserializationError("Reading nodes not supported")
      case Right(metaData) => BigDecimal(metaData(name).text)
    }
    def write(value: BigDecimal, name: String = "") = attribute(name, value.toString)
  }

  implicit object SymbolXmlAttrFormat extends XmlAttrFormat[Symbol] {
    def read(xml: XML, name: String = ""): Symbol = xml match {
      case Left(node) => deserializationError("Reading nodes not supported")
      case Right(metaData) => Symbol(metaData(name).text)
    }
    def write(value: Symbol, name: String = "") = attribute(name, value.name)
  }
}
