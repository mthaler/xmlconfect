package com.mthaler.xmlconfect

import scala.xml.{ Node, Text }

object BasicTextFormats {

  implicit object BooleanXmlTextFormat extends SimpleXmlTextFormat[Boolean] {
    protected def readText(text: Text, name: String = ""): Boolean = text.text.toBoolean
  }

  implicit object ByteXmlTextFormat extends SimpleXmlTextFormat[Byte] {
    protected def readText(text: Text, name: String = ""): Byte = text.text.toByte
  }

  implicit object ShortXmlTextFormat extends SimpleXmlTextFormat[Short] {
    protected def readText(text: Text, name: String = ""): Short = text.text.toShort
  }

  implicit object IntXmlTextFormat extends SimpleXmlTextFormat[Int] {
    protected def readText(text: Text, name: String = ""): Int = text.text.toInt
  }

  implicit object LongXmlTextFormat extends SimpleXmlTextFormat[Long] {
    protected def readText(text: Text, name: String = ""): Long = text.text.toLong
  }

  implicit object FloatXmlTextFormat extends SimpleXmlTextFormat[Float] {
    protected def readText(text: Text, name: String = ""): Float = text.text.toFloat
  }

  implicit object DoubleXmlTextFormat extends SimpleXmlTextFormat[Double] {
    protected def readText(text: Text, name: String = ""): Double = text.text.toDouble
  }

  implicit object StringXmlTextFormat extends SimpleXmlTextFormat[String] {
    protected def readText(text: Text, name: String = ""): String = text.text
  }

  implicit object CharXmlTextFormat extends SimpleXmlTextFormat[Char] {
    protected def readText(text: Text, name: String = ""): Char = {
      val txt = text.text
      if (txt.length == 1) txt.charAt(0) else deserializationError("Expected Char as single-character string, but got " + txt)
    }
  }
}
