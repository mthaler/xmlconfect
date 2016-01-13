package com.mthaler.xmlconfect

import com.mthaler.xmlconfect.CollectionFormatsTest.Count
import org.scalatest.FunSuite

object CollectionFormatsTest {
  case class Count(count: Int)
}

class CollectionFormatsTest extends FunSuite {

  import CollectionFormatsTest._

  test("hobbyList") {
    case class Hobby(name: String)
    case class Person(name: String, hobbies: List[Hobby])
    import ProductFormatInstances._
    import BasicAttrFormats._
    import CollectionFormats.listFormat
    implicit val hobbyFormat = xmlFormat1(Hobby)
    implicit val personFormat = xmlFormat2(Person)
    val p = Person("Richard Feynman", List(Hobby("Bongo Drums"), Hobby("Hiking")))
    assertResult(<Person name="Richard Feynman"><Hobby name="Bongo Drums"/><Hobby name="Hiking"/></Person>) {
      p.toNode
    }
    assertResult(p) {
      <Person name="Richard Feynman"><Hobby name="Bongo Drums"/><Hobby name="Hiking"/></Person>.convertTo[Person]
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
    case class Person(name: String = "Albert Einstein", age: Int = 42)
    case class Persons(persons: List[Person])
    implicit val pf = ProductFormat.xmlFormat2(Person)
    implicit val lf = WrappedCollectionFormats.listFormat[Person]
    implicit val psf = ProductFormat.xmlFormat(Persons, "Persons")
    val persons = Persons(List(Person("Richard Feynman", 56)))
    assertResult(<Persons><Persons><Person name="Richard Feynman" age="56"/></Persons></Persons>) {
      persons.toNode
    }
    assertResult(Persons(List(Person("Richard Feynman", 56)))) {
      <Persons><Persons><Person name="Richard Feynman" age="56"/></Persons></Persons>.convertTo[Persons]
    }
  }

  test("countImmIterable") {
    import BasicAttrFormats._
    import collection.immutable.Iterable
    implicit val f = ProductFormat.xmlFormat1(Count)
    implicit val lf = CollectionFormats.immIterableFormat[Count]
    val l = Iterable(Count(5), Count(8), Count(42))
    assertResult(<Counts><Count count="5"/><Count count="8"/><Count count="42"/></Counts>) {
      l.toNode("Counts")
    }
    assertResult(l) {
      <Counts><Count count="5"/><Count count="8"/><Count count="42"/></Counts>.convertTo[Iterable[Count]]
    }
    assertResult(<Counts/>) {
      Iterable.empty[Count].toNode("Counts")
    }
    assertResult(Vector.empty[Count]) {
      <Counts/>.convertTo[Iterable[Count]]
    }
  }

  test("countImmSeq") {
    import BasicAttrFormats._
    import collection.immutable.Seq
    implicit val f = ProductFormat.xmlFormat1(Count)
    implicit val lf = CollectionFormats.immSeqFormat[Count]
    val l = Seq(Count(5), Count(8), Count(42))
    assertResult(<Counts><Count count="5"/><Count count="8"/><Count count="42"/></Counts>) {
      l.toNode("Counts")
    }
    assertResult(l) {
      <Counts><Count count="5"/><Count count="8"/><Count count="42"/></Counts>.convertTo[Seq[Count]]
    }
    assertResult(<Counts/>) {
      Seq.empty[Count].toNode("Counts")
    }
    assertResult(Seq.empty[Count]) {
      <Counts/>.convertTo[Seq[Count]]
    }
  }

  test("countImmIndexedSeq") {
    import BasicAttrFormats._
    import collection.immutable.IndexedSeq
    implicit val f = ProductFormat.xmlFormat1(Count)
    implicit val lf = CollectionFormats.immIndexedSeqFormat[Count]
    val l = IndexedSeq(Count(5), Count(8), Count(42))
    assertResult(<Counts><Count count="5"/><Count count="8"/><Count count="42"/></Counts>) {
      l.toNode("Counts")
    }
    assertResult(l) {
      <Counts><Count count="5"/><Count count="8"/><Count count="42"/></Counts>.convertTo[IndexedSeq[Count]]
    }
    assertResult(<Counts/>) {
      IndexedSeq.empty[Count].toNode("Counts")
    }
    assertResult(IndexedSeq.empty[Count]) {
      <Counts/>.convertTo[IndexedSeq[Count]]
    }
  }

  test("countImmLinearSeq") {
    import BasicAttrFormats._
    import collection.immutable.LinearSeq
    implicit val f = ProductFormat.xmlFormat1(Count)
    implicit val lf = CollectionFormats.immLinearSeqFormat[Count]
    val l = LinearSeq(Count(5), Count(8), Count(42))
    assertResult(<Counts><Count count="5"/><Count count="8"/><Count count="42"/></Counts>) {
      l.toNode("Counts")
    }
    assertResult(l) {
      <Counts><Count count="5"/><Count count="8"/><Count count="42"/></Counts>.convertTo[LinearSeq[Count]]
    }
    assertResult(<Counts/>) {
      LinearSeq.empty[Count].toNode("Counts")
    }
    assertResult(LinearSeq.empty[Count]) {
      <Counts/>.convertTo[LinearSeq[Count]]
    }
  }

  test("countImmSet") {
    import BasicAttrFormats._
    import collection.immutable.Set
    implicit val f = ProductFormat.xmlFormat1(Count)
    implicit val lf = CollectionFormats.immSetFormat[Count]
    val l = Set(Count(5), Count(8), Count(42))
    assertResult(<Counts><Count count="5"/><Count count="8"/><Count count="42"/></Counts>) {
      l.toNode("Counts")
    }
    assertResult(l) {
      <Counts><Count count="5"/><Count count="8"/><Count count="42"/></Counts>.convertTo[Set[Count]]
    }
    assertResult(<Counts/>) {
      Set.empty[Count].toNode("Counts")
    }
    assertResult(Set.empty[Count]) {
      <Counts/>.convertTo[Set[Count]]
    }
  }
}

class WrappedCollectionFormatsTest extends FunSuite {

  test("countList") {
    import BasicAttrFormats._
    implicit val f = ProductFormat.xmlFormat1(Count)
    implicit val lf = WrappedCollectionFormats.listFormat[Count]
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
    implicit val lf = WrappedCollectionFormats.arrayFormat[Count]
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

}
