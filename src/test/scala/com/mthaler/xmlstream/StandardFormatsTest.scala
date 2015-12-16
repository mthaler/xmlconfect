package com.mthaler.xmlstream

import org.scalatest.FunSuite

import scala.xml.{Attribute, Null, Text}

class StandardFormatsTest extends FunSuite {

  test("intOption") {
    import BasicAttrFormats._
    val f = StandardFormats.optionFormat[Int]
    assertResult(Some(42)) {
      f.read(Right(Attribute("value", Text("42"), Null)), "value")
    }
    assertResult(Right(Attribute("value", Text("42"), Null))) {
      f.write(Some(42), "value")
    }
    assertResult(None) {
      f.read(Right(Null), "value")
    }
    assertResult(Right(Null)) {
      f.write(None, "value")
    }
    intercept[DeserializationException] {
      f.read(Left(<value>42</value>), "value")
    }
  }

  test("stringOption") {
    import BasicAttrFormats._
    val f = StandardFormats.optionFormat[String]
    assertResult(Some("42")) {
      f.read(Right(Attribute("value", Text("42"), Null)), "value")
    }
    assertResult(Right(Attribute("value", Text("42"), Null))) {
      f.write(Some("42"), "value")
    }
    assertResult(Right(Null)) {
      f.write(None, "value")
    }
    intercept[DeserializationException] {
      f.read(Left(<value>42</value>), "value")
    }
  }
}
