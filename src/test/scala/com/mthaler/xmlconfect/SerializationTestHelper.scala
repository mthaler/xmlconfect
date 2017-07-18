package com.mthaler.xmlconfect

import java.io.{ ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream }

object SerializationTestHelper {

  /**
   * Helper method to serialize / deserialize an object using Java serialization,
   * useful for testing if an object is actually serializable
   *
   * @param obj object to be serialized / deserialized
   * @tparam T type of the object
   * @return resulting object
   */
  def serializeDeserialize[T](obj: T): T = {
    val bout = new ByteArrayOutputStream()
    val out = new ObjectOutputStream(bout)
    out.writeObject(obj)
    val bin = new ByteArrayInputStream(bout.toByteArray)
    val in = new ObjectInputStream(bin)
    in.readObject().asInstanceOf[T]
  }
}
