ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "scala-check-demos"
  )

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.17.0"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.15"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test"
libraryDependencies += "org.scalatestplus" %% "scalacheck-1-14" % "3.2.2.0" % "test"
