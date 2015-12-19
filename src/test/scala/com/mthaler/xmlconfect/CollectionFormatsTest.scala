package com.mthaler.xmlconfect

import org.scalatest.FunSuite

object CollectionFormatsTest {
  case class Count(count: Int)
  case class Person(name: String = "Albert Einstein", age: Int = 42)
  case class Persons(persons: List[Person])
}

class CollectionFormatsTest extends FunSuite {

  import CollectionFormatsTest._

  test("countList") {
    import BasicAttrFormats._
    implicit val f = ProductFormat.xmlFormat1(Count)
    implicit val lf = CollectionFormats.listFormat[Count]
    val l = List(Count(5), Count(8), Count(42))
    assertResult(<Counts><Count count="5"/><Count count="8"/><Count count="42"/></Counts>) {
      l.toNode("Counts")
    }
    assertResult(l) {
      <Counts><Count count="5"/><Count count="8"/><Count count="42"/></Counts>.convertTo[List[Count]]
    }
    assertResult(<Counts/>) {
      List.empty[Count].toNode("Counts")
    }
    assertResult(Nil) {
      <Counts/>.convertTo[List[Count]]
    }
  }

  test("countArray") {
    import BasicAttrFormats._
    implicit val f = ProductFormat.xmlFormat1(Count)
    implicit val lf = CollectionFormats.arrayFormat[Count]
    val l = Array(Count(5), Count(8), Count(42))
    assertResult(<Counts><Count count="5"/><Count count="8"/><Count count="42"/></Counts>) {
      l.toNode("Counts")
    }
    assertResult(l) {
      <Counts><Count count="5"/><Count count="8"/><Count count="42"/></Counts>.convertTo[Array[Count]]
    }
    assertResult(<Counts/>) {
      Array.empty[Count].toNode("Counts")
    }
    assertResult(Array.empty[Count]) {
      <Counts/>.convertTo[Array[Count]]
    }
  }

  test("countVector") {
    import BasicAttrFormats._
    implicit val f = ProductFormat.xmlFormat1(Count)
    implicit val lf = CollectionFormats.vectorFormat[Count]
    val l = Vector(Count(5), Count(8), Count(42))
    assertResult(<Counts><Count count="5"/><Count count="8"/><Count count="42"/></Counts>) {
      l.toNode("Counts")
    }
    assertResult(l) {
      <Counts><Count count="5"/><Count count="8"/><Count count="42"/></Counts>.convertTo[Vector[Count]]
    }
    assertResult(<Counts/>) {
      Vector.empty[Count].toNode("Counts")
    }
    assertResult(Vector.empty[Count]) {
      <Counts/>.convertTo[Vector[Count]]
    }
  }

  test("personList") {
    import BasicAttrFormats._
    implicit val pf = ProductFormat.xmlFormat2(Person)
    implicit val lf = CollectionFormats.listFormat[Person]
    implicit val psf = ProductFormat.xmlFormat(Persons, "Persons")
    val persons = Persons(List(Person("Richard Feynman", 56)))
    assertResult(<Persons><Persons><Person name="Richard Feynman" age="56"/></Persons></Persons>) {
      persons.toNode
    }
    assertResult(Persons(List(Person("Richard Feynman", 56)))) {
      <Persons><Persons><Person name="Richard Feynman" age="56"/></Persons></Persons>.convertTo[Persons]
    }
  }
}
