package com.mthaler

import scala.xml.{ Text, Attribute, MetaData, Node, Null, Elem, TopScope }
import scala.language.implicitConversions

package object xmlconfect {

  type XML = Either[Node, MetaData]

  def deserializationError(msg: String, cause: Throwable = null, fieldNames: List[String] = Nil) = throw new DeserializationException(msg, cause, fieldNames)
  def serializationError(msg: String) = throw new SerializationException(msg)

  def attribute(name: String, value: String) = Right(Attribute(name, Text(value.toString), Null))
  def elem(name: String, attributes: MetaData, children: Seq[Node]) = Left(Elem(null, name, attributes, TopScope, children: _*))

  def xmlReader[T](implicit reader: XmlReader[T]) = reader
  def xmlWriter[T](implicit writer: XmlWriter[T]) = writer

  implicit def pimpElem(elem: Elem) = new PimpedElem(elem)
}

package xmlconfect {
  case class DeserializationException(msg: String, cause: Throwable = null, fieldNames: List[String] = Nil) extends RuntimeException(msg, cause)

  class SerializationException(msg: String) extends RuntimeException(msg)

  private[xmlconfect] class PimpedElem(val elem: Elem) extends AnyVal {

    def convertTo[T](implicit reader: XmlElemReader[T]): T = reader.read(Left(elem))
  }
}