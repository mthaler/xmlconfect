name := "xmlstream"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.7"

organization := "com.mthaler"

organizationHomepage := Some(new URL("http://www.mthaler.com"))

description := "A Scala library for easy and idiomatic XML (de)serialization"

startYear := Some(2015)

licenses := Seq("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))

scalacOptions += "-deprecation"

libraryDependencies += "junit" % "junit" % "4.12" % "test"

libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"

libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.4"

// generate boilerplate
Boilerplate.settings
