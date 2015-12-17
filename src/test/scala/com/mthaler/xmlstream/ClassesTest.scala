package com.mthaler.xmlstream

import org.scalatest.FunSuite

object ClassesTest {
  case class Person(name: String = "Albert Einstein", age: Int = 42)
}

class ClassesTest extends FunSuite {

  import ClassesTest._

  test("newDefault") {
    val p = Classes.newDefault[Person]
    assert(p == Person())
  }
}
