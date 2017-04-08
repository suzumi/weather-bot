import Dependencies._

lazy val root = (project in file(".")).
  enablePlugins(JavaAppPackaging).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT"
    )),

    name := "WeatherBot",

    libraryDependencies ++= Seq(
      scalaTest % Test,
      "org.json4s" %% "json4s-native" % "3.5.0",
      "org.skinny-framework" %% "skinny-http-client" % "2.3.3",
      "joda-time" % "joda-time" % "2.9.2", // 使ってない
      "org.joda" % "joda-convert" % "1.8",
      "com.enragedginger" %% "akka-quartz-scheduler" % "1.6.0-akka-2.4.x"
    )
  )
