package com.mthaler.xmlstream

import org.junit.Assert._
import org.junit.Test

import scala.xml.{Attribute, Null, Text}

class StandardFormatsTest {

  @Test
  def testOptionIntXmlAttrFormat(): Unit = {
    import BasicAttrFormats._
    val f = StandardFormats.optionFormat[Int]
    val result0 = f.read(Right(Attribute("value", Text("42"), Null)), "value")
    assertEquals(Some(42), result0)
    val result1 = f.write(Some(42), "value")
    assertEquals(Right(Attribute("value", Text("42"), Null)), result1)
    val result2 = f.read(Right(Null), "value")
    assertEquals(None, result2)
    val result3 = f.write(None, "value")
    assertEquals(Right(Null), result3)
  }

  @Test
  def testOptionStringXmlAttrFormat(): Unit = {
    import BasicAttrFormats._
    val f = StandardFormats.optionFormat[String]
    val result0 = f.read(Right(Attribute("value", Text("42"), Null)), "value")
    assertEquals(Some("42"), result0)
    val result1 = f.write(Some("42"), "value")
    assertEquals(Right(Attribute("value", Text("42"), Null)), result1)
    val result2 = f.read(Right(Null), "value")
    assertEquals(None, result2)
    val result3 = f.write(None, "value")
    assertEquals(Right(Null), result3)
  }
}
