import play.core.PlayVersion.current
import sbt._

object AppDependencies {

  private val bootstrapVersion = "7.12.0"

  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc" %% "bootstrap-frontend-play-28" % bootstrapVersion
  )

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"            %% "bootstrap-test-play-28" % bootstrapVersion,
    "org.scalatest"          %% "scalatest"              % "3.2.15",
    "com.typesafe.play"      %% "play-test"              % current,
    "org.mockito"            %  "mockito-core"           % "4.11.0",
    "org.scalatestplus"      %% "mockito-4-6"            % "3.2.15.0",
    "org.jsoup"              %  "jsoup"                  % "1.15.3",
    "com.github.tomakehurst" %  "wiremock-standalone"    % "2.27.2",
    "com.vladsch.flexmark"   %  "flexmark-all"           % "0.62.2"
  ).map(_ % Test)
}
