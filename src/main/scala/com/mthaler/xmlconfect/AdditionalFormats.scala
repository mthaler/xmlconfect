package com.mthaler.xmlconfect

import scala.xml.{ Null, MetaData, Node }

object AdditionalFormats {

  /**
   * Lazy wrapper around serialization. Useful when you want to serialize (mutually) recursive structures.
   */
  def lazyFormat[T](format: => XmlFormat[T]) = new XmlFormat[T] {
    lazy val delegate = format
    def write(x: T, name: String = "") = delegate.write(x, name)
    def read(value: XML, name: String = "") = delegate.read(value, name)
  }

  /**
   * Creates a special format that ignores a value
   */
  def ignoreFormat[T](value: T) = new XmlFormat[T] {

    override def read(xml: Either[Node, MetaData], name: String): T = value

    override def write(obj: T, name: String): Either[Node, MetaData] = Right(Null)
  }
}
