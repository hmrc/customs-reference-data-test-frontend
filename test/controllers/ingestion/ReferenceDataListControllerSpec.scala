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
import org.mockito.ArgumentMatchers.{any, eq as eqTo}
import org.mockito.Mockito.{reset, when}
import org.scalatestplus.mockito.MockitoSugar.mock
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import play.api.Application
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.Json
import play.api.mvc.AnyContentAsJson
import play.api.test.FakeRequest
import play.api.test.Helpers.*
import uk.gov.hmrc.http.HttpResponse

import scala.concurrent.Future

class ReferenceDataListControllerSpec extends SpecBase with ScalaCheckPropertyChecks {

  private val mockConnector: CustomsReferenceDataConnector = mock[CustomsReferenceDataConnector]

  private val testJson = Json.obj("foo" -> "bar")

  private val headers = Seq(
    "Accept" -> "application/vnd.hmrc.2.0+gzip"
  )

  override def beforeEach(): Unit = {
    super.beforeEach()
    reset(mockConnector)
  }

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .overrides(
        bind[CustomsReferenceDataConnector].toInstance(mockConnector)
      )
      .build()

  "post" - {

    def fakeRequest: FakeRequest[AnyContentAsJson] =
      FakeRequest(POST, routes.ReferenceDataListController.post().url)
        .withJsonBody(testJson)
        .withHeaders(headers *)

    "must return Accepted" - {
      "when the data has been validated and processed" in {

        when(mockConnector.postReferenceDataList(eqTo(testJson))(any(), any()))
          .thenReturn(Future.successful(HttpResponse(ACCEPTED, "")))

        val result = route(app, fakeRequest).value

        status(result) mustEqual ACCEPTED
      }
    }

    "must return Bad Request" - {
      "when a validation error occurs" in {

        when(mockConnector.postReferenceDataList(eqTo(testJson))(any(), any()))
          .thenReturn(Future.successful(HttpResponse(BAD_REQUEST, "")))

        val result = route(app, fakeRequest).value

        status(result) mustEqual BAD_REQUEST
      }
    }

    "must return Internal Server Error" - {
      "when the data was not processed successfully" in {

        when(mockConnector.postReferenceDataList(eqTo(testJson))(any(), any()))
          .thenReturn(Future.successful(HttpResponse(INTERNAL_SERVER_ERROR, "")))

        val result = route(app, fakeRequest).value

        status(result) mustEqual INTERNAL_SERVER_ERROR
      }
    }
  }
}
