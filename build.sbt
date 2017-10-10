enablePlugins(ScalaJSPlugin)

name := "IcanHazDOM"

version := "20171010"

scalaVersion := "2.12.3"

organization := "se.chimps.js"

credentials += Credentials(Path.userHome / ".ivy2" / ".fuckjs")

publishTo := Some("se.chimps.js" at "http://yamr.kodiak.se/maven")

publishArtifact in (Compile, packageDoc) := false

scalaJSUseMainModuleInitializer := false

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.1"

libraryDependencies += "org.scalatest" %%% "scalatest" % "3.0.1" % "test"
