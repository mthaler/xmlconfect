package com.mthaler.xmlconfect

import BasicTextFormats._
import ProductFormatInstances._
import org.scalatest.funsuite.AnyFunSuite

class BasicTextFormatsTest extends AnyFunSuite {

  test("boolean") {
    case class Test(value: Boolean)
    implicit val f = xmlFormat1(Test)

    assertResult(Test(true)) {
      <Test>true</Test>.convertTo[Test]
    }
    assertResult(<Test>true</Test>) {
      Test(true).toNode
    }
  }

  test("byte") {
    case class Test(value: Byte)
    implicit val f = xmlFormat1(Test)

    assertResult(Test(42.toByte)) {
      <Test>42</Test>.convertTo[Test]
    }
    assertResult(<Test>42</Test>) {
      Test(42.toByte).toNode
    }
  }

  test("short") {
    case class Test(value: Short)
    implicit val f = xmlFormat1(Test)

    assertResult(Test(42.toShort)) {
      <Test>42</Test>.convertTo[Test]
    }
    assertResult(<Test>42</Test>) {
      Test(42.toShort).toNode
    }
  }

  test("int") {
    case class Test(value: Int)
    implicit val f = xmlFormat1(Test)

    assertResult(Test(42)) {
      <Test>42</Test>.convertTo[Test]
    }
    assertResult(<Test>42</Test>) {
      Test(42).toNode
    }
  }

  test("long") {
    case class Test(value: Long)
    implicit val f = xmlFormat1(Test)

    assertResult(Test(42.toLong)) {
      <Test>42</Test>.convertTo[Test]
    }
    assertResult(<Test>42</Test>) {
      Test(42.toLong).toNode
    }
  }

  test("float") {
    case class Test(value: Float)
    implicit val f = xmlFormat1(Test)

    assertResult(Test(3.14f)) {
      <Test>3.14</Test>.convertTo[Test]
    }
    assertResult(<Test>3.14</Test>) {
      Test(3.14f).toNode
    }
  }

  test("double") {
    case class Test(value: Double)
    implicit val f = xmlFormat1(Test)

    assertResult(Test(3.14)) {
      <Test>3.14</Test>.convertTo[Test]
    }
    assertResult(<Test>3.14</Test>) {
      Test(3.14).toNode
    }
  }

  test("string") {
    case class Test(value: String)
    implicit val f = xmlFormat1(Test)

    assertResult(Test("test")) {
      <Test>test</Test>.convertTo[Test]
    }
    assertResult(<Test>test</Test>) {
      Test("test").toNode
    }
  }

  test("char") {
    case class Test(value: Char)
    implicit val f = xmlFormat1(Test)

    assertResult(Test('c')) {
      <Test>c</Test>.convertTo[Test]
    }
    assertResult(<Test>c</Test>) {
      Test('c').toNode
    }
  }

  test("symbol") {
    case class Test(value: Symbol)
    implicit val f = xmlFormat1(Test)

    assertResult(Test('test)) {
      <Test>test</Test>.convertTo[Test]
    }
    assertResult(<Test>test</Test>) {
      Test('test).toNode
    }
  }

  test("bigInt") {
    case class Test(value: BigInt)
    implicit val f = xmlFormat1(Test)

    assertResult(Test(BigInt("1234567891234567891234567890"))) {
      <Test>1234567891234567891234567890</Test>.convertTo[Test]
    }
    assertResult(<Test>1234567891234567891234567890</Test>) {
      Test(BigInt("1234567891234567891234567890")).toNode
    }
  }

  test("bigDecimal") {
    case class Test(value: BigDecimal)
    implicit val f = xmlFormat1(Test)

    assertResult(Test(BigDecimal("1234567891234567891234567890.123456789"))) {
      <Test>1234567891234567891234567890.123456789</Test>.convertTo[Test]
    }
    assertResult(<Test>1234567891234567891234567890.123456789</Test>) {
      Test(BigDecimal("1234567891234567891234567890.123456789")).toNode
    }
  }

  test("enum") {
    case class Test(value: Day)
    implicit val f = enumFormat[Day]
    implicit val f2 = xmlFormat1(Test)

    assertResult(Test(Day.MONDAY)) {
      <Test>MONDAY</Test>.convertTo[Test]
    }
    assertResult(<Test>MONDAY</Test>) {
      Test(Day.MONDAY).toNode
    }
  }
}
