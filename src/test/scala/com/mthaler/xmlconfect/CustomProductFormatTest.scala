package com.mthaler.xmlconfect

import com.mthaler.xmlconfect.CustomProductFormat.xmlFormat2
import com.mthaler.xmlconfect.ProductFormatTest.Product2
import org.scalatest.FunSuite

class CustomProductFormatTest extends FunSuite {

  test("xmlFormat2Attributes") {
    val formats = Map("field1" -> BasicAttrFormats.StringXmlAttrFormat, "field2" -> BasicAttrFormats.IntXmlAttrFormat)
    implicit val f = xmlFormat2(formats, Product2)
    val p = Product2("test", 42)
    assertResult(<Product2 field1="test" field2="42"/>) {
      p.toNode
    }
    assertResult(p) {
      <Product2 field1="test" field2="42"/>.convertTo[Product2]
    }
  }

  test("xmlFormat2Elements") {
    val formats = Map("field1" -> BasicElemFormats.StringXmlElemFormat, "field2" -> BasicElemFormats.IntXmlElemFormat)
    implicit val f = xmlFormat2(formats, Product2)
    val p = Product2("test", 42)
    assertResult(<Product2><field1>test</field1><field2>42</field2></Product2>) {
      p.toNode
    }
    assertResult(p) {
      <Product2><field1>test</field1><field2>42</field2></Product2>.convertTo[Product2]
    }
  }

  test("xmlFormat2Mixed") {
    val formats = Map("field1" -> BasicElemFormats.StringXmlElemFormat, "field2" -> BasicAttrFormats.IntXmlAttrFormat)
    implicit val f = xmlFormat2(formats, Product2)
    val p = Product2("test", 42)
    assertResult(<Product2 field2="42"><field1>test</field1></Product2>) {
      p.toNode
    }
    assertResult(p) {
      <Product2 field2="42"><field1>test</field1></Product2>.convertTo[Product2]
    }
  }
}
