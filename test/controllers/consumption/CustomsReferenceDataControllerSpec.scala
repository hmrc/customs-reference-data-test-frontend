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

package controllers.consumption

import base.SpecBase
import connectors.CustomsReferenceDataConnector
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.{any, eq as eqTo}
import org.mockito.Mockito.{reset, verify, when}
import org.scalacheck.Gen
import org.scalatestplus.mockito.MockitoSugar.mock
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import play.api.Application
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers.*
import uk.gov.hmrc.http.{HeaderCarrier, HttpResponse}

import scala.concurrent.Future

class CustomsReferenceDataControllerSpec extends SpecBase with ScalaCheckPropertyChecks {

  private val mockConnector = mock[CustomsReferenceDataConnector]

  private val bearerToken = "ABC"

  private val listName = "List Name"

  override lazy val app: Application = new GuiceApplicationBuilder()
    .overrides(bind[CustomsReferenceDataConnector].toInstance(mockConnector))
    .build()

  override def beforeEach(): Unit = {
    super.beforeEach()
    reset(mockConnector)
  }

  "CustomsReferenceDataController" - {
    "get" - {
      "must return Ok when get successful" - {
        "without query parameter" in {
          val json = Json.obj("foo" -> "bar")

          when(mockConnector.getList(any(), any())(any(), any()))
            .thenReturn(Future.successful(HttpResponse(OK, json, Map.empty)))

          val request = FakeRequest(GET, routes.CustomsReferenceDataController.get(listName).url)
            .withHeaders(
              "Authorization" -> bearerToken
            )

          val result = route(app, request).value

          status(result) mustEqual OK

          val headerCarrierCaptor: ArgumentCaptor[HeaderCarrier] = ArgumentCaptor.forClass(classOf[HeaderCarrier])
          verify(mockConnector).getList(eqTo(listName), eqTo(Map.empty))(any(), headerCarrierCaptor.capture())
          headerCarrierCaptor.getValue.authorization.value.value mustEqual bearerToken
        }

        "with query parameter" in {
          val json = Json.obj("foo" -> "bar")

          when(mockConnector.getList(any(), any())(any(), any()))
            .thenReturn(Future.successful(HttpResponse(OK, json, Map.empty)))

          val url = s"${routes.CustomsReferenceDataController.get(listName).url}?countryId=GB"

          val request = FakeRequest(GET, url)
            .withHeaders(
              "Authorization" -> bearerToken
            )

          val result = route(app, request).value

          status(result) mustEqual OK

          val headerCarrierCaptor: ArgumentCaptor[HeaderCarrier] = ArgumentCaptor.forClass(classOf[HeaderCarrier])
          verify(mockConnector).getList(eqTo(listName), eqTo(Map("countryId" -> Seq("GB"))))(any(), headerCarrierCaptor.capture())
          headerCarrierCaptor.getValue.authorization.value.value mustEqual bearerToken
        }
      }

      "must return InternalServerError when get unsuccessful" in {
        forAll(Gen.choose(400, 599).retryUntil(_ != 404)) {
          errorCode =>
            beforeEach()

            when(mockConnector.getList(any(), any())(any(), any()))
              .thenReturn(Future.successful(HttpResponse(errorCode, "")))

            val request = FakeRequest(GET, routes.CustomsReferenceDataController.get(listName).url)
              .withHeaders(
                "Authorization" -> bearerToken
              )

            val result = route(app, request).value

            status(result) mustEqual INTERNAL_SERVER_ERROR

            val headerCarrierCaptor: ArgumentCaptor[HeaderCarrier] = ArgumentCaptor.forClass(classOf[HeaderCarrier])
            verify(mockConnector).getList(eqTo(listName), eqTo(Map.empty))(any(), headerCarrierCaptor.capture())
            headerCarrierCaptor.getValue.authorization.value.value mustEqual bearerToken
        }
      }

      "must return NotFound when get returns 404" in {
        when(mockConnector.getList(any(), any())(any(), any()))
          .thenReturn(Future.successful(HttpResponse(NOT_FOUND, "")))

        val request = FakeRequest(GET, routes.CustomsReferenceDataController.get(listName).url)
          .withHeaders(
            "Authorization" -> bearerToken
          )

        val result = route(app, request).value

        status(result) mustEqual NOT_FOUND

        val headerCarrierCaptor: ArgumentCaptor[HeaderCarrier] = ArgumentCaptor.forClass(classOf[HeaderCarrier])
        verify(mockConnector).getList(eqTo(listName), eqTo(Map.empty))(any(), headerCarrierCaptor.capture())
        headerCarrierCaptor.getValue.authorization.value.value mustEqual bearerToken
      }
    }
  }
}
