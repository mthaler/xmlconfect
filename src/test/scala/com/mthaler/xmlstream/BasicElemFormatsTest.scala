package com.mthaler.xmlstream

import org.junit.Assert._
import org.junit.Test

import BasicElemFormats._

class BasicElemFormatsTest {

  @Test
  def testBooleanXmlElemFormat(): Unit = {
    val result0 = BooleanXmlElemFormat.read((Left(<value>true</value>)), "value")
    assertEquals(true, result0)
    val result1 = BooleanXmlElemFormat.write(true, "value")
    assertEquals(Left(<value>true</value>), result1)
  }

  @Test
  def testByteXmlElemFormat(): Unit = {
    val result0 = ByteXmlElemFormat.read((Left(<value>42</value>)), "value")
    assertEquals(42.toByte, result0)
    val result1 = ByteXmlElemFormat.write(42.toByte, "value")
    assertEquals(Left(<value>42</value>), result1)
  }

  @Test
  def testShortXmlElemFormat(): Unit = {
    val result0 = ShortXmlElemFormat.read((Left(<value>42</value>)), "value")
    assertEquals(42.toShort, result0)
    val result1 = ShortXmlElemFormat.write(42.toShort, "value")
    assertEquals(Left(<value>42</value>), result1)
  }

  @Test
  def testIntXmlElemFormat(): Unit = {
    val result0 = IntXmlElemFormat.read((Left(<value>42</value>)), "value")
    assertEquals(42.toInt, result0)
    val result1 = IntXmlElemFormat.write(42, "value")
    assertEquals(Left(<value>42</value>), result1)
  }

  @Test
  def testLongXmlElemFormat(): Unit = {
    val result0 = LongXmlElemFormat.read((Left(<value>42</value>)), "value")
    assertEquals(42l, result0)
    val result1 = LongXmlElemFormat.write(42l, "value")
    assertEquals(Left(<value>42</value>), result1)
  }

  @Test
  def testFloatXmlElemFormat(): Unit = {
    val result0 = FloatXmlElemFormat.read((Left(<value>3.14</value>)), "value")
    assertEquals(3.14f, result0, 0.00000001)
    val result1 = FloatXmlElemFormat.write(3.14f, "value")
    assertEquals(Left(<value>3.14</value>), result1)
  }

  @Test
  def testDoubleXmlElemFormat(): Unit = {
    val result0 = DoubleXmlElemFormat.read((Left(<value>3.14</value>)), "value")
    assertEquals(3.14, result0, 0.00000001)
    val result1 = DoubleXmlElemFormat.write(3.14, "value")
    assertEquals(Left(<value>3.14</value>), result1)
  }

  @Test
  def testStringXmlElemFormat(): Unit = {
    val result0 = StringXmlElemFormat.read((Left(<value>42</value>)), "value")
    assertEquals("42", result0)
    val result1 = StringXmlElemFormat.write("42", "value")
    assertEquals(Left(<value>42</value>), result1)
  }

  @Test
  def testCharXmlElemFormat(): Unit = {
    val result0 = CharXmlElemFormat.read((Left(<value>c</value>)), "value")
    assertEquals('c', result0)
    val result1 = CharXmlElemFormat.write('c', "value")
    assertEquals(Left(<value>c</value>), result1)
  }

  @Test
  def testSymbolXmlElemFormat(): Unit = {
    val result0 = SymbolXmlElemFormat.read((Left(<value>symbol</value>)), "value")
    assertEquals('symbol, result0)
    val result1 = SymbolXmlElemFormat.write('symbol, "value")
    assertEquals(Left(<value>symbol</value>), result1)
  }

  @Test
  def testBigIntXmlElemFormat(): Unit = {
    val result0 = BigIntXmlElemFormat.read((Left(<value>1234567891234567891234567890</value>)), "value")
    assertEquals(BigInt("1234567891234567891234567890"), result0)
    val result1 = BigIntXmlElemFormat.write(BigInt("1234567891234567891234567890"), "value")
    assertEquals(Left(<value>1234567891234567891234567890</value>), result1)
  }

  @Test
  def testBigDecimalXmlElemFormat(): Unit = {
    val result0 = BigDecimalXmlElemFormat.read((Left(<value>1234567891234567891234567890.123456789</value>)), "value")
    assertEquals(BigDecimal("1234567891234567891234567890.123456789"), result0)
    val result1 = BigDecimalXmlElemFormat.write(BigDecimal("1234567891234567891234567890.123456789"), "value")
    assertEquals(Left(<value>1234567891234567891234567890.123456789</value>), result1)
  }
}
