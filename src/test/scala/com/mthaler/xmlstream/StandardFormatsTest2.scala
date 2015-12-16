package com.mthaler.xmlstream

import org.scalatest.FunSuite

import scala.xml.{Attribute, Null, Text}

class StandardFormatsTest2 extends FunSuite {

  test("intoption") {
    import BasicAttrFormats._
    val f = StandardFormats.optionFormat[Int]
    val result0 = f.read(Right(Attribute("value", Text("42"), Null)), "value")
    assert(Some(42) == result0)
    val result1 = f.write(Some(42), "value")
    assert(Right(Attribute("value", Text("42"), Null)) == result1)
    val result2 = f.read(Right(Null), "value")
    assert(None == result2)
    val result3 = f.write(None, "value")
    assert(Right(Null) == result3)
  }

  test("stringoption") {
    import BasicAttrFormats._
    val f = StandardFormats.optionFormat[String]
    val result0 = f.read(Right(Attribute("value", Text("42"), Null)), "value")
    assert(Some("42") == result0)
    val result1 = f.write(Some("42"), "value")
    assert(Right(Attribute("value", Text("42"), Null)) == result1)
    val result2 = f.read(Right(Null), "value")
    assert(None == result2)
    val result3 = f.write(None, "value")
    assert(Right(Null) == result3)
  }
}
