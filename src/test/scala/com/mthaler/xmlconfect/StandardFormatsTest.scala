package com.mthaler.xmlconfect

import org.scalatest.FunSuite

import scala.xml.{ Attribute, Null, Text }

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

  test("tuple1attr") {
    import BasicAttrFormats._
    implicit val f = StandardFormats.tuple1Format[String]
    assertResult(Tuple1("test")) {
      <Tuple1 _1="test"/>.convertTo[Tuple1[String]]
    }
    assertResult(<Tuple1 _1="test"/>) {
      Tuple1("test").toNode("Tuple1")
    }
    intercept[DeserializationException] {
      f.read(Right(Attribute("_1", Text("test"), Null)))
    }
  }

  test("tuple1elem") {
    import BasicElemFormats._
    implicit val f = StandardFormats.tuple1Format[String]
    assertResult(Tuple1("test")) {
      <Tuple1><_1>test</_1></Tuple1>.convertTo[Tuple1[String]]
    }
    assertResult(<Tuple1><_1>test</_1></Tuple1>) {
      Tuple1("test").toNode("Tuple1")
    }
    intercept[DeserializationException] {
      f.read(Right(Null))
    }
  }

  test("tuple2attr") {
    import BasicAttrFormats._
    implicit val f = StandardFormats.tuple2Format[String, Int]
    assertResult(("test", 42)) {
      <Tuple2 _1="test" _2="42"/>.convertTo[(String, Int)]
    }
    assertResult(<Tuple2 _1="test" _2="42"/>) {
      ("test", 42).toNode("Tuple2")
    }
    intercept[DeserializationException] {
      f.read(Right(Null))
    }
  }

  test("tuple2elem") {
    import BasicElemFormats._
    implicit val f = StandardFormats.tuple2Format[String, Int]
    assertResult(("test", 42)) {
      <Tuple2><_1>test</_1><_2>42</_2></Tuple2>.convertTo[(String, Int)]
    }
    assertResult(<Tuple2><_1>test</_1><_2>42</_2></Tuple2>) {
      ("test", 42).toNode("Tuple2")
    }
    intercept[DeserializationException] {
      f.read(Right(Null))
    }
  }

  test("tuple3") {
    import BasicAttrFormats._
    val f = StandardFormats.tuple3Format[String, Int, Boolean]
    assertResult(("test", 42, true)) {
      f.read(Right(Attribute("_1", Text("test"), Null).append(Attribute("_2", Text("42"), Null)).append(Attribute("_3", Text("true"), Null))))
    }
    assertResult(Right(Attribute("_1", Text("test"), Null).append(Attribute("_2", Text("42"), Null)).append(Attribute("_3", Text("true"), Null)))) {
      f.write(("test", 42, true), "value")
    }
    intercept[DeserializationException] {
      f.read(Left(<test/>))
    }
  }

  test("tuple4") {
    import BasicAttrFormats._
    val f = StandardFormats.tuple4Format[String, Int, Boolean, BigInt]
    assertResult(("test", 42, true, BigInt(42))) {
      f.read(Right(Attribute("_1", Text("test"), Null).append(Attribute("_2", Text("42"), Null)).append(Attribute("_3", Text("true"), Null)).append(Attribute("_4", Text("42"), Null))))
    }
    assertResult(Right(Attribute("_1", Text("test"), Null).append(Attribute("_2", Text("42"), Null)).append(Attribute("_3", Text("true"), Null)).append(Attribute("_4", Text("42"), Null)))) {
      f.write(("test", 42, true, BigInt(42)), "value")
    }
    intercept[DeserializationException] {
      f.read(Left(<test/>))
    }
  }
}
