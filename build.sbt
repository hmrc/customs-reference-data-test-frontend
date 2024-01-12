import uk.gov.hmrc.DefaultBuildSettings.integrationTestSettings
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin.publishingSettings

val appName = "customs-reference-data-test-frontend"

lazy val microservice = Project(appName, file("."))
  .enablePlugins(
    play.sbt.PlayScala,
    SbtAutoBuildPlugin,
    SbtDistributablesPlugin
  )
  .disablePlugins(JUnitXmlReportPlugin)
  .settings(
    majorVersion := 0,
    scalaVersion := "2.13.12",
    libraryDependencies ++= AppDependencies.compile ++ AppDependencies.test,
    PlayKeys.playDefaultPort := 9493,
    scalacOptions += "-Wconf:src=routes/.*:s"
  )
  .settings(inConfig(Test)(testSettings): _*)
  .configs(IntegrationTest)
  .settings(integrationTestSettings(): _*)
  .settings(resolvers += Resolver.jcenterRepo)

lazy val testSettings: Seq[Def.Setting[_]] = Seq(
  unmanagedResourceDirectories += baseDirectory.value / "test" / "resources",
  javaOptions ++= Seq(
    "-Dconfig.resource=test.application.conf"
  )
)
