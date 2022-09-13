import uk.gov.hmrc.DefaultBuildSettings.integrationTestSettings
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin.publishingSettings

val appName = "customs-reference-data-test-frontend"

val silencerVersion = "1.7.9"

lazy val microservice = Project(appName, file("."))
  .enablePlugins(
    play.sbt.PlayScala,
    SbtAutoBuildPlugin,
    SbtGitVersioning,
    SbtDistributablesPlugin
  )
  .disablePlugins(JUnitXmlReportPlugin)
  .settings(
    majorVersion := 0,
    scalaVersion := "2.12.15",
    libraryDependencies ++= AppDependencies.compile ++ AppDependencies.test,
    PlayKeys.playDefaultPort := 9493,
    // ***************
    // Use the silencer plugin to suppress warnings
    // You may turn it on for `views` too to suppress warnings from unused imports in compiled twirl templates, but this will hide other warnings.
    scalacOptions += "-P:silencer:pathFilters=routes",
    libraryDependencies ++= Seq(
      compilerPlugin(
        "com.github.ghik" % "silencer-plugin" % silencerVersion cross CrossVersion.full
      ),
      "com.github.ghik" % "silencer-lib" % silencerVersion % Provided cross CrossVersion.full
    )
    // ***************
  )
  .settings(publishingSettings: _*)
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
