val appName = "customs-reference-data-test-frontend"

lazy val microservice = Project(appName, file("."))
  .enablePlugins(
    play.sbt.PlayScala,
    SbtDistributablesPlugin
  )
  .disablePlugins(JUnitXmlReportPlugin)
  .settings(
    majorVersion := 0,
    scalaVersion := "3.5.0",
    libraryDependencies ++= AppDependencies.compile ++ AppDependencies.test,
    PlayKeys.playDefaultPort := 9493,
    scalacOptions += "-Wconf:src=routes/.*:s"
  )
  .settings(inConfig(Test)(testSettings) *)
  .settings(CodeCoverageSettings.settings*)

lazy val testSettings: Seq[Def.Setting[?]] = Seq(
  unmanagedResourceDirectories += baseDirectory.value / "test" / "resources",
  javaOptions ++= Seq(
    "-Dconfig.resource=test.application.conf"
  )
)
