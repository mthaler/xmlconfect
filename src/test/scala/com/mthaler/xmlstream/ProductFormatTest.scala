package com.mthaler.xmlstream

import ProductFormat._
import org.junit.Assert._
import org.junit.Test
import scala.reflect.classTag

object ProductFormatTest {
  case class TestClass(field1: String, field2: Int, field3: Double)
  case class Product1(field1: String)
  case class Product2(field1: String, field2: Int)
  case class Product1WithProduct1(product1: Product1)
  case class Product1WithOption(option: Option[Int])
}

class ProductFormatTest {

  import ProductFormatTest._

  @Test
  def testExtractFieldNames(): Unit = {
    val fieldNames = ProductFormat.extractFieldNames(classTag[TestClass])
    assertEquals(Seq("field1", "field2", "field3"), fieldNames.toSeq)
  }

  @Test
  def testXmlFormat1(): Unit = {
    import BasicAttrFormats._
    implicit val f = xmlFormat1(Product1)
    val p = Product1("test")
    val result0 = f.write(p)
    assertEquals(Left(<Product1 field1="test"/>), result0)
    val result1 = f.read(result0)
    assertEquals(p, result1)
    val f2 = xmlFormat1(Product1WithProduct1)
    val p2 = Product1WithProduct1(Product1("test"))
    val result2 = f2.write(p2)
    assertEquals(Left(<Product1WithProduct1><product1 field1="test"/></Product1WithProduct1>), result2)
    val result3 = f2.read(result2)
    assertEquals(p2, result3)
  }

  @Test
  def testXmlFormat2(): Unit = {
    import com.mthaler.xmlstream.BasicAttrFormats._
    val f = xmlFormat2(Product2)
    val p = Product2("test", 42)
    val result0 = f.write(p)
    assertEquals(Left(<Product2 field1="test" field2="42"/>), result0)
    val result1 = f.read(result0)
    assertEquals(p, result1)
  }

  @Test
  def testXmlFormat1WithOption(): Unit = {
    import com.mthaler.xmlstream.BasicAttrFormats._
    import com.mthaler.xmlstream.StandardFormats._
    implicit val f = xmlFormat1(Product1WithOption)
    val p = Product1WithOption(Some(42))
    val result0 = f.write(p)
    assertEquals(Left(<Product1WithOption option="42"/>), result0)
    val result1 = f.read(result0)
    assertEquals(p, result1)
    val p2 = Product1WithOption(None)
    val result2 = f.write(p2)
    assertEquals(Left(<Product1WithOption/>), result2)
    val result3 = f.read(result2)
    assertEquals(p2, result3)
  }

  @Test
  def testXmlFormat2CustomFieldNames() {
    import com.mthaler.xmlstream.BasicAttrFormats._
    val f = xmlFormat(Product2.apply, "myfield1", "myfield2")
    val p = Product2("test", 42)
    val result0 = f.write(p)
    assertEquals(Left(<Product2 myfield1="test" myfield2="42"/>), result0)
    val result1 = f.read(result0)
    assertEquals(p, result1)
  }
}
