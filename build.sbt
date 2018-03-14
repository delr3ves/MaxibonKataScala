scalaVersion := "2.12.4"

name := "MaxibonKataScala"
organization := "com.emaginalabs"
version := "1.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.5" % "test"

logBuffered in Test := false


addCommandAlias("format", ";scalafmt;test:scalafmt")
