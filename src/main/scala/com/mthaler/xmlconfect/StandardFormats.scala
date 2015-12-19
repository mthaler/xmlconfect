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

  implicit def eitherFormat[A, B](implicit format1: XmlFormat[A], format2: XmlFormat[B]) = new XmlElemFormat[Either[A, B]] {

    def read(value: XML, name: String = "") = value match {
      case Left(node) =>
        val left = format1 match {
          case _: XmlAttrFormat[_] => node.attribute("left") match {
            case Some(_) => Some(format1.read(Right(node.attributes), "left"))
            case None => None
          }
          case _ => (node \ "left").headOption match {
            case Some(n) => Some(format1.read(Left(n)))
            case None => None
          }
        }
        val right = format2 match {
          case _: XmlAttrFormat[_] => node.attribute("right") match {
            case Some(_) => Some(format2.read(Right(node.attributes), "right"))
            case None => None
          }
          case _ => (node \ "right").headOption match {
            case Some(n) => Some(format2.read(Left(n)))
            case None => None
          }
        }
        (left, right) match {
          case (None, None) => deserializationError("Could not read left nor right")
          case (Some(l), None) => Left(l)
          case (None, Some(r)) => Right(r)
          case (Some(l), Some(r)) => deserializationError("Both left=" + l + " and right=" + r + " present, this should not happen")
        }
      case Right(metaData) => deserializationError("Reading attributes not supported")
    }

    def write(e: Either[A, B], name: String = "") = {
      e match {
        case Left(value) => format1.write(value, "left") match {
          case Left(node) => elem(name, Null, Seq(node))
          case Right(metaData) => elem(name, metaData, Nil)
        }
        case Right(value) => format2.write(value, "right") match {
          case Left(node) => elem(name, Null, Seq(node))
          case Right(metaData) => elem(name, metaData, Nil)
        }
      }
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
      var result = emptyElem(name)
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

  implicit def tuple3Format[A, B, C](implicit format1: XmlFormat[A], format2: XmlFormat[B], format3: XmlFormat[C]) = new XmlElemFormat[(A, B, C)] {

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
        val c = format3 match {
          case _: XmlAttrFormat[_] => format3.read(Right(node.attributes), "_3")
          case _ => format3.read(Left((node \ "_3").head))
        }
        (a, b, c)
      case Right(metaData) => deserializationError("Reading attributes not supported")
    }

    def write(t: (A, B, C), name: String = "") = {
      var result = emptyElem(name)
      format1.write(t._1, "_1") match {
        case Left(node) => result = result.copy(child = result.child :+ node)
        case Right(metaData) => result = result % metaData
      }
      format2.write(t._2, "_2") match {
        case Left(node) => result = result.copy(child = result.child :+ node)
        case Right(metaData) => result = result % metaData
      }
      format3.write(t._3, "_3") match {
        case Left(node) => result = result.copy(child = result.child :+ node)
        case Right(metaData) => result = result % metaData
      }
      Left(result)
    }
  }

  implicit def tuple4Format[A, B, C, D](implicit format1: XmlFormat[A], format2: XmlFormat[B], format3: XmlFormat[C], format4: XmlFormat[D]) = new XmlElemFormat[(A, B, C, D)] {

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
        val c = format3 match {
          case _: XmlAttrFormat[_] => format3.read(Right(node.attributes), "_3")
          case _ => format3.read(Left((node \ "_3").head))
        }
        val d = format4 match {
          case _: XmlAttrFormat[_] => format4.read(Right(node.attributes), "_4")
          case _ => format4.read(Left((node \ "_4").head))
        }
        (a, b, c, d)
      case Right(metaData) => deserializationError("Reading attributes not supported")
    }

    def write(t: (A, B, C, D), name: String = "") = {
      var result = emptyElem(name)
      format1.write(t._1, "_1") match {
        case Left(node) => result = result.copy(child = result.child :+ node)
        case Right(metaData) => result = result % metaData
      }
      format2.write(t._2, "_2") match {
        case Left(node) => result = result.copy(child = result.child :+ node)
        case Right(metaData) => result = result % metaData
      }
      format3.write(t._3, "_3") match {
        case Left(node) => result = result.copy(child = result.child :+ node)
        case Right(metaData) => result = result % metaData
      }
      format4.write(t._4, "_4") match {
        case Left(node) => result = result.copy(child = result.child :+ node)
        case Right(metaData) => result = result % metaData
      }
      Left(result)
    }
  }

  private def emptyElem(name: String) = Elem(null, name, Null, TopScope, true)
}
