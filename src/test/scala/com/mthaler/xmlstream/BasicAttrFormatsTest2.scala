package com.mthaler.xmlstream

import BasicAttrFormats._
import org.scalatest.FunSuite
import scala.xml.{Null, Text, Attribute}

class BasicAttrFormatsTest2 extends FunSuite {

  test("boolean") {
    val result0 = BooleanXmlAttrFormat.read(Right(Attribute("value", Text("true"), Null)), "value")
    assert(true, result0)
    val result1 = BooleanXmlAttrFormat.write(true, "value")
    assert(Right(Attribute("value", Text("true"), Null)) == result1)
  }


  test("byte") {
    val result0 = ByteXmlAttrFormat.read(Right(Attribute("value", Text("42"), Null)), "value")
    assert(42.toByte == result0)
    val result1 = ByteXmlAttrFormat.write(42.toByte, "value")
    assert(Right(Attribute("value", Text("42"), Null)) == result1)
  }

  test("short") {
    val result0 = ShortXmlAttrFormat.read(Right(Attribute("value", Text("42"), Null)), "value")
    assert(42.toShort == result0)
    val result1 = ShortXmlAttrFormat.write(42.toShort, "value")
    assert(Right(Attribute("value", Text("42"), Null)) == result1)
  }

  test("int") {
    val result0 = IntXmlAttrFormat.read(Right(Attribute("value", Text("42"), Null)), "value")
    assert(42 == result0)
    val result1 = IntXmlAttrFormat.write(42, "value")
    assert(Right(Attribute("value", Text("42"), Null)) == result1)
  }

  test("long") {
    val result0 = LongXmlAttrFormat.read(Right(Attribute("value", Text("42"), Null)), "value")
    assert(42.toLong == result0)
    val result1 = LongXmlAttrFormat.write(42.toLong, "value")
    assert(Right(Attribute("value", Text("42"), Null)) == result1)
  }

  test("float") {
    val result0 = FloatXmlAttrFormat.read(Right(Attribute("value", Text("3.14"), Null)), "value")
    assert(3.14f == result0)
    val result1 = FloatXmlAttrFormat.write(3.14f, "value")
    assert(Right(Attribute("value", Text("3.14"), Null)) == result1)
  }

  test("double") {
    val result0 = DoubleXmlAttrFormat.read(Right(Attribute("value", Text("3.14"), Null)), "value")
    assert(3.14 == result0)
    val result1 = DoubleXmlAttrFormat.write(3.14, "value")
    assert(Right(Attribute("value", Text("3.14"), Null)) == result1)
  }

  test("string") {
    val result0 = StringXmlAttrFormat.read(Right(Attribute("value", Text("42"), Null)), "value")
    assert("42" == result0)
    val result1 = StringXmlAttrFormat.write("42", "value")
    assert(Right(Attribute("value", Text("42"), Null)) == result1)
  }

  test("char") {
    val result0 = CharXmlAttrFormat.read(Right(Attribute("value", Text("c"), Null)), "value")
    assert('c' == result0)
    val result1 = CharXmlAttrFormat.write('c', "value")
    assert(Right(Attribute("value", Text("c"), Null)) == result1)
  }

  test("symbol") {
    val result0 = SymbolXmlAttrFormat.read(Right(Attribute("value", Text("symbol"), Null)), "value")
    assert('symbol == result0)
    val result1 = SymbolXmlAttrFormat.write('symbol, "value")
    assert(Right(Attribute("value", Text("symbol"), Null)) == result1)
  }

  test("bigint") {
    val result0 = BigIntXmlAttrFormat.read(Right(Attribute("value", Text("1234567891234567891234567890"), Null)), "value")
    assert(BigInt("1234567891234567891234567890") == result0)
    val result1 = BigIntXmlAttrFormat.write(BigInt("1234567891234567891234567890"), "value")
    assert(Right(Attribute("value", Text("1234567891234567891234567890"), Null)) == result1)
  }

  test("bigdecimal") {
    val result0 = BigDecimalXmlAttrFormat.read(Right(Attribute("value", Text("1234567891234567891234567890.123456789"), Null)), "value")
    assert(BigDecimal("1234567891234567891234567890.123456789") == result0)
    val result1 = BigDecimalXmlAttrFormat.write(BigDecimal("1234567891234567891234567890.123456789"), "value")
    assert(Right(Attribute("value", Text("1234567891234567891234567890.123456789"), Null)) == result1)
  }
}
