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

    override def read(xml: XML, name: String): T = value

    override def write(obj: T, name: String): XML = Right(Null)
  }

  /**
   * Constructs a XmlFormat from its two parts, JsonReader and JsonWriter.
   */
  def xmlFormat[T](reader: XmlReader[T], writer: XmlWriter[T]) = new XmlFormat[T] {

    def write(obj: T, name: String = "") = writer.write(obj, name)

    def read(xml: XML, name: String = "") = reader.read(xml, name)
  }

  /**
   * Constructs a XmlAttrFormat from its two parts, XmlAttrReader and XmlAttrWriter.
   */
  def xmlAttrFormat[T](reader: XmlAttrReader[T], writer: XmlAttrWriter[T]) = new XmlAttrFormat[T] {

    protected override def writeAttr(obj: T, name: String = ""): MetaData = writer.write(obj, name).right.get

    protected def readAttr(metaData: MetaData, name: String = ""): T = reader.read(Right(metaData), name)
  }

  /**
   * Constructs a XmlElemFormat from its two parts, XmlElemReader and XmlElemWriter.
   */
  def xmlElemFormat[T](reader: XmlElemReader[T], writer: XmlElemWriter[T]) = new XmlElemFormat[T] {

    protected override def writeElem0(obj: T, name: String = "") = writer.write(obj, name).left.get

    protected def readElem(tnode: TNode, name: String = ""): T = reader.read(Left(tnode), name)
  }

  def namedFormat[T](format: XmlElemFormat[T], name: String) = new XmlElemFormat[T] {

    override protected def readElem(tnode: TNode, n: String): T = format.read(Left(tnode), name)

    override protected def writeElem0(obj: T, n: String): TNode = format.write(obj, name).left.get
  }

  def namedFormat[T](format: XmlElemFormat[T], name: String, transform: Node => Node) = new XmlElemFormat[T] {

    override protected def readElem(tnode: TNode, n: String): T = format.read(Left(TNode(tnode.node, transform)), name)

    override protected def writeElem0(obj: T, n: String): TNode = format.write(obj, name).left.get
  }

}
