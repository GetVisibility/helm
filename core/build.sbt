
scalaTestVersion  := "3.2.0"
libraryDependencies ++= Seq(
  "io.argonaut"                %% "argonaut"          % "6.3.0",
  "org.typelevel"              %% "cats-free"         % "2.1.1",
  "org.typelevel"              %% "cats-effect"       % "2.1.3",
  "org.scalacheck"             %% "scalacheck"        % "1.14.0"
)

addCompilerPlugin("org.typelevel" % "kind-projector" % "0.10.3" cross CrossVersion.binary)
