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

import base.SpecBase
import connectors.CustomsReferenceDataConnector
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{reset, verify, verifyNoInteractions, when}
import org.scalacheck.Gen
import org.scalatestplus.mockito.MockitoSugar.mock
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import play.api.Application
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.FakeRequest
import play.api.test.Helpers.{ACCEPTED, _}
import uk.gov.hmrc.http.{HeaderCarrier, HttpResponse}

import scala.concurrent.Future

class CustomsReferenceDataControllerSpec extends SpecBase with ScalaCheckPropertyChecks {

  private val mockConnector = mock[CustomsReferenceDataConnector]

  private val bearerToken = "ABC"

  override lazy val app: Application = new GuiceApplicationBuilder()
    .overrides(bind[CustomsReferenceDataConnector].toInstance(mockConnector))
    .build()

  override def beforeEach(): Unit = {
    super.beforeEach()
    reset(mockConnector)
  }

  "CustomsReferenceDataController" - {
    "referenceDataListPost" - {
      "must return Accepted when post successful" in {
        when(mockConnector.referenceDataListPost(any(), any())(any(), any()))
          .thenReturn(Future.successful(HttpResponse(ACCEPTED, "")))

        val request = FakeRequest(POST, routes.CustomsReferenceDataController.referenceDataListPost().url)
          .withHeaders(
            "Authorization" -> bearerToken,
            "Content-Type" -> "application/json"
          )

        val result = route(app, request).value

        status(result) mustEqual ACCEPTED

        val headerCarrierCaptor: ArgumentCaptor[HeaderCarrier] = ArgumentCaptor.forClass(classOf[HeaderCarrier])
        verify(mockConnector).referenceDataListPost(any(), any())(any(), headerCarrierCaptor.capture())
        headerCarrierCaptor.getValue.authorization.value.value mustBe bearerToken
      }

      "must return InternalServerError when post unsuccessful" in {
        forAll(Gen.choose(400, 599)) {
          errorCode =>
            beforeEach()

            when(mockConnector.referenceDataListPost(any(), any())(any(), any()))
              .thenReturn(Future.successful(HttpResponse(errorCode, "")))

            val request = FakeRequest(POST, routes.CustomsReferenceDataController.referenceDataListPost().url)
              .withHeaders(
                "Authorization" -> bearerToken,
                "Content-Type" -> "application/json"
              )

            val result = route(app, request).value

            status(result) mustEqual INTERNAL_SERVER_ERROR

            val headerCarrierCaptor: ArgumentCaptor[HeaderCarrier] = ArgumentCaptor.forClass(classOf[HeaderCarrier])
            verify(mockConnector).referenceDataListPost(any(), any())(any(), headerCarrierCaptor.capture())
            headerCarrierCaptor.getValue.authorization.value.value mustBe bearerToken
        }
      }

      "must return BadRequest when Content-Type missing" in {
        when(mockConnector.referenceDataListPost(any(), any())(any(), any()))
          .thenReturn(Future.successful(HttpResponse(ACCEPTED, "")))

        val request = FakeRequest(POST, routes.CustomsReferenceDataController.referenceDataListPost().url)
          .withHeaders(
            "Authorization" -> bearerToken
          )

        val result = route(app, request).value

        status(result) mustEqual BAD_REQUEST

        verifyNoInteractions(mockConnector)
      }
    }

    "customsOfficeListPost" - {
      "must return Accepted when post successful" in {
        when(mockConnector.customsOfficeListPost(any(), any())(any(), any()))
          .thenReturn(Future.successful(HttpResponse(ACCEPTED, "")))

        val request = FakeRequest(POST, routes.CustomsReferenceDataController.customsOfficeListPost().url)
          .withHeaders(
            "Authorization" -> bearerToken,
            "Content-Type" -> "application/json"
          )

        val result = route(app, request).value

        status(result) mustEqual ACCEPTED

        val headerCarrierCaptor: ArgumentCaptor[HeaderCarrier] = ArgumentCaptor.forClass(classOf[HeaderCarrier])
        verify(mockConnector).customsOfficeListPost(any(), any())(any(), headerCarrierCaptor.capture())
        headerCarrierCaptor.getValue.authorization.value.value mustBe bearerToken
      }

      "must return InternalServerError when post unsuccessful" in {
        forAll(Gen.choose(400, 599)) {
          errorCode =>
            beforeEach()

            when(mockConnector.customsOfficeListPost(any(), any())(any(), any()))
              .thenReturn(Future.successful(HttpResponse(errorCode, "")))

            val request = FakeRequest(POST, routes.CustomsReferenceDataController.customsOfficeListPost().url)
              .withHeaders(
                "Authorization" -> bearerToken,
                "Content-Type" -> "application/json"
              )

            val result = route(app, request).value

            status(result) mustEqual INTERNAL_SERVER_ERROR

            val headerCarrierCaptor: ArgumentCaptor[HeaderCarrier] = ArgumentCaptor.forClass(classOf[HeaderCarrier])
            verify(mockConnector).customsOfficeListPost(any(), any())(any(), headerCarrierCaptor.capture())
            headerCarrierCaptor.getValue.authorization.value.value mustBe bearerToken
        }
      }

      "must return BadRequest when post successful" in {
        when(mockConnector.customsOfficeListPost(any(), any())(any(), any()))
          .thenReturn(Future.successful(HttpResponse(ACCEPTED, "")))

        val request = FakeRequest(POST, routes.CustomsReferenceDataController.customsOfficeListPost().url)
          .withHeaders(
            "Authorization" -> bearerToken
          )

        val result = route(app, request).value

        status(result) mustEqual BAD_REQUEST

        verifyNoInteractions(mockConnector)
      }
    }
  }
}
