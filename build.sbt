name := "orbicular"

version := "0.0.2"

organization := "de.qx"

scalaVersion := "2.11.6"

publishMavenStyle := true

publishTo := Some(Resolver.file("file", new File("./target")))

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.12" % "test"
)

crossPaths := false

autoScalaLibrary := false