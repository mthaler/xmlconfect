package com.mthaler.xmlconfect

import ProductFormat._
import org.scalatest.funsuite.AnyFunSuite
import scala.xml._

object AdditionalFormatsTest {
  case class Friend(name: String)
  case class Person(name: String, friends: List[Friend])
  case class Integer(value: Int)
  case class Researcher(name: String, institution: Institution)
  case class Institution(name: String)
}

class AdditionalFormatsTest extends AnyFunSuite {

  import AdditionalFormatsTest._

  test("ignoreFormat") {

    import BasicAttrFormats._
    val p = Person("Albert Einstein", List(Friend("Richard Feynman"), Friend("Werner Heisenberg"), Friend("Paul Dirac")))
    implicit val friendsFormat = AdditionalFormats.ignoreFormat(List.empty[Friend])
    implicit val pf = xmlFormat2(Person)

    assertResult(<Person name="Albert Einstein"/>) {
      p.toNode
    }
    assertResult(Person("Albert Einstein", List.empty[Friend])) {
      <Person name="Albert Einstein"/>.convertTo[Person]
    }
  }

  test("xmlFormatFromReaderWriter") {
    val reader = new XmlReader[Int] {
      override def read(xml: XML, name: String) = xml match {
        case Left(node) => deserializationError("Reading nodes not supported")
        case Right(metaData) => metaData(name).text.toInt
      }
    }
    val writer = new XmlWriter[Int] {
      override def write(obj: Int, name: String): XML = Right(attribute(name, obj.toString))
    }
    import AdditionalFormats.xmlFormat
    val format = xmlFormat(reader, writer)
    assertResult(42) {
      format.read(Right(Attribute("value", Text("42"), Null)), "value")
    }
    assertResult(Right(Attribute("value", Text("42"), Null))) {
      format.write(42, "value")
    }
    intercept[DeserializationException] {
      format.read(Left(TNode.id(<value>42</value>)))
    }
  }

  test("xmlAttrFormatFromReaderWriter") {
    val reader = new XmlAttrReader[Int] {
      def readAttr(metaData: MetaData, name: String = ""): Int = metaData(name).text.toInt
    }
    val writer = new XmlAttrWriter[Int] {}
    import AdditionalFormats.xmlAttrFormat
    val format = xmlAttrFormat(reader, writer)
    assertResult(42) {
      format.read(Right(Attribute("value", Text("42"), Null)), "value")
    }
    assertResult(Right(Attribute("value", Text("42"), Null))) {
      format.write(42, "value")
    }
    intercept[DeserializationException] {
      format.read(Left(TNode.id(<value>42</value>)))
    }
  }

  test("xmlElemFormatFromReaderWriter") {
    val reader = new SimpleXmlElemReader[Int] {
      protected def readElem(node: Node, name: String = ""): Int = node.text.toInt
    }
    val writer = new SimpleXmlElemWriter[Int] {}
    import AdditionalFormats.xmlElemFormat
    implicit val format = xmlElemFormat(reader, writer)
    assertResult(42) {
      <value>42</value>.convertTo[Int]
    }
    assertResult(<value>42</value>) {
      42.toNode("value")
    }
    intercept[DeserializationException] {
      format.read(Right(Attribute("value", Text("42"), Null)))
    }
  }

  test("namedFormat") {
    import BasicAttrFormats._
    import ProductFormat._
    import AdditionalFormats.namedFormat
    implicit val friendFormat = namedFormat(xmlFormat1(Friend))
    implicit val friendsFormat = WrappedCollectionFormats.listFormat[Friend]("Buddies")
    implicit val f = xmlFormat2(Person)
    val p = Person("Albert Einstein", List(Friend("Richard Feynman"), Friend("Werner Heisenberg"), Friend("Paul Dirac")))
    assertResult(<Person name="Albert Einstein"><Buddies><Friend name="Richard Feynman"/><Friend name="Werner Heisenberg"/><Friend name="Paul Dirac"/></Buddies></Person>) {
      p.toNode
    }
    assertResult(p) {
      <Person name="Albert Einstein"><Buddies><Friend name="Richard Feynman"/><Friend name="Werner Heisenberg"/><Friend name="Paul Dirac"/></Buddies></Person>.convertTo[Person]
    }
  }

  test("namedFormat2") {
    import BasicAttrFormats._
    import ProductFormat._
    import AdditionalFormats.namedFormat
    implicit val institutionFormat = namedFormat(xmlFormat1(Institution), "Institute")
    implicit val researcherFormat = xmlFormat2(Researcher)
    val researcher = Researcher("Albert Einstein", Institution("Institute of Advanced Studies"))
    assertResult(<Researcher name="Albert Einstein"><Institute name="Institute of Advanced Studies"/></Researcher>) {
      researcher.toNode
    }
    assertResult(researcher) {
      <Researcher name="Albert Einstein"><Institute name="Institute of Advanced Studies"/></Researcher>.convertTo[Researcher]
    }
  }
}
