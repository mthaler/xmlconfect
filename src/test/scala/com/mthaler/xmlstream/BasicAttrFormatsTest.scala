package com.mthaler.xmlstream

import org.junit.Assert._
import org.junit.Test

import scala.xml._

import BasicAttrFormats._

class BasicAttrFormatsTest {

  @Test
  def testBooleanXmlAttrFormat(): Unit = {
    val result0 = BooleanXmlAttrFormat.read(Right(Attribute("value", Text("true"), Null)), "value")
    assertEquals(true, result0)
    val result1 = BooleanXmlAttrFormat.write(true, "value")
    assertEquals(Right(Attribute("value", Text("true"), Null)), result1)
  }

  @Test
  def testByteXmlAttrFormat(): Unit = {
    val result0 = ByteXmlAttrFormat.read(Right(Attribute("value", Text("42"), Null)), "value")
    assertEquals(42.toByte, result0)
    val result1 = ByteXmlAttrFormat.write(42.toByte, "value")
    assertEquals(Right(Attribute("value", Text("42"), Null)), result1)
  }

  @Test
  def testShortXmlAttrFormat(): Unit = {
    val result0 = ShortXmlAttrFormat.read(Right(Attribute("value", Text("42"), Null)), "value")
    assertEquals(42.toShort, result0)
    val result1 = ShortXmlAttrFormat.write(42.toShort, "value")
    assertEquals(Right(Attribute("value", Text("42"), Null)), result1)
  }

  @Test
  def testIntXmlAttrFormat(): Unit = {
    val result0 = IntXmlAttrFormat.read(Right(Attribute("value", Text("42"), Null)), "value")
    assertEquals(42, result0)
    val result1 = IntXmlAttrFormat.write(42, "value")
    assertEquals(Right(Attribute("value", Text("42"), Null)), result1)
  }

  @Test
  def testLongXmlAttrFormat(): Unit = {
    val result0 = LongXmlAttrFormat.read(Right(Attribute("value", Text("42"), Null)), "value")
    assertEquals(42.toLong, result0)
    val result1 = LongXmlAttrFormat.write(42.toLong, "value")
    assertEquals(Right(Attribute("value", Text("42"), Null)), result1)
  }

  @Test
  def testFloatXmlAttrFormat(): Unit = {
    val result0 = FloatXmlAttrFormat.read(Right(Attribute("value", Text("3.14"), Null)), "value")
    assertEquals(3.14f, result0, 0.000000001)
    val result1 = FloatXmlAttrFormat.write(3.14f, "value")
    assertEquals(Right(Attribute("value", Text("3.14"), Null)), result1)
  }

  @Test
  def testDoubleXmlAttrFormat(): Unit = {
    val result0 = DoubleXmlAttrFormat.read(Right(Attribute("value", Text("3.14"), Null)), "value")
    assertEquals(3.14, result0, 0.000000001)
    val result1 = DoubleXmlAttrFormat.write(3.14, "value")
    assertEquals(Right(Attribute("value", Text("3.14"), Null)), result1)
  }

  @Test
  def testStringXmlAttrFormat(): Unit = {
    val result0 = StringXmlAttrFormat.read(Right(Attribute("value", Text("42"), Null)), "value")
    assertEquals("42", result0)
    val result1 = StringXmlAttrFormat.write("42", "value")
    assertEquals(Right(Attribute("value", Text("42"), Null)), result1)
  }

  @Test
  def testCharXmlAttrFormat(): Unit = {
    val result0 = CharXmlAttrFormat.read(Right(Attribute("value", Text("c"), Null)), "value")
    assertEquals('c', result0)
    val result1 = CharXmlAttrFormat.write('c', "value")
    assertEquals(Right(Attribute("value", Text("c"), Null)), result1)
  }

  @Test
  def testSymbolXmlAttrFormat(): Unit = {
    val result0 = SymbolXmlAttrFormat.read(Right(Attribute("value", Text("symbol"), Null)), "value")
    assertEquals('symbol, result0)
    val result1 = SymbolXmlAttrFormat.write('symbol, "value")
    assertEquals(Right(Attribute("value", Text("symbol"), Null)), result1)
  }

  @Test
  def testBigIntXmlAttrFormat(): Unit = {
    val result0 = BigIntXmlAttrFormat.read(Right(Attribute("value", Text("1234567891234567891234567890"), Null)), "value")
    assertEquals(BigInt("1234567891234567891234567890"), result0)
    val result1 = BigIntXmlAttrFormat.write(BigInt("1234567891234567891234567890"), "value")
    assertEquals(Right(Attribute("value", Text("1234567891234567891234567890"), Null)), result1)
  }

  @Test
  def testBigDecimalXmlAttrFormat(): Unit = {
    val result0 = BigDecimalXmlAttrFormat.read(Right(Attribute("value", Text("1234567891234567891234567890.123456789"), Null)), "value")
    assertEquals(BigDecimal("1234567891234567891234567890.123456789"), result0)
    val result1 = BigDecimalXmlAttrFormat.write(BigDecimal("1234567891234567891234567890.123456789"), "value")
    assertEquals(Right(Attribute("value", Text("1234567891234567891234567890.123456789"), Null)), result1)
  }
}
