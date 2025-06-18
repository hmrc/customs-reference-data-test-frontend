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

package controllers.conversion

import base.SpecBase
import org.mockito.ArgumentMatchers.eq as eqTo
import org.mockito.Mockito.{reset, when}
import org.scalatestplus.mockito.MockitoSugar.mock
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import play.api.Application
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.Json
import play.api.mvc.AnyContentAsXml
import play.api.test.FakeRequest
import play.api.test.Helpers.*
import utils.XmlToJsonConverter.CustomsOfficeListXmlToJsonConverter

class CustomsOfficeListControllerSpec extends SpecBase with ScalaCheckPropertyChecks {

  private val mockXmlToJsonConverter: CustomsOfficeListXmlToJsonConverter = mock[CustomsOfficeListXmlToJsonConverter]

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
  }

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .overrides(
        bind[CustomsOfficeListXmlToJsonConverter].toInstance(mockXmlToJsonConverter)
      )
      .build()

  "convertLists" - {

    def fakeRequest: FakeRequest[AnyContentAsXml] =
      FakeRequest(POST, routes.CustomsOfficeListController.convertLists().url)
        .withXmlBody(testXml)
        .withHeaders(headers *)

    "must return Ok" in {
      
      when(mockXmlToJsonConverter.convert(eqTo(testXml)))
        .thenReturn(testJson)

      val result = route(app, fakeRequest).value

      status(result) mustBe OK
      contentAsJson(result) mustBe testJson
    }
  }

  "convertList" - {

    val listName = "foo"

    def fakeRequest: FakeRequest[AnyContentAsXml] =
      FakeRequest(POST, routes.CustomsOfficeListController.convertList(listName).url)
        .withXmlBody(testXml)
        .withHeaders(headers *)

    "must return Ok" in {

      when(mockXmlToJsonConverter.convert(eqTo(testXml), eqTo(listName)))
        .thenReturn(testJson)

      val result = route(app, fakeRequest).value

      status(result) mustBe OK
      contentAsJson(result) mustBe testJson
    }
  }
}
