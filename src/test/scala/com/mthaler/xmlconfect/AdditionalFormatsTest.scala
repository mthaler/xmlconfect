package com.mthaler.xmlconfect

import org.scalatest.FunSuite
import ProductFormat._

object AdditionalFormatsTest {
  case class Friends(friends: List[String])
  case class Person(name: String, friends: Friends)
}

class AdditionalFormatsTest extends FunSuite {

  import AdditionalFormatsTest._

  test("ignoreFormat") {

    import BasicAttrFormats._
    val p = Person("Albert Einstein", Friends(List("Richard Feynman", "Werner Heisenberg", "Paul Dirac")))
    implicit val friendsFormat = AdditionalFormats.ignoreFormat(Friends(Nil))
    implicit val pf = xmlFormat2(Person)

    assertResult(Left(<Person name="Albert Einstein"/>)) {
      pf.write(p)
    }
    assertResult(Person("Albert Einstein", Friends(Nil))) {
      pf.read(Left(<Person name="Albert Einstein"/>))
    }
  }
}
