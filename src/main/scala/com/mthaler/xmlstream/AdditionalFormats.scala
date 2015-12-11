package com.mthaler.xmlstream

object AdditionalFormats {

  /**
   * Lazy wrapper around serialization. Useful when you want to serialize (mutually) recursive structures.
   */
  def lazyFormat[T](format: => XmlFormat[T]) = new XmlFormat[T] {
    lazy val delegate = format
    def write(x: T, name: String = "") = delegate.write(x, name)
    def read(value: XML, name: String = "") = delegate.read(value, name)
  }
}
