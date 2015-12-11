package com.mthaler.xmlstream

import org.junit.Assert._
import org.junit.Test



object CollectionFormatTest {
  case class Count(count: Int)
}

class CollectionFormatTest {

  import CollectionFormatTest._

  @Test
  def testIntListFormat(): Unit = {
    import BasicAttrFormats._
    implicit val f = ProductFormat.xmlFormat1(Count)
    val lf = CollectionFormats.listFormat[Count]
    val l = List(Count(5), Count(8), Count(42))
    val result0 = lf.write(l, "Counts")
    assertEquals(Left(<Counts><Count count="5"/><Count count="8"/><Count count="42"/></Counts>), result0)
    val result1 = lf.read(result0)
    assertEquals(l, result1)
    val result2 = lf.write(Nil, "Counts")
    assertEquals(Left(<Counts/>), result2)
    val result3 = lf.read(result2)
    assertEquals(Nil, result3)
  }

  @Test
  def testIntArrayFormat(): Unit = {
    import BasicAttrFormats._
    implicit val f = ProductFormat.xmlFormat1(Count)
    val lf = CollectionFormats.arrayFormat[Count]
    val l = Array(Count(5), Count(8), Count(42))
    val result0 = lf.write(l, "Counts")
    assertEquals(Left(<Counts><Count count="5"/><Count count="8"/><Count count="42"/></Counts>), result0)
    val result1 = lf.read(result0)
    assertEquals(l.toSeq, result1.toSeq)
    val result2 = lf.write(Array.empty, "Counts")
    assertEquals(Left(<Counts/>), result2)
    val result3 = lf.read(result2)
    assertEquals(Nil, result3.toSeq)
  }

  @Test
  def testIntVectorFormat(): Unit = {
    import BasicAttrFormats._
    implicit val f = ProductFormat.xmlFormat1(Count)
    val lf = CollectionFormats.vectorFormat[Count]
    val l = Vector(Count(5), Count(8), Count(42))
    val result0 = lf.write(l, "Counts")
    assertEquals(Left(<Counts><Count count="5"/><Count count="8"/><Count count="42"/></Counts>), result0)
    val result1 = lf.read(result0)
    assertEquals(l, result1)
    val result2 = lf.write(Vector.empty, "Counts")
    assertEquals(Left(<Counts/>), result2)
    val result3 = lf.read(result2)
    assertEquals(Nil, result3)
  }
}
