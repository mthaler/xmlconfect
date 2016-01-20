package com.mthaler.xmlconfect

import scala.reflect._
import scala.xml.{ Null, Node, Text }

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

  implicit object SymbolXmlTextFormat extends SimpleXmlTextFormat[Symbol] {
    protected def readText(text: Text, name: String = ""): Symbol = Symbol(text.text)
    protected override def writeElem(obj: Symbol, name: String = ""): Node = Text(obj.name)
  }

  implicit object BigIntXmlTextFormat extends SimpleXmlTextFormat[BigInt] {
    protected def readText(text: Text, name: String = ""): BigInt = BigInt(text.text)
  }

  implicit object BigDecimalXmlTextFormat extends SimpleXmlTextFormat[BigDecimal] {
    protected def readText(text: Text, name: String = ""): BigDecimal = BigDecimal(text.text)
  }

  implicit def enumFormat[T <: Enum[T]: ClassTag] = new SimpleXmlTextFormat[T] {
    protected def readText(text: Text, name: String = ""): T = {
      val c = classTag[T].runtimeClass.asInstanceOf[Class[T]]
      Enum.valueOf(c, text.text)
    }
  }
}
