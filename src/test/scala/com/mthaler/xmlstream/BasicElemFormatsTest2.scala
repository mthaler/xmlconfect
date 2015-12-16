package com.mthaler.xmlstream

import org.scalatest.FunSuite
import BasicElemFormats._

class BasicElemFormatsTest2 extends FunSuite {

  test("boolean") {
    val result0 = BooleanXmlElemFormat.read((Left(<value>true</value>)), "value")
    assert(true == result0)
    val result1 = BooleanXmlElemFormat.write(true, "value")
    assert(Left(<value>true</value>) == result1)
  }

  test("byte") {
    val result0 = ByteXmlElemFormat.read((Left(<value>42</value>)), "value")
    assert(42.toByte == result0)
    val result1 = ByteXmlElemFormat.write(42.toByte, "value")
    assert(Left(<value>42</value>) == result1)
  }

  test("short") {
    val result0 = ShortXmlElemFormat.read((Left(<value>42</value>)), "value")
    assert(42.toShort == result0)
    val result1 = ShortXmlElemFormat.write(42.toShort, "value")
    assert(Left(<value>42</value>) == result1)
  }

  test("int") {
    val result0 = IntXmlElemFormat.read((Left(<value>42</value>)), "value")
    assert(42.toInt == result0)
    val result1 = IntXmlElemFormat.write(42, "value")
    assert(Left(<value>42</value>) == result1)
  }

  test("long") {
    val result0 = LongXmlElemFormat.read((Left(<value>42</value>)), "value")
    assert(42l == result0)
    val result1 = LongXmlElemFormat.write(42l, "value")
    assert(Left(<value>42</value>) == result1)
  }

  test("float") {
    val result0 = FloatXmlElemFormat.read((Left(<value>3.14</value>)), "value")
    assert(3.14f == result0)
    val result1 = FloatXmlElemFormat.write(3.14f, "value")
    assert(Left(<value>3.14</value>) == result1)
  }

  test("double") {
    val result0 = DoubleXmlElemFormat.read((Left(<value>3.14</value>)), "value")
    assert(3.14 == result0)
    val result1 = DoubleXmlElemFormat.write(3.14, "value")
    assert(Left(<value>3.14</value>) == result1)
  }

  test("string") {
    val result0 = StringXmlElemFormat.read((Left(<value>42</value>)), "value")
    assert("42" == result0)
    val result1 = StringXmlElemFormat.write("42", "value")
    assert(Left(<value>42</value>) == result1)
  }

  test("char") {
    val result0 = CharXmlElemFormat.read((Left(<value>c</value>)), "value")
    assert('c' == result0)
    val result1 = CharXmlElemFormat.write('c', "value")
    assert(Left(<value>c</value>) == result1)
  }

  test("symbol") {
    val result0 = SymbolXmlElemFormat.read((Left(<value>symbol</value>)), "value")
    assert('symbol == result0)
    val result1 = SymbolXmlElemFormat.write('symbol, "value")
    assert(Left(<value>symbol</value>) == result1)
  }

  test("bigint") {
    val result0 = BigIntXmlElemFormat.read((Left(<value>1234567891234567891234567890</value>)), "value")
    assert(BigInt("1234567891234567891234567890") == result0)
    val result1 = BigIntXmlElemFormat.write(BigInt("1234567891234567891234567890"), "value")
    assert(Left(<value>1234567891234567891234567890</value>) == result1)
  }

  test("bigdecimal") {
    val result0 = BigDecimalXmlElemFormat.read((Left(<value>1234567891234567891234567890.123456789</value>)), "value")
    assert(BigDecimal("1234567891234567891234567890.123456789") == result0)
    val result1 = BigDecimalXmlElemFormat.write(BigDecimal("1234567891234567891234567890.123456789"), "value")
    assert(Left(<value>1234567891234567891234567890.123456789</value>) == result1)
  }
}
