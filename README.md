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
xmlconfect is really easy to use:

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
