name := "orbicular"

version := "0.0.1"

organization := "de.qx"

scalaVersion := "2.11.6"

publishMavenStyle := true

libraryDependencies ++= Seq(
  "junit"             % "junit"           % "4.12"  % "test"
)

crossPaths := false

autoScalaLibrary := false