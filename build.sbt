organization in ThisBuild := "helm"
scalaVersion in ThisBuild := "2.13.3"
version in ThisBuild := "4.0.0-SNAPSHOT"

// Publishing-related settings:
credentials in ThisBuild += Credentials(Path.userHome / ".ivy2" / "gv-credentials")

publishTo in ThisBuild := Some("Getvisibility artefacts" at "https://registry2.getvisibility.com/artifactory/ivy-dev/")

lazy val `helm` = (project in file("."))
  .aggregate(`core`, `http4s`)

lazy val `core` = project
  .settings(
    libraryDependencies ++= Seq(
      "io.argonaut" %% "argonaut" % "6.3.0",
      "org.typelevel" %% "cats-free" % "2.1.1",
      "org.typelevel" %% "cats-effect" % "2.1.3",
      "org.scalacheck" %% "scalacheck" % "1.14.3"
    )
  )

val http4sOrg = "org.http4s"
val http4sVersion = "0.21.5"
val dockeritVersion = "0.9.9"

lazy val `http4s` = project
  .settings(
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      http4sOrg %% "http4s-blaze-client" % http4sVersion,
      http4sOrg %% "http4s-argonaut" % http4sVersion,
      "com.whisk" %% "docker-testkit-scalatest" % dockeritVersion % "test",
      "com.whisk" %% "docker-testkit-impl-docker-java" % dockeritVersion % "test"
    )
  ).dependsOn(`core`)
