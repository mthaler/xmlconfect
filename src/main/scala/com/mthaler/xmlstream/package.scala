package com.mthaler

import scala.xml.{ Text, Attribute, MetaData, Node, Null, Elem, TopScope }

package object xmlstream {

  type XML = Either[Node, MetaData]

  def deserializationError(msg: String, cause: Throwable = null, fieldNames: List[String] = Nil) = throw new DeserializationException(msg, cause, fieldNames)
  def serializationError(msg: String) = throw new SerializationException(msg)

  def attribute(name: String, value: String) = Right(Attribute(name, Text(value.toString), Null))
  def elem(name: String, attributes: MetaData, children: Seq[Node]) = Left(Elem(null, name, attributes, TopScope, children: _*))

  def xmlReader[T](implicit reader: XmlReader[T]) = reader
  def xmlWriter[T](implicit writer: XmlWriter[T]) = writer
}

package xmlstream {
  case class DeserializationException(msg: String, cause: Throwable = null, fieldNames: List[String] = Nil) extends RuntimeException(msg, cause)

  class SerializationException(msg: String) extends RuntimeException(msg)
}