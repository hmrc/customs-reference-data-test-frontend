import sbt.*

object AppDependencies {

  private val bootstrapVersion = "9.14.0"

  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc" %% "bootstrap-frontend-play-30" % bootstrapVersion
  )

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"            %% "bootstrap-test-play-30" % bootstrapVersion,
    "org.scalatest"          %% "scalatest"              % "3.2.19",
    "org.mockito"            %  "mockito-core"           % "5.18.0",
    "org.scalatestplus"      %% "mockito-5-12"           % "3.2.19.0",
    "org.scalacheck"         %% "scalacheck"             % "1.18.1",
    "org.scalatestplus"      %% "scalacheck-1-18"        % "3.2.19.0",
    "org.jsoup"              %  "jsoup"                  % "1.21.1"
  ).map(_ % Test)
}
