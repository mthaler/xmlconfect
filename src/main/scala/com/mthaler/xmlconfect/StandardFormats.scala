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

  implicit def tuple1Format[A](implicit format1: XmlAttrFormat[A]) = new XmlAttrFormat[Tuple1[A]] {

    def read(value: XML, name: String = "") = value match {
      case Left(node) => deserializationError("Reading nodes not supported")
      case md @ Right(metaData) =>
        val a = format1.read(md, "_1")
        Tuple1(a)
    }

    def write(t: Tuple1[A], name: String = "") = format1.write(t._1, "_1")
  }

  implicit def tuple2Format[A, B](implicit format1: XmlAttrFormat[A], format2: XmlAttrFormat[B]) = new XmlAttrFormat[(A, B)] {

    def read(value: XML, name: String = "") = value match {
      case Left(node) => deserializationError("Reading nodes not supported")
      case md @ Right(metaData) =>
        val a = format1.read(md, "_1")
        val b = format2.read(md, "_2")
        (a, b)
    }

    def write(t: (A, B), name: String = "") = Right(format1.write(t._1, "_1").right.get.append(format2.write(t._2, "_2").right.get))
  }

  implicit def tuple3Format[A, B, C](implicit format1: XmlAttrFormat[A], format2: XmlAttrFormat[B], format3: XmlAttrFormat[C]) = new XmlAttrFormat[(A, B, C)] {

    def read(value: XML, name: String = "") = value match {
      case Left(node) => deserializationError("Reading nodes not supported")
      case md @ Right(metaData) =>
        val a = format1.read(md, "_1")
        val b = format2.read(md, "_2")
        val c = format3.read(md, "_3")
        (a, b, c)
    }

    def write(t: (A, B, C), name: String = "") = Right(format1.write(t._1, "_1").right.get.append(format2.write(t._2, "_2").right.get.append(format3.write(t._3, "_3").right.get)))
  }

  implicit def tuple4Format[A, B, C, D](implicit format1: XmlAttrFormat[A], format2: XmlAttrFormat[B], format3: XmlAttrFormat[C], format4: XmlFormat[D]) = new XmlAttrFormat[(A, B, C, D)] {

    def read(value: XML, name: String = "") = value match {
      case Left(node) => deserializationError("Reading nodes not supported")
      case md @ Right(metaData) =>
        val a = format1.read(md, "_1")
        val b = format2.read(md, "_2")
        val c = format3.read(md, "_3")
        val d = format4.read(md, "_4")
        (a, b, c, d)
    }

    def write(t: (A, B, C, D), name: String = "") = Right(format1.write(t._1, "_1").right.get.append(format2.write(t._2, "_2").right.get.append(format3.write(t._3, "_3").right.get).append(format4.write(t._4, "_4").right.get)))
  }
}
