package com.mthaler.xmlconfect

import com.mthaler.xmlconfect.ProductFormat.xmlFormat2
import com.mthaler.xmlconfect.ProductFormatTest.Product2
import org.scalatest.FunSuite
import scala.reflect._

class CustomProductFormatTest extends FunSuite {

  test("xmlFormat2Attributes") {
    implicit val f = xmlFormat2(Product2)(BasicAttrFormats.StringXmlAttrFormat, BasicAttrFormats.IntXmlAttrFormat, classTag[Product2])
    val p = Product2("test", 42)
    assertResult(<Product2 field1="test" field2="42"/>) {
      p.toNode
    }
    assertResult(p) {
      <Product2 field1="test" field2="42"/>.convertTo[Product2]
    }
  }

  test("xmlFormat2Elements") {
    implicit val f = xmlFormat2(Product2)(BasicElemFormats.StringXmlElemFormat, BasicElemFormats.IntXmlElemFormat, classTag[Product2])
    val p = Product2("test", 42)
    assertResult(<Product2><field1>test</field1><field2>42</field2></Product2>) {
      p.toNode
    }
    assertResult(p) {
      <Product2><field1>test</field1><field2>42</field2></Product2>.convertTo[Product2]
    }
  }

  test("xmlFormat2Mixed") {
    implicit val f = xmlFormat2(Product2)(BasicElemFormats.StringXmlElemFormat, BasicAttrFormats.IntXmlAttrFormat, classTag[Product2])
    val p = Product2("test", 42)
    assertResult(<Product2 field2="42"><field1>test</field1></Product2>) {
      p.toNode
    }
    assertResult(p) {
      <Product2 field2="42"><field1>test</field1></Product2>.convertTo[Product2]
    }
  }
}
