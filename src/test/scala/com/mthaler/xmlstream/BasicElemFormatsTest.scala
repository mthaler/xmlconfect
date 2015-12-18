package com.mthaler.xmlstream

import org.scalatest.FunSuite
import scala.xml.{ Null, Text, Attribute }
import BasicElemFormats._

class BasicElemFormatsTest extends FunSuite {

  test("boolean") {
    assertResult(true) {
      BooleanXmlElemFormat.read(Left(<value>true</value>), "value")
    }
    assertResult(Left(<value>true</value>)) {
      BooleanXmlElemFormat.write(true, "value")
    }
    intercept[DeserializationException] {
      BooleanXmlElemFormat.read(Right(Attribute("value", Text("true"), Null)))
    }
  }

  test("byte") {
    assertResult(42.toByte) {
      ByteXmlElemFormat.read(Left(<value>42</value>), "value")
    }
    assertResult(Left(<value>42</value>)) {
      ByteXmlElemFormat.write(42.toByte, "value")
    }
    intercept[DeserializationException] {
      ByteXmlElemFormat.read(Right(Attribute("value", Text("42"), Null)))
    }
  }

  test("short") {
    assertResult(42.toShort) {
      ShortXmlElemFormat.read(Left(<value>42</value>), "value")
    }
    assertResult(Left(<value>42</value>)) {
      ShortXmlElemFormat.write(42.toShort, "value")
    }
    intercept[DeserializationException] {
      ShortXmlElemFormat.read(Right(Attribute("value", Text("42"), Null)))
    }
  }

  test("int") {
    assertResult(42) {
      IntXmlElemFormat.read(Left(<value>42</value>), "value")
    }
    assertResult(Left(<value>42</value>)) {
      IntXmlElemFormat.write(42, "value")
    }
    intercept[DeserializationException] {
      IntXmlElemFormat.read(Right(Attribute("value", Text("42"), Null)))
    }
  }

  test("long") {
    assertResult(42.toLong) {
      LongXmlElemFormat.read(Left(<value>42</value>), "value")
    }
    assertResult(Left(<value>42</value>)) {
      LongXmlElemFormat.write(42.toLong, "value")
    }
    intercept[DeserializationException] {
      LongXmlElemFormat.read(Right(Attribute("value", Text("42"), Null)))
    }
  }

  test("float") {
    assertResult(3.14f) {
      FloatXmlElemFormat.read((Left(<value>3.14</value>)), "value")
    }
    assertResult(Left(<value>3.14</value>)) {
      FloatXmlElemFormat.write(3.14f, "value")
    }
    intercept[DeserializationException] {
      FloatXmlElemFormat.read(Right(Attribute("value", Text("3.14"), Null)))
    }
  }

  test("double") {
    assertResult(3.14) {
      DoubleXmlElemFormat.read((Left(<value>3.14</value>)), "value")
    }
    assertResult(Left(<value>3.14</value>)) {
      DoubleXmlElemFormat.write(3.14, "value")
    }
    intercept[DeserializationException] {
      DoubleXmlElemFormat.read(Right(Attribute("value", Text("3.14"), Null)))
    }
  }

  test("string") {
    assertResult("42") {
      StringXmlElemFormat.read(Left(<value>42</value>), "value")
    }
    assertResult(Left(<value>42</value>)) {
      StringXmlElemFormat.write("42", "value")
    }
    intercept[DeserializationException] {
      StringXmlElemFormat.read(Right(Attribute("value", Text("42"), Null)))
    }
  }

  test("char") {
    assertResult('c') {
      CharXmlElemFormat.read(Left(<value>c</value>), "value")
    }
    assertResult(Left(<value>c</value>)) {
      CharXmlElemFormat.write('c', "value")
    }
    intercept[DeserializationException] {
      CharXmlElemFormat.read(Left(<value>ccccc</value>))
    }
    intercept[DeserializationException] {
      CharXmlElemFormat.read(Right(Attribute("value", Text("c"), Null)))
    }
  }

  test("symbol") {
    assertResult('symbol) {
      SymbolXmlElemFormat.read(Left(<value>symbol</value>), "value")
    }
    assertResult(Left(<value>symbol</value>)) {
      SymbolXmlElemFormat.write('symbol, "value")
    }
    intercept[DeserializationException] {
      SymbolXmlElemFormat.read(Right(Attribute("value", Text("symbol"), Null)))
    }
  }

  test("bigInt") {
    assertResult(BigInt("1234567891234567891234567890")) {
      BigIntXmlElemFormat.read(Left(<value>1234567891234567891234567890</value>), "value")
    }
    assertResult(Left(<value>1234567891234567891234567890</value>)) {
      BigIntXmlElemFormat.write(BigInt("1234567891234567891234567890"), "value")
    }
    intercept[DeserializationException] {
      BigIntXmlElemFormat.read(Right(Attribute("value", Text("1234567891234567891234567890"), Null)))
    }
  }

  test("bigDecimal") {
    assertResult(BigDecimal("1234567891234567891234567890.123456789")) {
      BigDecimalXmlElemFormat.read(Left(<value>1234567891234567891234567890.123456789</value>), "value")
    }
    assertResult(Left(<value>1234567891234567891234567890.123456789</value>)) {
      BigDecimalXmlElemFormat.write(BigDecimal("1234567891234567891234567890.123456789"), "value")
    }
    intercept[DeserializationException] {
      BigDecimalXmlElemFormat.read(Right(Attribute("value", Text("1234567891234567891234567890.123456789"), Null)))
    }
  }
}
