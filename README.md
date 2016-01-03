[![Build Status](https://travis-ci.org/mthaler/xmlconfect.png)](https://travis-ci.org/mthaler/xmlconfect)
[![codecov.io](http://codecov.io/github/mthaler/xmlconfect/coverage.svg?branch=master)](http://codecov.io/github/mthaler/xmlconfect?branch=master)

_xmlconfect_ is a type class based library to serialize Scala classes, in particular case classes, to XML / deserialize XML to Scala classes

It sports the following features:

* Based on Scala XML
* Type-class based (de)serialization of custom objects
* Supports reading / writing attributes or elements for basic types
* No external dependencies

## Installation

## Usage
_xmlconfect_ is really easy to use. In the following we show how to serialize / deserialize a simple class `case class Person(name: String, age: Int)`:

```scala
import com.mthaler.xmlconfect._
import com.mthaler.xmlconfect.BasicAttrFormats._
import com.mthaler.xmlconfect.ProductFormatInstances._
implicit val f = xmlFormat2(Person)
val p = Person("Albert Einstein", 42)
p.toNode
```
This will create an XML element with two attributes, name and age:

```xml
<Person name="Albert Einstein" age="42"/>
```

It is just as easy to deserialize XML:

```scala
import com.mthaler.xmlconfect._
import com.mthaler.xmlconfect.BasicAttrFormats._
import com.mthaler.xmlconfect.ProductFormatInstances._
implicit val f = xmlFormat2(Person)
<Person name="Albert Einstein" age="42"/>.convertTo[Person]
```

This will result in:

```scala
Person("Albert Einstein", 42)
```

To make it clear what happens in the above examples, we walk through them step by step. The first line

```scala
import com.mthaler.xmlconfect._
```

brings the `toNode` and the `convertTo` methods into scope. 

```scala
import com.mthaler.xmlconfect.BasicAttrFormats._
```
brings attribute formats for basic Scala types like int, double and String into scope.

```scala
import com.mthaler.xmlconfect.ProductFormatInstances._
```
imports all methods from ProductFormatInstances which are used to create formats for case classes.

```scala
implicit val f = xmlFormat2(Person)
```
defines a format for the Person class.

```scala
val p = Person("Albert Einstein", 42)
p.toNode
```
creates a new person instance and calls the `toNode` method to serialize it to XML. Instead of using implicits we could also pass the format directly using `p.toNode(f)`.

```scala
<Person name="Albert Einstein" age="42"/>.convertTo[Person]
```
deserializes a class. Again, instead of using implicits, we could pass the format directly using `<Person name="Albert Einstein" age="42"/>.convertTo[Person](f)`.

###Elements and Attriutes
XML offers two ways to store data: elements and attributes. In the above example we used attributes to store the name and the age. But we could also use child elements to store them. _xmlconfect_ supports both:

```scala
import com.mthaler.xmlconfect._
import com.mthaler.xmlconfect.BasicElemFormats._
import com.mthaler.xmlconfect.ProductFormatInstances._
implicit val f = xmlFormat2(Person)
val p = Person("Albert Einstein", 42)
p.toNode
```
This will create an XML element with two children, name and age:

```xml
<Person><name>Albert Einstein</name><age>42</age></Person>
```
The only difference is, that we import `com.mthaler.xmlconfect.BasicElemFormats._` instead of `com.mthaler.xmlconfect.BasicAttrFormats._`. To deserialize the class, we do

```scala
import com.mthaler.xmlconfect._
import com.mthaler.xmlconfect.BasicElemFormats._
import com.mthaler.xmlconfect.ProductFormatInstances._
implicit val f = xmlFormat2(Person)
<Person><name>Albert Einstein</name><age>42</age></Person>.convertTo[Person]
```
Again, the only difference is that we import `com.mthaler.xmlconfect.BasicElemFormats._` instead of `com.mthaler.xmlconfect.BasicAttrFormats._`.

###Default values
Sometimes we need to provide default values if e.g. an attribute is not present in XML. This could be the case if we save a config as XML and than read the config with a newer version of the program that added an aditional field to a class. For case classes, it is possible to use case class default values for missing attributes / elements. If we change our Person class to `case class Person(name: String = "Name", age: Int = 21)`

the following code

```scala
import com.mthaler.xmlconfect._
import com.mthaler.xmlconfect.BasicAttrFormats._
import com.mthaler.xmlconfect.ProductFormatInstances._
implicit val f = xmlFormat2(Person)
<Person name="Albert Einstein"/>.convertTo[Person]
```

will result in 

```scala
Person(Albert Einstein, 21)
```

__Important:___ this will only work if default values are specified for all fields of a case class.

###Collections
It is quite common to serialize / deserialize collections of objects. It is easy to do this using _xmlconfect_:

```scala
import com.mthaler.xmlconfect._
import com.mthaler.xmlconfect.BasicAttrFormats._
import com.mthaler.xmlconfect.ProductFormatInstances._
import com.mthaler.xmlconfect.CollectionFormats.listFormat
implicit val f = xmlFormat2(Person)
val persons = List(Person("Albert Einstein", 42), Person("Richard Feyman", 28))
persons.toNode("Persons")
```
The result is
```xml
<Persons><Person name="Albert Einstein" age="42"/><Person name="Richard Feyman" age="28"/></Persons>
```

It is just as easy to deserialize the persons list:
```scala
import com.mthaler.xmlconfect._
import com.mthaler.xmlconfect.BasicAttrFormats._
import com.mthaler.xmlconfect.ProductFormatInstances._
import com.mthaler.xmlconfect.CollectionFormats.listFormat
implicit val f = xmlFormat2(Person)
<Persons>
  <Person name="Albert Einstein" age="42"/>
  <Person name="Richard Feyman" age="28"/>
</Persons>.convertTo[List[Person]]
```
In addition to the formats used to serialize / deserialize a person instance, we now import `com.mthaler.xmlconfect.CollectionFormats.listFormat` which offers a format to serialize / deserialize lists. There are formats for several collection types:

* List
* Array
* immutable Iterable
* immutable Seq
* immutable IndexedSeq
* immutable LinearSeq
* immutable Set
* Vector

There is an overloaded version of toNode that allows us to specify a name for the element:
```scala
val persons = List(Person("Albert Einstein", 42), Person("Richard Feyman", 28))
persons.toNode("Persons")
```
We have to use the overloaded version here, otherwise the top-level element will have an empty name which is not valid XML.

###Renaming elements
It is sometimes necessary to rename elements, e.g. we have a list of Integer classes, but want to rename Integer to Int in the resulting XML. This is easy to do with _xmlconfect_:
```scala
import com.mthaler.xmlconfect._
import com.mthaler.xmlconfect.BasicAttrFormats._
import com.mthaler.xmlconfect.ProductFormat._
implicit val namedIntFormat = AdditionalFormats.namedFormat(xmlFormat1(Integer), "Int")
implicit val intListFomat = CollectionFormats.listFormat[Integer]
List(Integer(5), Integer(10), Integer(15)).toNode("Integers")
```
The result is:
```xml
<Integers><Int value="5"/><Int value="10"/><Int value="15"/></Integers>
```
### Custom classes

It is easy to define custom formats with _xmlconfect_. To serialize / deserialize `java.awt.Color` we could use the following format:

```scala
import com.mthaler.xmlconfect._
import java.awt.Color
import scala.xml.Node
import scala.xml.Null
import scala.xml.Text

implicit object ColorElemFormat extends SimpleXmlElemFormat[Color] {
    protected def readElem(node: Node, name: String = ""): Color = {
      val r = (node \ "@r").text.toInt
      val g = (node \ "@g").text.toInt
      val b = (node \ "@b").text.toInt
      new Color(r, g, b)
    }
    protected override def writeElem(color: Color, name: String = ""): Node = 
    <Color r={ color.getRed.toString } g={ color.getGreen.toString } b={ color.getBlue.toString }/>
  }
```
`Color.red.toNode("Color")` would then result in `<Color r="255" g="0" b="0"/>` and `<Color r="255" g="0" b="0"/>.convertTo[Color]` would create a Color object representing the color red.

##Credits
Most of the code is inspired by (or just copied from) the excellent [spray-json](https://github.com/spray/spray-json) library.

##License
xmlconfect is licensed under [APL 2.0](http://www.apache.org/licenses/LICENSE-2.0).
