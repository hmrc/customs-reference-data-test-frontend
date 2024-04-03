import sbt._

object AppDependencies {

  private val bootstrapVersion = "8.3.0"
  private val pekkoVersion = "1.0.1"

  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc" %% "bootstrap-frontend-play-30" % bootstrapVersion
  )

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"            %% "bootstrap-test-play-30" % bootstrapVersion,
    "org.scalatest"          %% "scalatest"              % "3.2.17",
    "org.mockito"            %  "mockito-core"           % "4.11.0",
    "org.scalatestplus"      %% "mockito-4-11"           % "3.2.17.0",
    "org.scalacheck"         %% "scalacheck"             % "1.17.0",
    "org.scalatestplus"      %% "scalacheck-1-17"        % "3.2.17.0",
    "org.jsoup"              %  "jsoup"                  % "1.15.3",
    "org.apache.pekko"       %% "pekko-testkit"          % pekkoVersion,
    "org.apache.pekko"       %% "pekko-stream-testkit"   % pekkoVersion
  ).map(_ % Test)
}
