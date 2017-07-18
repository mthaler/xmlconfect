package com.mthaler.xmlconfect

import org.scalatest.FunSuite
import scala.xml.{ Null, Text, Attribute }
import BasicElemFormats._

class BasicElemFormatsTest extends FunSuite {

  test("boolean") {
    assertResult(true) {
      (<value>true</value>).convertTo[Boolean]
    }
    assertResult(<value>true</value>) {
      true.toNode("value")
    }
    intercept[DeserializationException] {
      BooleanXmlElemFormat.read(Right(Attribute("value", Text("true"), Null)))
    }
  }

  test("byte") {
    assertResult(42.toByte) {
      <value>42</value>.convertTo[Byte]
    }
    assertResult(<value>42</value>) {
      42.toByte.toNode("value")
    }
    intercept[DeserializationException] {
      ByteXmlElemFormat.read(Right(Attribute("value", Text("42"), Null)))
    }
  }

  test("short") {
    assertResult(42.toShort) {
      <value>42</value>.convertTo[Short]
    }
    assertResult(<value>42</value>) {
      42.toShort.toNode("value")
    }
    intercept[DeserializationException] {
      ShortXmlElemFormat.read(Right(Attribute("value", Text("42"), Null)))
    }
  }

  test("int") {
    assertResult(42) {
      <value>42</value>.convertTo[Int]
    }
    assertResult(<value>42</value>) {
      42.toNode("value")
    }
    intercept[DeserializationException] {
      IntXmlElemFormat.read(Right(Attribute("value", Text("42"), Null)))
    }
  }

  test("long") {
    assertResult(42.toLong) {
      <value>42</value>.convertTo[Long]
    }
    assertResult(<value>42</value>) {
      42.toLong.toNode("value")
    }
    intercept[DeserializationException] {
      LongXmlElemFormat.read(Right(Attribute("value", Text("42"), Null)))
    }
  }

  test("float") {
    assertResult(3.14f) {
      <value>3.14</value>.convertTo[Float]
    }
    assertResult(<value>3.14</value>) {
      3.14f.toNode("value")
    }
    intercept[DeserializationException] {
      FloatXmlElemFormat.read(Right(Attribute("value", Text("3.14"), Null)))
    }
  }

  test("double") {
    assertResult(3.14) {
      <value>3.14</value>.convertTo[Double]
    }
    assertResult(<value>3.14</value>) {
      3.14.toNode("value")
    }
    intercept[DeserializationException] {
      DoubleXmlElemFormat.read(Right(Attribute("value", Text("3.14"), Null)))
    }
  }

  test("string") {
    assertResult("42") {
      <value>42</value>.convertTo[String]
    }
    assertResult(<value>42</value>) {
      "42".toNode("value")
    }
    intercept[DeserializationException] {
      StringXmlElemFormat.read(Right(Attribute("value", Text("42"), Null)))
    }
  }

  test("char") {
    assertResult('c') {
      <value>c</value>.convertTo[Char]
    }
    assertResult(<value>c</value>) {
      'c'.toNode("value")
    }
    intercept[DeserializationException] {
      CharXmlElemFormat.read(Left(TNode.id(<value>ccccc</value>)))
    }
    intercept[DeserializationException] {
      CharXmlElemFormat.read(Right(Attribute("value", Text("c"), Null)))
    }
  }

  test("symbol") {
    assertResult('symbol) {
      <value>symbol</value>.convertTo[Symbol]
    }
    assertResult(<value>symbol</value>) {
      'symbol.toNode("value")
    }
    intercept[DeserializationException] {
      SymbolXmlElemFormat.read(Right(Attribute("value", Text("symbol"), Null)))
    }
  }

  test("bigInt") {
    assertResult(BigInt("1234567891234567891234567890")) {
      <value>1234567891234567891234567890</value>.convertTo[BigInt]
    }
    assertResult(<value>1234567891234567891234567890</value>) {
      BigInt("1234567891234567891234567890").toNode("value")
    }
    intercept[DeserializationException] {
      BigIntXmlElemFormat.read(Right(Attribute("value", Text("1234567891234567891234567890"), Null)))
    }
  }

  test("bigDecimal") {
    assertResult(BigDecimal("1234567891234567891234567890.123456789")) {
      <value>1234567891234567891234567890.123456789</value>.convertTo[BigDecimal]
    }
    assertResult(<value>1234567891234567891234567890.123456789</value>) {
      BigDecimal("1234567891234567891234567890.123456789").toNode("value")
    }
    intercept[DeserializationException] {
      BigDecimalXmlElemFormat.read(Right(Attribute("value", Text("1234567891234567891234567890.123456789"), Null)))
    }
  }

  test("enum") {
    implicit val f = enumFormat[Day]
    assertResult(Day.MONDAY) {
      <value>MONDAY</value>.convertTo[Day]
    }
    assertResult(<value>MONDAY</value>) {
      Day.MONDAY.toNode("value")
    }
    intercept[DeserializationException] {
      f.read(Right(Attribute("value", Text("MONDAY"), Null)))
    }
  }

  test("serializeDeserializeXML") {
    val xml = 42.toNode("value")
    val result = SerializationTestHelper.serializeDeserialize(xml)
    assert(result === xml)
  }
}
