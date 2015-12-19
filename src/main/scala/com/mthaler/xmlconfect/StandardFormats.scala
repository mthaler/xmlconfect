package com.mthaler.xmlconfect

import scala.xml.{ TopScope, Elem, Null }

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

  implicit def tuple1Format[A](implicit format1: XmlFormat[A]) = new XmlElemFormat[Tuple1[A]] {

    def read(value: XML, name: String = "") = value match {
      case Left(node) =>
        val a = format1 match {
          case _: XmlAttrFormat[_] => format1.read(Right(node.attributes), "_1")
          case _ => format1.read(Left((node \ "_1").head))
        }
        Tuple1(a)
      case Right(metaData) => deserializationError("Reading attributes not supported")
    }

    def write(t: Tuple1[A], name: String = "") = {
      format1.write(t._1, "_1") match {
        case Left(node) => elem(name, Null, Seq(node))
        case Right(metaData) => elem(name, metaData, Nil)
      }
    }
  }

  implicit def tuple2Format[A, B](implicit format1: XmlFormat[A], format2: XmlFormat[B]) = new XmlElemFormat[(A, B)] {

    def read(value: XML, name: String = "") = value match {
      case Left(node) =>
        val a = format1 match {
          case _: XmlAttrFormat[_] => format1.read(Right(node.attributes), "_1")
          case _ => format1.read(Left((node \ "_1").head))
        }
        val b = format2 match {
          case _: XmlAttrFormat[_] => format2.read(Right(node.attributes), "_2")
          case _ => format2.read(Left((node \ "_2").head))
        }
        (a, b)
      case Right(metaData) => deserializationError("Reading attributes not supported")
    }

    def write(t: (A, B), name: String = "") = {
      var result = Elem(null, name, Null, TopScope)
      format1.write(t._1, "_1") match {
        case Left(node) => result = result.copy(child = result.child :+ node)
        case Right(metaData) => result = result % metaData
      }
      format2.write(t._2, "_2") match {
        case Left(node) => result = result.copy(child = result.child :+ node)
        case Right(metaData) => result = result % metaData
      }
      Left(result)
    }
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
