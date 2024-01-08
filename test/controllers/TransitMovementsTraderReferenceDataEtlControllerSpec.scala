/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers

import org.apache.pekko.stream.scaladsl.Source
import org.apache.pekko.util.ByteString
import connectors.TransitMovementsTraderReferenceDataEtlConnector
import org.mockito.Mockito.when
import org.scalatest.OptionValues
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import org.scalatestplus.mockito.MockitoSugar.mock
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.http.Status.OK
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.{WSCookie, WSResponse}
import play.api.test.FakeRequest
import play.api.test.Helpers._

import java.net.URI
import scala.concurrent.Future
import scala.xml.Elem

class TransitMovementsTraderReferenceDataEtlControllerSpec extends AnyFreeSpec with Matchers with OptionValues with GuiceOneAppPerSuite {

  private val mockConnector: TransitMovementsTraderReferenceDataEtlConnector = mock[TransitMovementsTraderReferenceDataEtlConnector]

  private class DummyWSResponse(statusCode: Int) extends WSResponse {
    override def status: Int = statusCode
    override def statusText: String = ???
    override def headers: Map[String, Seq[String]] = ???
    override def underlying[T]: T = ???
    override def cookies: Seq[WSCookie] = ???
    override def cookie(name: String): Option[WSCookie] = ???
    override def body: String = ???
    override def bodyAsBytes: ByteString = ???
    override def bodyAsSource: Source[ByteString, _] = ???
    override def allHeaders: Map[String, Seq[String]] = ???
    override def xml: Elem = ???
    override def json: JsValue = ???
    override def uri: URI = ???
  }

  override lazy val app: Application = new GuiceApplicationBuilder()
    .overrides(bind[TransitMovementsTraderReferenceDataEtlConnector].toInstance(mockConnector))
    .build()

  "TransitMovementsTraderReferenceDataEtlController" - {

    "referenceDataImport" - {
      "when OK response" - {
        "must return Ok" in {
          when(mockConnector.referenceDataImport()).thenReturn(Future.successful(new DummyWSResponse(OK)))

          val request =
            FakeRequest(PUT, controllers.routes.TransitMovementsTraderReferenceDataEtlController.referenceDataImport().url)
              .withJsonBody(
                Json.obj()
              )

          val result = route(app, request).value

          status(result) mustEqual OK
        }
      }

      "when anything else" - {
        "must return InternalServerError" in {
          when(mockConnector.referenceDataImport()).thenReturn(Future.successful(new DummyWSResponse(INTERNAL_SERVER_ERROR)))

          val request =
            FakeRequest(PUT, controllers.routes.TransitMovementsTraderReferenceDataEtlController.referenceDataImport().url)
              .withJsonBody(
                Json.obj()
              )

          val result = route(app, request).value

          status(result) mustEqual INTERNAL_SERVER_ERROR
        }
      }
    }
  }

}
