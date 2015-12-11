package com.mthaler.xmlstream

import scala.xml.Null

object StandardFormats {

  private type XF[T] = XmlFormat[T]

  implicit def optionFormat[T](implicit format: XmlFormat[T]) = new XmlFormat[Option[T]] {
    def read(xml: XML, name: String = "") = xml match {
      case Left(node) => throw new UnsupportedOperationException("not supported")
      case Right(metaData) => metaData.get(name) match {
        case Some(result) => Some(format.read(xml, name))
        case None => None
      }
    }
    def write(value: Option[T], name: String = "") = value match {
      case Some(obj) => format.write(obj, name)
      case None => Right(Null)
    }
  }
}
