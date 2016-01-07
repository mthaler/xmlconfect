package com.mthaler.xmlconfect

import scala.annotation.implicitNotFound
import scala.xml.{ Text, Null, MetaData, Node }

/**
 * Provides the XML deserialization for type T.
 */

@implicitNotFound(msg = "Cannot find XmlReader or XmlFormat type class for ${T}")
trait XmlReader[T] {
  def read(xml: XML, name: String = ""): T
}

/**
 * Provides the XML serialization for type T.
 */
@implicitNotFound(msg = "Cannot find XmlWriter or XmlFormat type class for ${T}")
trait XmlWriter[T] {
  def write(obj: T, name: String = ""): XML
}

/**
 * Provides the XML deserialization and serialization for type T.
 */
trait XmlFormat[T] extends XmlReader[T] with XmlWriter[T]

/**
 * A special XmlReader capable of reading an XML attribute.
 */
@implicitNotFound(msg = "Cannot find XmlAttrReader or XmlAttrFormat type class for ${T}")
trait XmlAttrReader[T] extends XmlReader[T] {

  final def read(xml: XML, name: String = ""): T = xml match {
    case Left(node) => deserializationError("Reading nodes not supported")
    case Right(metaData) => readAttr(metaData, name)
  }

  protected def readAttr(metaData: MetaData, name: String = ""): T
}

/**
 * A special XmlWriter capable of writing an XML attribute.
 */
@implicitNotFound(msg = "Cannot find XmlAttrWriter or XmlAttrFormat type class for ${T}")
trait XmlAttrWriter[T] extends XmlWriter[T] {
  final def write(obj: T, name: String = ""): XML = Right(writeAttr(obj, name))

  protected def writeAttr(obj: T, name: String = ""): MetaData = attribute(name, obj.toString)
}

/**
 * A special XmlFormat signaling that the format produces an XML attribute.
 */
trait XmlAttrFormat[T] extends XmlFormat[T] with XmlAttrReader[T] with XmlAttrWriter[T]

/**
 * A special XmlReader capable of reading an XML element.
 */
@implicitNotFound(msg = "Cannot find XmlElemReader or RootXmlFormat type class for ${T}")
trait XmlElemReader[T] extends XmlReader[T] {

  final def read(xml: XML, name: String = ""): T = xml match {
    case Left(tnode) => readElem(tnode, name)
    case Right(metaData) => deserializationError("Reading attributes not supported")
  }

  protected def readElem(tnode: TNode, name: String): T
}

/**
 * A special XmlWriter capable of writing an XML element.
 */
@implicitNotFound(msg = "Cannot find XmlElemWriter or XmlElemFormat type class for ${T}")
trait XmlElemWriter[T] extends XmlWriter[T] {

  final def write(obj: T, name: String = ""): XML = Left(writeElem0(obj, name))

  protected def writeElem0(obj: T, name: String): TNode
}

/**
 * A special XmlFormat signaling that the format produces an XML element.
 */
trait XmlElemFormat[T] extends XmlFormat[T] with XmlElemReader[T] with XmlElemWriter[T]

trait SimpleXmlElemReader[T] extends XmlElemReader[T] {

  protected final def readElem(tnode: TNode, name: String): T = readElem(tnode.apply, name)

  protected def readElem(node: Node, name: String): T
}

/**
 * A special XmlWriter capable of writing an XML element.
 */
@implicitNotFound(msg = "Cannot find XmlElemWriter or XmlElemFormat type class for ${T}")
trait SimpleXmlElemWriter[T] extends XmlElemWriter[T] {
  protected final def writeElem0(obj: T, name: String): TNode = TNode.id(writeElem(obj, name))

  protected def writeElem(obj: T, name: String = ""): Node = elem(name, Null, Seq(Text(obj.toString)))
}

/**
 * A special XmlFormat signaling that the format produces an XML element.
 */
trait SimpleXmlElemFormat[T] extends XmlElemFormat[T] with SimpleXmlElemReader[T] with SimpleXmlElemWriter[T]