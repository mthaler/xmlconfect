package com.mthaler.xmlstream

import org.scalatest.FunSuite

object CollectionFormatsTest {
  case class Count(count: Int)
  case class Person(name: String = "Albert Einstein", age: Int = 42)
  case class Persons(persons: List[Person])
}

class CollectionFormatsTest extends FunSuite {

  import CollectionFormatsTest._

  test("intList") {
    import BasicAttrFormats._
    implicit val f = ProductFormat.xmlFormat1(Count)
    val lf = CollectionFormats.listFormat[Count]
    val l = List(Count(5), Count(8), Count(42))
    val result0 = lf.write(l, "Counts")
    assert(Left(<Counts><Count count="5"/><Count count="8"/><Count count="42"/></Counts>) == result0)
    val result1 = lf.read(result0)
    assert(l == result1)
    val result2 = lf.write(Nil, "Counts")
    assert(Left(<Counts/>) == result2)
    val result3 = lf.read(result2)
    assert(Nil == result3)
  }

  test("intArray") {
    import BasicAttrFormats._
    implicit val f = ProductFormat.xmlFormat1(Count)
    val lf = CollectionFormats.arrayFormat[Count]
    val l = Array(Count(5), Count(8), Count(42))
    val result0 = lf.write(l, "Counts")
    assert(Left(<Counts><Count count="5"/><Count count="8"/><Count count="42"/></Counts>) == result0)
    val result1 = lf.read(result0)
    assert(l.toSeq == result1.toSeq)
    val result2 = lf.write(Array.empty, "Counts")
    assert(Left(<Counts/>) == result2)
    val result3 = lf.read(result2)
    assert(Nil == result3.toSeq)
  }

  test("intVector") {
    import BasicAttrFormats._
    implicit val f = ProductFormat.xmlFormat1(Count)
    val lf = CollectionFormats.vectorFormat[Count]
    val l = Vector(Count(5), Count(8), Count(42))
    val result0 = lf.write(l, "Counts")
    assert(Left(<Counts><Count count="5"/><Count count="8"/><Count count="42"/></Counts>) == result0)
    val result1 = lf.read(result0)
    assert(l == result1)
    val result2 = lf.write(Vector.empty, "Counts")
    assert(Left(<Counts/>) == result2)
    val result3 = lf.read(result2)
    assert(Nil == result3)
  }

  test("personList") {
    import BasicAttrFormats._
    implicit val pf = ProductFormat.xmlFormat2(Person)
    implicit val lf = CollectionFormats.listFormat[Person]
    val psf = ProductFormat.xmlFormat(Persons, "Persons")
    val persons = Persons(List(Person("Richard Feynman", 56)))
    assertResult(Left(<Persons><Persons><Person name="Richard Feynman" age="56"/></Persons></Persons>)) {
      psf.write(persons)
    }
    assertResult(Persons(List(Person("Richard Feynman", 56)))) {
      psf.read(Left(<Persons><Persons><Person name="Richard Feynman" age="56"/></Persons></Persons>))
    }
  }
}
