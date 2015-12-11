package com.mthaler.xmlstream

import scala.xml.{Null, Text}

object BasicElemFormats {

  implicit object BooleanXmlElemFormat extends XmlElemFormat[Boolean] {
    def read(xml: XML, name: String = ""): Boolean = xml match {
      case Left(node) => node.text.toBoolean
      case Right(metaData) => deserializationError("Reading attributes not supported")
    }
    def write(value: Boolean, name: String = "") = elem(name, Null, Seq(Text(value.toString)))
  }

  implicit object ByteXmlElemFormat extends XmlElemFormat[Byte] {
    def read(xml: XML, name: String = ""): Byte = xml match {
      case Left(node) => node.text.toByte
      case Right(metaData) => deserializationError("Reading attributes not supported")
    }
    def write(value: Byte, name: String = "") = elem(name, Null, Seq(Text(value.toString)))
  }

  implicit object ShortXmlElemFormat extends XmlElemFormat[Short] {
    def read(xml: XML, name: String = ""): Short = xml match {
      case Left(node) => node.text.toShort
      case Right(metaData) => deserializationError("Reading attributes not supported")
    }
    def write(value: Short, name: String = "") = elem(name, Null, Seq(Text(value.toString)))
  }

  implicit object IntXmlElemFormat extends XmlElemFormat[Int] {
    def read(xml: XML, name: String = ""): Int = xml match {
      case Left(node) => node.text.toInt
      case Right(metaData) => deserializationError("Reading attributes not supported")
    }
    def write(value: Int, name: String = "") = elem(name, Null, Seq(Text(value.toString)))
  }

  implicit object LongXmlElemFormat extends XmlElemFormat[Long] {
    def read(xml: XML, name: String = ""): Long = xml match {
      case Left(node) => node.text.toLong
      case Right(metaData) => deserializationError("Reading attributes not supported")
    }
    def write(value: Long, name: String = "") = elem(name, Null, Seq(Text(value.toString)))
  }

  implicit object FloatXmlElemFormat extends XmlElemFormat[Float] {
    def read(xml: XML, name: String = ""): Float = xml match {
      case Left(node) => node.text.toFloat
      case Right(metaData) => deserializationError("Reading attributes not supported")
    }
    def write(value: Float, name: String = "") = elem(name, Null, Seq(Text(value.toString)))
  }

  implicit object DoubleXmlElemFormat extends XmlElemFormat[Double] {
    def read(xml: XML, name: String = ""): Double = xml match {
      case Left(node) => node.text.toDouble
      case Right(metaData) => deserializationError("Reading attributes not supported")
    }
    def write(value: Double, name: String = "") = elem(name, Null, Seq(Text(value.toString)))
  }

  implicit object StringXmlElemFormat extends XmlElemFormat[String] {
    def read(xml: XML, name: String = ""): String = xml match {
      case Left(node) => node.text
      case Right(metaData) => deserializationError("Reading attributes not supported")
    }
    def write(value: String, name: String = "") = elem(name, Null, Seq(Text(value.toString)))
  }

  implicit object CharXmlElemFormat extends XmlElemFormat[Char] {
    def read(xml: XML, name: String = ""): Char = xml match {
      case Left(node) =>
        val txt = node.text
        if (txt.length == 1) txt.charAt(0) else deserializationError("Expected Char as single-character string, but got " + txt)
      case Right(metaData) => deserializationError("Reading attributes not supported")
    }
    def write(value: Char, name: String = "") = elem(name, Null, Seq(Text(value.toString)))
  }

  implicit object SymbolXmlElemFormat extends XmlElemFormat[Symbol] {
    def read(xml: XML, name: String = ""): Symbol = xml match {
      case Left(node) => Symbol(node.text)
      case Right(metaData) => deserializationError("Reading attributes not supported")
    }
    def write(value: Symbol, name: String = "") = elem(name, Null, Seq(Text(value.name)))
  }

  implicit object BigIntXmlElemFormat extends XmlElemFormat[BigInt] {
    def read(xml: XML, name: String = ""): BigInt = xml match {
      case Left(node) => BigInt(node.text)
      case Right(metaData) => deserializationError("Reading attributes not supported")
    }
    def write(value: BigInt, name: String = "") = elem(name, Null, Seq(Text(value.toString)))
  }

  implicit object BigDecimalXmlElemFormat extends XmlElemFormat[BigDecimal] {
    def read(xml: XML, name: String = ""): BigDecimal = xml match {
      case Left(node) => BigDecimal(node.text)
      case Right(metaData) => deserializationError("Reading attributes not supported")
    }
    def write(value: BigDecimal, name: String = "") = elem(name, Null, Seq(Text(value.toString)))
  }
}
