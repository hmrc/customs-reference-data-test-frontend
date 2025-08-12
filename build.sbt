import uk.gov.hmrc.DefaultBuildSettings

val appName = "customs-reference-data-test-frontend"

ThisBuild / majorVersion := 0
ThisBuild / scalaVersion := "3.5.0"
ThisBuild / scalafmtOnCompile := true

lazy val microservice = Project(appName, file("."))
  .enablePlugins(
    play.sbt.PlayScala,
    SbtDistributablesPlugin
  )
  .disablePlugins(JUnitXmlReportPlugin)
  .settings(
    libraryDependencies ++= AppDependencies.compile ++ AppDependencies.test,
    PlayKeys.playDefaultPort := 9493,
    scalacOptions += "-Wconf:src=routes/.*:s"
  )
  .settings(CodeCoverageSettings.settings*)

lazy val it = project
  .enablePlugins(PlayScala)
  .dependsOn(microservice % "test->test") // the "test->test" allows reusing test code and test dependencies
  .settings(
    libraryDependencies ++= AppDependencies.test,
    DefaultBuildSettings.itSettings()
  )
