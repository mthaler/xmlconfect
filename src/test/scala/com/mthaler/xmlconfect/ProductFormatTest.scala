package com.mthaler.xmlconfect

import org.scalatest.FunSuite

import ProductFormat._
import scala.reflect.classTag
import scala.xml.{ Null, MetaData, Node }

object ProductFormatTest {
  case class TestClass(field1: String, field2: Int, field3: Double)
  case class Product1(field1: String)
  case class Product2(field1: String, field2: Int)
  case class Product1WithProduct1(product1: Product1)
  case class Product1WithOption(option: Option[Int])
  case class Person(name: String = "Albert Einstein", age: Int = 42)
}

class ProductFormatTest extends FunSuite {

  import ProductFormatTest._

  test("extractFieldNames") {
    val fieldNames = ProductFormat.extractFieldNames(classTag[TestClass])
    assert(Seq("field1", "field2", "field3") == fieldNames.toSeq)
  }

  test("xmlFormat1") {
    import BasicAttrFormats._
    implicit val f = xmlFormat1(Product1)
    val p = Product1("test")
    val result0 = f.write(p)
    assert(<Product1 field1="test"/> == result0.left.get.apply)
    val result1 = f.read(result0)
    assert(p == result1)
    val f2 = xmlFormat1(Product1WithProduct1)
    val p2 = Product1WithProduct1(Product1("test"))
    val result2 = f2.write(p2)
    assert(<Product1WithProduct1><product1 field1="test"/></Product1WithProduct1> == result2.left.get.apply)
    val result3 = f2.read(result2)
    assert(p2 == result3)
  }

  test("xmlFormat2") {
    import com.mthaler.xmlconfect.BasicAttrFormats._
    val f = xmlFormat2(Product2)
    val p = Product2("test", 42)
    val result0 = f.write(p)
    assert(<Product2 field1="test" field2="42"/> == result0.left.get.apply)
    val result1 = f.read(result0)
    assert(p == result1)
  }

  test("xmlFormat1WithOptions") {
    import com.mthaler.xmlconfect.BasicAttrFormats._
    import com.mthaler.xmlconfect.StandardFormats._
    implicit val f = xmlFormat1(Product1WithOption)
    val p = Product1WithOption(Some(42))
    val result0 = f.write(p)
    assert(<Product1WithOption option="42"/> == result0.left.get.apply)
    val result1 = f.read(result0)
    assert(p == result1)
    val p2 = Product1WithOption(None)
    val result2 = f.write(p2)
    assert(<Product1WithOption/> == result2.left.get.apply)
    val result3 = f.read(result2)
    assert(p2 == result3)
  }

  test("xmlFormat2CustomFieldNames") {
    import com.mthaler.xmlconfect.BasicAttrFormats._
    val f = xmlFormat(Product2.apply, "myfield1", "myfield2")
    val p = Product2("test", 42)
    val result0 = f.write(p)
    assert(<Product2 myfield1="test" myfield2="42"/> == result0.left.get.apply)
    val result1 = f.read(result0)
    assert(p == result1)
  }

  test("missingFields") {
    import com.mthaler.xmlconfect.BasicAttrFormats._
    val f = xmlFormat2(Person)
    assertResult(<Person name="Richard Feynman" age="56"/>) {
      f.write(Person("Richard Feynman", 56)).left.get.apply
    }
    assertResult(Person("Richard Feynman", 56)) {
      f.read(Left(TNode.id(<Person name="Richard Feynman" age="56"/>)))
    }
    assertResult(Person("Richard Feynman", 42)) {
      f.read(Left(TNode.id(<Person name="Richard Feynman"/>)))
    }
  }
}
