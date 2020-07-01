package com.mthaler.xmlconfect

import com.mthaler.xmlconfect.CollectionFormatsTest.Count
import org.scalatest.funsuite.AnyFunSuite

object CollectionFormatsTest {
  case class Count(count: Int)
}

class CollectionFormatsTest extends AnyFunSuite {

  import CollectionFormatsTest._

  test("hobbyList") {
    case class Hobby(name: String)
    case class Person(name: String, hobbies: List[Hobby])
    import ProductFormatInstances._
    import BasicAttrFormats._
    import CollectionFormats.listFormat
    import AdditionalFormats.namedFormat
    implicit val hobbyFormat = namedFormat(xmlFormat1(Hobby))
    implicit val personFormat = xmlFormat2(Person)
    val p = Person("Richard Feynman", List(Hobby("Bongo Drums"), Hobby("Hiking")))
    assertResult(<Person name="Richard Feynman"><Hobby name="Bongo Drums"/><Hobby name="Hiking"/></Person>) {
      p.toNode
    }
    assertResult(p) {
      <Person name="Richard Feynman"><Hobby name="Bongo Drums"/><Hobby name="Hiking"/></Person>.convertTo[Person]
    }
  }

  test("hobbyFriendList") {
    case class Hobby(name: String)
    case class Friend(name: String)
    case class Person(name: String, hobbies: List[Hobby], friends: List[Friend])
    import ProductFormatInstances._
    import BasicAttrFormats._
    import CollectionFormats.listFormat
    import AdditionalFormats.namedFormat
    implicit val hobbyFormat = namedFormat(xmlFormat1(Hobby))
    implicit val friendFormat = namedFormat(xmlFormat1(Friend))
    implicit val personFormat = xmlFormat3(Person)
    val p = Person("Richard Feynman", List(Hobby("Bongo Drums"), Hobby("Hiking")), List(Friend("Freeman Dyson")))
    assertResult(<Person name="Richard Feynman"><Hobby name="Bongo Drums"/><Hobby name="Hiking"/><Friend name="Freeman Dyson"/></Person>) {
      p.toNode
    }
    assertResult(p) {
      <Person name="Richard Feynman"><Hobby name="Bongo Drums"/><Hobby name="Hiking"/><Friend name="Freeman Dyson"/></Person>.convertTo[Person]
    }
  }

  test("hobbyBuddyList") {
    case class Hobby(name: String)
    case class Friend(name: String)
    case class Person(name: String, hobbies: List[Hobby], friends: List[Friend])
    import ProductFormatInstances._
    import BasicAttrFormats._
    import CollectionFormats.listFormat
    import AdditionalFormats.namedFormat
    implicit val hobbyFormat = namedFormat(xmlFormat1(Hobby))
    implicit val friendFormat = namedFormat(xmlFormat1(Friend), "Buddy")
    implicit val personFormat = xmlFormat3(Person)
    val p = Person("Richard Feynman", List(Hobby("Bongo Drums"), Hobby("Hiking")), List(Friend("Freeman Dyson")))
    assertResult(<Person name="Richard Feynman"><Hobby name="Bongo Drums"/><Hobby name="Hiking"/><Buddy name="Freeman Dyson"/></Person>) {
      p.toNode
    }
    assertResult(p) {
      <Person name="Richard Feynman"><Hobby name="Bongo Drums"/><Hobby name="Hiking"/><Buddy name="Freeman Dyson"/></Person>.convertTo[Person]
    }
  }

  test("countVector") {
    import BasicAttrFormats._
    import AdditionalFormats.namedFormat
    implicit val f = namedFormat(ProductFormat.xmlFormat1(Count))
    implicit val lf = WrappedCollectionFormats.vectorFormat[Count]()
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
    import AdditionalFormats._
    case class Person(name: String = "Albert Einstein", age: Int = 42)
    case class Persons(persons: List[Person])
    implicit val pf = namedFormat(ProductFormat.xmlFormat2(Person))
    implicit val lf = WrappedCollectionFormats.listFormat[Person]("Persons")
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
    import AdditionalFormats.namedFormat
    import collection.immutable.Iterable
    implicit val f = namedFormat(ProductFormat.xmlFormat1(Count))
    implicit val lf = WrappedCollectionFormats.immIterableFormat[Count]()
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
    import AdditionalFormats.namedFormat
    import collection.immutable.Seq
    implicit val f = namedFormat(ProductFormat.xmlFormat1(Count))
    implicit val lf = WrappedCollectionFormats.immSeqFormat[Count]()
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
    import AdditionalFormats.namedFormat
    import collection.immutable.IndexedSeq
    implicit val f = namedFormat(ProductFormat.xmlFormat1(Count))
    implicit val lf = WrappedCollectionFormats.immIndexedSeqFormat[Count]()
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
    import AdditionalFormats.namedFormat
    import collection.immutable.LinearSeq
    implicit val f = namedFormat(ProductFormat.xmlFormat1(Count))
    implicit val lf = WrappedCollectionFormats.immLinearSeqFormat[Count]()
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
    import AdditionalFormats._
    import collection.immutable.Set
    implicit val f = namedFormat(ProductFormat.xmlFormat1(Count))
    implicit val lf = WrappedCollectionFormats.immSetFormat[Count]()
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

class WrappedCollectionFormatsTest extends AnyFunSuite {

  test("countList") {
    import BasicAttrFormats._
    import AdditionalFormats.namedFormat
    implicit val f = namedFormat(ProductFormat.xmlFormat1(Count))
    implicit val lf = WrappedCollectionFormats.listFormat[Count]()
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
    import AdditionalFormats.namedFormat
    implicit val f = namedFormat(ProductFormat.xmlFormat1(Count))
    implicit val lf = WrappedCollectionFormats.arrayFormat[Count]()
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

  test("serializeDeserializeXML") {
    case class Hobby(name: String)
    case class Person(name: String, hobbies: List[Hobby])
    import ProductFormatInstances._
    import BasicAttrFormats._
    import CollectionFormats.listFormat
    import AdditionalFormats.namedFormat
    implicit val hobbyFormat = namedFormat(xmlFormat1(Hobby))
    implicit val personFormat = xmlFormat2(Person)
    val p = Person("Richard Feynman", List(Hobby("Bongo Drums"), Hobby("Hiking")))
    val xml = p.toNode
    val result = SerializationTestHelper.serializeDeserialize(xml)
    assert(result === xml)
  }

  test("serializeDeserializeXML2") {
    import BasicAttrFormats._
    import AdditionalFormats.namedFormat
    implicit val f = namedFormat(ProductFormat.xmlFormat1(Count))
    implicit val lf = WrappedCollectionFormats.listFormat[Count]()
    val l = List(Count(5), Count(8), Count(42))
    val xml = l.toNode("Test")
    val result = SerializationTestHelper.serializeDeserialize(xml)
    assert(xml === result)
  }
}
