package com.mthaler.xmlconfect

import com.mthaler.xmlconfect.CustomProductFormat.xmlFormat2
import com.mthaler.xmlconfect.ProductFormatTest.Product2
import org.scalatest.FunSuite

class CustomProductFormatTest extends FunSuite {

  test("xmlFormat2") {
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
}
