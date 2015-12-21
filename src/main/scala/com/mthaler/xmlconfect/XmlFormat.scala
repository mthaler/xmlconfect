package com.mthaler.xmlconfect

import scala.annotation.implicitNotFound
import scala.xml.{ MetaData, Node }

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
trait XmlAttrReader[T] extends XmlReader[T]

/**
 * A special XmlWriter capable of writing an XML attribute.
 */
@implicitNotFound(msg = "Cannot find XmlAttrWriter or XmlAttrFormat type class for ${T}")
trait XmlAttrWriter[T] extends XmlWriter[T]

/**
 * A special XmlFormat signaling that the format produces an XML attribute.
 */
trait XmlAttrFormat[T] extends XmlFormat[T] with XmlAttrReader[T] with XmlAttrWriter[T]

/**
 * A special XmlReader capable of reading an XML element.
 */
@implicitNotFound(msg = "Cannot find XmlElemReader or RootXmlFormat type class for ${T}")
trait XmlElemReader[T] extends XmlReader[T]

/**
 * A special XmlWriter capable of writing an XML element.
 */
@implicitNotFound(msg = "Cannot find XmlElemWriter or RootJsonFormat type class for ${T}")
trait XmlElemWriter[T] extends XmlWriter[T]

/**
 * A special XmlFormat signaling that the format produces an XML element.
 */
trait XmlElemFormat[T] extends XmlFormat[T] with XmlElemReader[T] with XmlElemWriter[T]