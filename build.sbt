
name := """sql-injection-scw"""

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq()

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.11"

routesGenerator := InjectedRoutesGenerator

libraryDependencies += jdbc

libraryDependencies += "com.typesafe.play" %% "anorm" % "2.5.1"
