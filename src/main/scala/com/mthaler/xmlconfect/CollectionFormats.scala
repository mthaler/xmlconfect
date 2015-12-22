package com.mthaler.xmlconfect

import scala.reflect.ClassTag
import scala.xml.{ Node, Elem, Null }
import scala.language.postfixOps

object CollectionFormats {

  /**
   * Supplies the XmlElemFormat for lists.
   */
  implicit def listFormat[T](implicit format: XmlElemFormat[T]) = new XmlElemFormat[List[T]] {
    protected def readElem(node: Node, name: String = "") = node.child.collect { case elem: Elem => format.read(Left(TNode.id(elem))) } toList
    protected override def writeElem(value: List[T], name: String = "") = {
      val children = value.map(format.write(_).left.get.apply)
      elem(name, Null, children)
    }
  }

  /**
   * Supplies the XmlElemFormat for arrays.
   */
  implicit def arrayFormat[T: ClassTag](implicit format: XmlElemFormat[T]) = new XmlElemFormat[Array[T]] {
    protected def readElem(node: Node, name: String = "") = node.child.collect { case elem: Elem => format.read(Left(TNode.id(elem))) } toArray
    protected override def writeElem(value: Array[T], name: String = "") = {
      val children = value.map(format.write(_).left.get.apply)
      elem(name, Null, children)
    }
  }

  import scala.collection.{ immutable => imm }

  implicit def immIterableFormat[T: XmlElemFormat] = viaSeq[imm.Iterable[T], T](seq => imm.Iterable(seq: _*))
  implicit def immSeqFormat[T: XmlElemFormat] = viaSeq[imm.Seq[T], T](seq => imm.Seq(seq: _*))
  implicit def immIndexedSeqFormat[T: XmlElemFormat] = viaSeq[imm.IndexedSeq[T], T](seq => imm.IndexedSeq(seq: _*))
  implicit def immLinearSeqFormat[T: XmlElemFormat] = viaSeq[imm.LinearSeq[T], T](seq => imm.LinearSeq(seq: _*))
  implicit def immSetFormat[T: XmlElemFormat] = viaSeq[imm.Set[T], T](seq => imm.Set(seq: _*))
  implicit def vectorFormat[T: XmlElemFormat] = viaSeq[Vector[T], T](seq => Vector(seq: _*))

  /**
   * An XmlElemFormat construction helper that creates a XmlElemFormat for an iterable type I from a builder function
   * List => I.
   */
  def viaSeq[I <: Iterable[T], T](f: imm.Seq[T] => I)(implicit format: XmlElemFormat[T]): XmlElemFormat[I] = new XmlElemFormat[I] {
    protected def readElem(node: Node, name: String = "") = f(node.child.collect { case elem: Elem => format.read(Left(TNode.id(elem))) } toVector)
    protected override def writeElem(iterable: I, name: String = "") = {
      val children = iterable.toVector.map(format.write(_).left.get.apply)
      elem(name, Null, children)
    }
  }
}
