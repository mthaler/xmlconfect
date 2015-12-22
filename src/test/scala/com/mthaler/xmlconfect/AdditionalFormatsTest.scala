package com.mthaler.xmlconfect

import org.scalatest.FunSuite
import ProductFormat._

import scala.xml._

object AdditionalFormatsTest {
  case class Friends(friends: List[String])
  case class Person(name: String, friends: Friends)
}

class AdditionalFormatsTest extends FunSuite {

  import AdditionalFormatsTest._

  test("ignoreFormat") {

    import BasicAttrFormats._
    val p = Person("Albert Einstein", Friends(List("Richard Feynman", "Werner Heisenberg", "Paul Dirac")))
    implicit val friendsFormat = AdditionalFormats.ignoreFormat(Friends(Nil))
    implicit val pf = xmlFormat2(Person)

    assertResult(<Person name="Albert Einstein"/>) {
      p.toNode
    }
    assertResult(Person("Albert Einstein", Friends(Nil))) {
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
    val reader = new XmlElemReader[Int] {
      protected def readElem(node: Node, name: String = ""): Int = node.text.toInt
    }
    val writer = new XmlElemWriter[Int] {}
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
    import ProductFormat.xmlFormat2
    import AdditionalFormats.namedFormat
    import BasicAttrFormats._
    case class Car(name: String, manufacturer: String)
    implicit val f = xmlFormat2(Car)
    val nf = namedFormat(f, "Test")
    val c = Car("Golf", "Volkswagen")
    assertResult(<Car name="Golf" manufacturer="Volkswagen"/>) {
      c.toNode
    }
    assertResult(Car("Golf", "Volkswagen")) {
      <Car name="Golf" manufacturer="Volkswagen"/>.convertTo[Car]
    }
    assertResult(<Test name="Golf" manufacturer="Volkswagen"/>) {
      nf.write(c).left.get.apply
    }
    assertResult(Car("Golf", "Volkswagen")) {
      nf.read(Left(TNode.id(<Test name="Golf" manufacturer="Volkswagen"/>)))
    }
  }
}
