package com.mthaler.xmlconfect

import scala.reflect.ClassTag
import scala.xml.{ Node, Elem, Null }
import scala.language.postfixOps
import scala.language.implicitConversions
import scala.collection.{ immutable => imm }

object CollectionFormats {

  /**
   * Supplies the XmlElemFormat for lists.
   */
  implicit def listFormat[T](implicit format: XmlElemFormat[T]) = new XmlElemFormat[List[T]] {

    override protected def readElem(node: TNode, name: String): List[T] = node.node flatMap (n => n.child.collect { case elem: Elem => format.read(Left(TNode.id(elem))) }) toList

    override protected def writeElem0(value: List[T], name: String): TNode = TNode.id(value.flatMap(format.write(_).left.get.apply))
  }

  /**
   * Supplies the XmlElemFormat for arrays.
   */
  implicit def arrayFormat[T: ClassTag](implicit format: XmlElemFormat[T]) = new XmlElemFormat[Array[T]] {

    override protected def readElem(node: TNode, name: String): Array[T] = node.node.flatMap(n => n.child.collect { case elem: Elem => format.read(Left(TNode.id(elem))) }) toArray

    override protected def writeElem0(value: Array[T], name: String): TNode = TNode.id(value.toSeq.flatMap(format.write(_).left.get.apply))
  }

  implicit def immIterableFormat[T: SimpleXmlElemFormat] = viaSeq[imm.Iterable[T], T](seq => imm.Iterable(seq: _*))
  implicit def immSeqFormat[T: SimpleXmlElemFormat] = viaSeq[imm.Seq[T], T](seq => imm.Seq(seq: _*))
  implicit def immIndexedSeqFormat[T: SimpleXmlElemFormat] = viaSeq[imm.IndexedSeq[T], T](seq => imm.IndexedSeq(seq: _*))
  implicit def immLinearSeqFormat[T: SimpleXmlElemFormat] = viaSeq[imm.LinearSeq[T], T](seq => imm.LinearSeq(seq: _*))
  implicit def immSetFormat[T: SimpleXmlElemFormat] = viaSeq[imm.Set[T], T](seq => imm.Set(seq: _*))
  implicit def vectorFormat[T: SimpleXmlElemFormat] = viaSeq[Vector[T], T](seq => Vector(seq: _*))

  /**
   * An XmlElemFormat construction helper that creates a XmlElemFormat for an iterable type I from a builder function
   * List => I.
   */
  def viaSeq[I <: Iterable[T], T](f: imm.Seq[T] => I)(implicit format: XmlElemFormat[T]): XmlElemFormat[I] = new XmlElemFormat[I] {

    override protected def readElem(node: TNode, name: String = "") = f(node.node.flatMap(n => n.child.collect { case elem: Elem => format.read(Left(TNode.id(elem))) }) toVector)

    override protected def writeElem0(iterable: I, name: String = ""): TNode = {
      TNode.id(iterable.toVector.flatMap(format.write(_).left.get.apply))
    }
  }
}

object WrappedCollectionFormats {

  /**
   * Supplies the XmlElemFormat for lists.
   */
  implicit def listFormat[T](implicit format: XmlElemFormat[T]) = wrappedFormat(CollectionFormats.listFormat[T])

  /**
   * Supplies the XmlElemFormat for arrays.
   */
  implicit def arrayFormat[T: ClassTag](implicit format: XmlElemFormat[T]) = wrappedFormat(CollectionFormats.arrayFormat)

  implicit def immIterableFormat[T: SimpleXmlElemFormat] = viaSeq[imm.Iterable[T], T](seq => imm.Iterable(seq: _*))
  implicit def immSeqFormat[T: SimpleXmlElemFormat] = viaSeq[imm.Seq[T], T](seq => imm.Seq(seq: _*))
  implicit def immIndexedSeqFormat[T: SimpleXmlElemFormat] = viaSeq[imm.IndexedSeq[T], T](seq => imm.IndexedSeq(seq: _*))
  implicit def immLinearSeqFormat[T: SimpleXmlElemFormat] = viaSeq[imm.LinearSeq[T], T](seq => imm.LinearSeq(seq: _*))
  implicit def immSetFormat[T: SimpleXmlElemFormat] = viaSeq[imm.Set[T], T](seq => imm.Set(seq: _*))
  implicit def vectorFormat[T: SimpleXmlElemFormat] = viaSeq[Vector[T], T](seq => Vector(seq: _*))

  def viaSeq[I <: Iterable[T], T](f: imm.Seq[T] => I)(implicit format: XmlElemFormat[T]): XmlElemFormat[I] = wrappedFormat(CollectionFormats.viaSeq(f))

  private def wrappedFormat[T](format: XmlElemFormat[T]) = new SimpleXmlElemFormat[T] {

    protected def readElem(node: Node, name: String = "") = format.read(Left(TNode.id(node)), name)

    protected override def writeElem(value: T, name: String = "") = {

      val result = format.write(value, name)
      elem(name, Null, result.left.get.apply)
    }
  }
}
