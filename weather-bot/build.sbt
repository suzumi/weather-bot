import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT"
    )),

    name := "Hello",

    libraryDependencies ++= Seq(
      scalaTest % Test,
      "org.json4s" %% "json4s-native" % "3.5.0",
      "org.skinny-framework" %% "skinny-http-client" % "2.3.3"
    )
  )
