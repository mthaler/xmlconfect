package com.mthaler.xmlconfect

import scala.xml.Null

/**
 * Provides the XmlFormats for the non-collection standard types.
 */
object StandardFormats {

  private type XF[T] = XmlFormat[T]

  /**
   * Provides the XmlFormat for the non-collection standard types.
   */
  implicit def optionFormat[T](implicit format: XmlFormat[T]) = new XmlFormat[Option[T]] {
    def read(xml: XML, name: String = "") = xml match {
      case Left(node) => deserializationError("Reading nodes not supported")
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
