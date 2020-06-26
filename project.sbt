
organization in Global := "io.verizon.helm"

crossScalaVersions in Global := Seq("2.13.1")

scalaVersion in Global := crossScalaVersions.value.head

scalaTestVersion  := "3.2.0"
scalaCheckVersion := "1.14.2"

lazy val helm = project.in(file(".")).aggregate(core, http4s)

lazy val core = project

lazy val http4s = project dependsOn core

enablePlugins(DisablePublishingPlugin)
