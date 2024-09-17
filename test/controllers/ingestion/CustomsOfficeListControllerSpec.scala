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

package controllers.ingestion

import base.SpecBase
import connectors.CustomsReferenceDataConnector
import org.mockito.ArgumentMatchers.{any, eq => eqTo}
import org.mockito.Mockito.{reset, verifyNoInteractions, when}
import org.scalatestplus.mockito.MockitoSugar.mock
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import play.api.Application
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.Json
import play.api.mvc.{AnyContentAsJson, AnyContentAsXml}
import play.api.test.FakeRequest
import play.api.test.Helpers.*
import uk.gov.hmrc.http.HttpResponse
import utils.XmlToJsonConverter.CustomsOfficeListXmlToJsonConverter

import scala.concurrent.Future

class CustomsOfficeListControllerSpec extends SpecBase with ScalaCheckPropertyChecks {

  private val mockXmlToJsonConverter: CustomsOfficeListXmlToJsonConverter = mock[CustomsOfficeListXmlToJsonConverter]

  private val mockConnector: CustomsReferenceDataConnector = mock[CustomsReferenceDataConnector]

  private val testXml =
    <foo>
      bar
    </foo>

  private val testJson = Json.obj("foo" -> "bar")

  private val headers = Seq(
    "Accept" -> "application/vnd.hmrc.2.0+gzip"
  )

  override def beforeEach(): Unit = {
    super.beforeEach()
    reset(mockXmlToJsonConverter)
    reset(mockConnector)
  }

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .overrides(
        bind[CustomsOfficeListXmlToJsonConverter].toInstance(mockXmlToJsonConverter),
        bind[CustomsReferenceDataConnector].toInstance(mockConnector)
      )
      .build()

  "post" - {

    def fakeRequest: FakeRequest[AnyContentAsJson] =
      FakeRequest(POST, routes.CustomsOfficeListController.post().url)
        .withJsonBody(testJson)
        .withHeaders(headers *)

    "must return Accepted" - {
      "when the data has been validated and processed" in {

        when(mockConnector.postCustomsOfficeLists(eqTo(testJson))(any(), any()))
          .thenReturn(Future.successful(HttpResponse(ACCEPTED, "")))

        val result = route(app, fakeRequest).value

        status(result) mustBe ACCEPTED
        
        verifyNoInteractions(mockXmlToJsonConverter)
      }
    }

    "must return Bad Request" - {
      "when a validation error occurs" in {

        when(mockConnector.postCustomsOfficeLists(eqTo(testJson))(any(), any()))
          .thenReturn(Future.successful(HttpResponse(BAD_REQUEST, "")))

        val result = route(app, fakeRequest).value

        status(result) mustBe BAD_REQUEST

        verifyNoInteractions(mockXmlToJsonConverter)
      }
    }

    "must return Internal Server Error" - {
      "when the data was not processed successfully" in {

        when(mockConnector.postCustomsOfficeLists(eqTo(testJson))(any(), any()))
          .thenReturn(Future.successful(HttpResponse(INTERNAL_SERVER_ERROR, "")))

        val result = route(app, fakeRequest).value

        status(result) mustBe INTERNAL_SERVER_ERROR

        verifyNoInteractions(mockXmlToJsonConverter)
      }
    }
  }

  "convert" - {

    def fakeRequest: FakeRequest[AnyContentAsXml] =
      FakeRequest(POST, routes.CustomsOfficeListController.convert().url)
        .withXmlBody(testXml)
        .withHeaders(headers *)

    "must return Ok" in {
      
      when(mockXmlToJsonConverter.convert(eqTo(testXml)))
        .thenReturn(testJson)

      val result = route(app, fakeRequest).value

      status(result) mustBe OK
      contentAsJson(result) mustBe testJson

      verifyNoInteractions(mockConnector)
    }
  }
}
