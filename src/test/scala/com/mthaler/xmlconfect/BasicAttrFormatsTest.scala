package com.mthaler.xmlconfect

import BasicAttrFormats._
import org.scalatest.FunSuite
import scala.xml.{ Null, Text, Attribute }

class BasicAttrFormatsTest extends FunSuite {

  test("boolean") {
    assertResult(true) {
      BooleanXmlAttrFormat.read(Right(Attribute("value", Text("true"), Null)), "value")
    }
    assertResult(Right(Attribute("value", Text("true"), Null))) {
      BooleanXmlAttrFormat.write(true, "value")
    }
  }

  test("byte") {
    assertResult(42.toByte) {
      ByteXmlAttrFormat.read(Right(Attribute("value", Text("42"), Null)), "value")
    }
    assertResult(Right(Attribute("value", Text("42"), Null))) {
      ByteXmlAttrFormat.write(42.toByte, "value")
    }
  }

  test("short") {
    assertResult(42.toShort) {
      ShortXmlAttrFormat.read(Right(Attribute("value", Text("42"), Null)), "value")
    }
    assertResult(Right(Attribute("value", Text("42"), Null))) {
      ShortXmlAttrFormat.write(42.toShort, "value")
    }
  }

  test("int") {
    assertResult(42) {
      IntXmlAttrFormat.read(Right(Attribute("value", Text("42"), Null)), "value")
    }
    assertResult(Right(Attribute("value", Text("42"), Null))) {
      IntXmlAttrFormat.write(42, "value")
    }
  }

  test("long") {
    assertResult(42l) {
      LongXmlAttrFormat.read(Right(Attribute("value", Text("42"), Null)), "value")
    }
    assertResult(Right(Attribute("value", Text("42"), Null))) {
      LongXmlAttrFormat.write(42l, "value")
    }
  }

  test("float") {
    assertResult(3.14f) {
      FloatXmlAttrFormat.read(Right(Attribute("value", Text("3.14"), Null)), "value")
    }
    assertResult(Right(Attribute("value", Text("3.14"), Null))) {
      FloatXmlAttrFormat.write(3.14f, "value")
    }
  }

  test("double") {
    assertResult(3.14) {
      DoubleXmlAttrFormat.read(Right(Attribute("value", Text("3.14"), Null)), "value")
    }
    assertResult(Right(Attribute("value", Text("3.14"), Null))) {
      DoubleXmlAttrFormat.write(3.14, "value")
    }
  }

  test("string") {
    assertResult("42") {
      StringXmlAttrFormat.read(Right(Attribute("value", Text("42"), Null)), "value")
    }
    assertResult(Right(Attribute("value", Text("42"), Null))) {
      StringXmlAttrFormat.write("42", "value")
    }
  }

  test("char") {
    assertResult('c') {
      CharXmlAttrFormat.read(Right(Attribute("value", Text("c"), Null)), "value")
    }
    assertResult(Right(Attribute("value", Text("c"), Null))) {
      CharXmlAttrFormat.write('c', "value")
    }
    intercept[DeserializationException] {
      CharXmlAttrFormat.read(Right(Attribute("value", Text("ccccc"), Null)), "value")
    }
  }

  test("symbol") {
    assertResult('symbol) {
      SymbolXmlAttrFormat.read(Right(Attribute("value", Text("symbol"), Null)), "value")
    }
    assertResult(Right(Attribute("value", Text("symbol"), Null))) {
      SymbolXmlAttrFormat.write('symbol, "value")
    }
  }

  test("bigint") {
    assertResult(BigInt("1234567891234567891234567890")) {
      BigIntXmlAttrFormat.read(Right(Attribute("value", Text("1234567891234567891234567890"), Null)), "value")
    }
    assertResult(Right(Attribute("value", Text("1234567891234567891234567890"), Null))) {
      BigIntXmlAttrFormat.write(BigInt("1234567891234567891234567890"), "value")
    }
  }

  test("bigdecimal") {
    assertResult(BigDecimal("1234567891234567891234567890.123456789")) {
      BigDecimalXmlAttrFormat.read(Right(Attribute("value", Text("1234567891234567891234567890.123456789"), Null)), "value")
    }
    assertResult(Right(Attribute("value", Text("1234567891234567891234567890.123456789"), Null))) {
      BigDecimalXmlAttrFormat.write(BigDecimal("1234567891234567891234567890.123456789"), "value")
    }
  }

  test("enum") {
    val f = enumFormat[Day]
    assertResult(Day.MONDAY) {
      f.read(Right(Attribute("value", Text("MONDAY"), Null)), "value")
    }
    assertResult(Right(Attribute("value", Text("MONDAY"), Null))) {
      f.write(Day.MONDAY, "value")
    }
  }
}
