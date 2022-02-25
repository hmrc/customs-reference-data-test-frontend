import play.core.PlayVersion.current
import sbt._

object AppDependencies {

  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"             %% "bootstrap-frontend-play-28" % "5.14.0",
    "uk.gov.hmrc"             %% "play-frontend-hmrc"         % "1.14.0-play-28",
    "uk.gov.hmrc"             %% "play-frontend-govuk"        % "2.0.0-play-28"
  )

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"             %% "bootstrap-test-play-28"   % "5.12.0",
    "org.scalatest"           %% "scalatest"                % "3.1.2",
    "org.jsoup"               %  "jsoup"                    % "1.10.2",
    "com.typesafe.play"       %% "play-test"                % current,
    "com.vladsch.flexmark"    %  "flexmark-all"             % "0.35.10",
    "org.scalatestplus.play"  %% "scalatestplus-play"       % "4.0.3",
    "org.scalatestplus"       %% "scalatestplus-mockito"    % "1.0.0-M2",
    "com.github.tomakehurst"  % "wiremock-standalone"       % "2.27.2",
    "org.scalacheck"          %% "scalacheck"               % "1.14.3"
  ).map(_ % Test)
}
