package com.mthaler.xmlconfect

import com.mthaler.xmlconfect
import org.scalatest.FunSuite
import ProductFormat._

import scala.xml._

object AdditionalFormatsTest {
  case class Friend(name: String)
  case class Person(name: String, friends: List[Friend])
  case class Integer(value: Int)
}

class AdditionalFormatsTest extends FunSuite {

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
    implicit val intFormat = xmlFormat1(Integer)
    implicit val intListFomat = WrappedCollectionFormats.listFormat[Integer]()
    assertResult(<Integers><Integer value="5"/><Integer value="10"/><Integer value="15"/></Integers>) {
      List(Integer(5), Integer(10), Integer(15)).toNode("Integers")
    }
    assertResult(List(Integer(5), Integer(10), Integer(15))) {
      <Integers><Integer value="5"/><Integer value="10"/><Integer value="15"/></Integers>.convertTo[List[Integer]]
    }
    val namedIntFormat = AdditionalFormats.namedFormat(xmlFormat1(Integer), "Int")
    val namedIntListFomat = WrappedCollectionFormats.listFormat[Integer]()(namedIntFormat)
    assertResult(<Integers><Int value="5"/><Int value="10"/><Int value="15"/></Integers>) {
      List(Integer(5), Integer(10), Integer(15)).toNode("Integers")(namedIntListFomat)
    }
    assertResult(List(Integer(5), Integer(10), Integer(15))) {
      <Integers><Int value="5"/><Int value="10"/><Int value="15"/></Integers>.convertTo[List[Integer]](namedIntListFomat)
    }
  }

  test("namedFormat2") {
    import BasicAttrFormats._
    import ProductFormat._
    implicit val friendFormat = xmlFormat1(Friend)
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
}
