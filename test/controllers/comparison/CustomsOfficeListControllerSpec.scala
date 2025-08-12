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

package controllers.comparison

import base.SpecBase
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.Files.{SingletonTemporaryFileCreator, TemporaryFile}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.MultipartFormData.FilePart
import play.api.mvc.{AnyContentAsMultipartFormData, MultipartFormData}
import play.api.test.FakeRequest
import play.api.test.Helpers.*

import java.io.PrintWriter

class CustomsOfficeListControllerSpec extends SpecBase with GuiceOneAppPerSuite with ScalaCheckPropertyChecks {

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .build()

  private def createFile(key: String, json: JsValue): FilePart[TemporaryFile] = {
    val tempFile = SingletonTemporaryFileCreator.create(key, ".json")
    val writer   = new PrintWriter(tempFile.toFile)
    try
      writer.write(Json.stringify(json))
    finally
      writer.close()
    FilePart[TemporaryFile](
      key = key,
      filename = s"$key.json",
      contentType = Some("application/json"),
      ref = tempFile
    )
  }

  "compare" - {

    "must return Ok" - {
      "when no diffs" in {

        val v1Content = Json.parse("""
            |{
            |  "data": [
            |    {
            |      "phoneNumber": "03000599436",
            |      "roles": [
            |        {
            |          "role": "DEP"
            |        }
            |      ],
            |      "name": "Heysham",
            |      "state": "valid",
            |      "id": "GB000008",
            |      "languageCode": "EN",
            |      "eMailAddress": "babatunde.adewole@hmrc.gsi.gov.uk",
            |      "activeFrom": "2018-12-01",
            |      "countryId": "GB"
            |    }
            |  ]
            |}
            |""".stripMargin)

        val v2Content = Json.parse("""
            |[
            |  {
            |    "referenceNumber": "GB000008",
            |    "countryCode": "GB",
            |    "emailAddress": "babatunde.adewole@hmrc.gsi.gov.uk",
            |    "phoneNumber": "03000599436",
            |    "customsOfficeLsd": {
            |      "languageCode": "EN",
            |      "customsOfficeUsualName": "Heysham"
            |    },
            |    "customsOfficeTimetable": {
            |      "customsOfficeTimetableLine": [
            |        {
            |          "customsOfficeRoleTrafficCompetence": [
            |            {
            |              "roleName": "DEP"
            |            }
            |          ]
            |        }
            |      ]
            |    }
            |  }
            |]
            |""".stripMargin)

        def fakeRequest: FakeRequest[AnyContentAsMultipartFormData] = {
          val form: MultipartFormData[TemporaryFile] = MultipartFormData(
            dataParts = Map(),
            files = Seq(
              createFile("v1", v1Content),
              createFile("v2", v2Content)
            ),
            badParts = Nil
          )

          FakeRequest(POST, routes.CustomsOfficeListController.compare().url)
            .withMultipartFormDataBody(form)
        }

        val result = route(app, fakeRequest).value

        val expectedJson = Json.parse("""
            |{
            |  "count": 0,
            |  "diffs": []
            |}
            |""".stripMargin)

        status(result) mustEqual OK
        contentAsJson(result) mustEqual expectedJson
      }

      "when there are diffs" in {

        val v1Content = Json.parse("""
            |{
            |  "data": [
            |    {
            |      "phoneNumber": "03000599436",
            |      "roles": [
            |        {
            |          "role": "DEP"
            |        }
            |      ],
            |      "name": "Heysham",
            |      "state": "valid",
            |      "id": "GB000008",
            |      "languageCode": "EN",
            |      "eMailAddress": "babatunde.adewole@hmrc.gsi.gov.uk",
            |      "activeFrom": "2018-12-01",
            |      "countryId": "GB"
            |    }
            |  ]
            |}
            |""".stripMargin)

        val v2Content = Json.parse("""
            |[
            |  {
            |    "referenceNumber": "GB000008",
            |    "countryCode": "GB",
            |    "emailAddress": "babatunde.adewole@hmrc.gsi.gov.uk",
            |    "phoneNumber": "03000599436",
            |    "customsOfficeLsd": {
            |      "languageCode": "EN",
            |      "customsOfficeUsualName": "Heysham"
            |    },
            |    "customsOfficeTimetable": {
            |      "customsOfficeTimetableLine": [
            |        {
            |          "customsOfficeRoleTrafficCompetence": [
            |            {
            |              "roleName": "DES"
            |            }
            |          ]
            |        }
            |      ]
            |    }
            |  }
            |]
            |""".stripMargin)

        def fakeRequest: FakeRequest[AnyContentAsMultipartFormData] = {
          val form: MultipartFormData[TemporaryFile] = MultipartFormData(
            dataParts = Map(),
            files = Seq(
              createFile("v1", v1Content),
              createFile("v2", v2Content)
            ),
            badParts = Nil
          )

          FakeRequest(POST, routes.CustomsOfficeListController.compare().url)
            .withMultipartFormDataBody(form)
        }

        val result = route(app, fakeRequest).value

        val expectedJson = Json.parse("""
            |{
            |  "count": 1,
            |  "diffs": [
            |    {
            |      "phoneNumber": "03000599436",
            |      "roles": [
            |        "DES"
            |      ],
            |      "name": "Heysham",
            |      "id": "GB000008",
            |      "languageCode": "EN",
            |      "emailAddress": "babatunde.adewole@hmrc.gsi.gov.uk",
            |      "countryId": "GB"
            |    }
            |  ]
            |}
            |""".stripMargin)

        status(result) mustEqual OK
        contentAsJson(result) mustEqual expectedJson
      }

      "when there are multiple offices with the same ID" in {

        val v1Content = Json.parse("""
            |{
            |  "data": [
            |    {
            |      "phoneNumber": "+ 359",
            |      "roles": [
            |        {
            |          "role": "DEP"
            |        },
            |        {
            |          "role": "DES"
            |        },
            |        {
            |          "role": "EIN"
            |        },
            |        {
            |          "role": "ENL"
            |        },
            |        {
            |          "role": "ENQ"
            |        },
            |        {
            |          "role": "EXL"
            |        },
            |        {
            |          "role": "EXP"
            |        },
            |        {
            |          "role": "REC"
            |        }
            |      ],
            |      "name": "МБ Монтана",
            |      "state": "valid",
            |      "id": "BG004210",
            |      "languageCode": "BG",
            |      "activeFrom": "2019-01-01",
            |      "countryId": "BG"
            |    },
            |    {
            |      "phoneNumber": "+ 359",
            |      "roles": [
            |        {
            |          "role": "DEP"
            |        },
            |        {
            |          "role": "DES"
            |        },
            |        {
            |          "role": "EIN"
            |        },
            |        {
            |          "role": "ENL"
            |        },
            |        {
            |          "role": "ENQ"
            |        },
            |        {
            |          "role": "EXL"
            |        },
            |        {
            |          "role": "EXP"
            |        },
            |        {
            |          "role": "REC"
            |        }
            |      ],
            |      "name": "MB Montana",
            |      "state": "valid",
            |      "id": "BG004210",
            |      "languageCode": "EN",
            |      "activeFrom": "2019-01-01",
            |      "countryId": "BG"
            |    }
            |  ]
            |}
            |""".stripMargin)

        val v2Content = Json.parse("""
            |[
            |  {
            |    "referenceNumber": "BG004210",
            |    "countryCode": "BG",
            |    "emailAddress": null,
            |    "phoneNumber": "+ 359",
            |    "customsOfficeLsd": {
            |      "customsOfficeUsualName": "MB Montana",
            |      "languageCode": "EN"
            |    },
            |    "customsOfficeTimetable": {
            |      "customsOfficeTimetableLine": [
            |        {
            |          "customsOfficeRoleTrafficCompetence": [
            |            {
            |              "roleName": "DEP",
            |              "trafficType": "R"
            |            },
            |            {
            |              "roleName": "DEP",
            |              "trafficType": "V"
            |            },
            |            {
            |              "roleName": "DES",
            |              "trafficType": "R"
            |            },
            |            {
            |              "roleName": "DES",
            |              "trafficType": "V"
            |            },
            |            {
            |              "roleName": "EIN",
            |              "trafficType": "R"
            |            },
            |            {
            |              "roleName": "EIN",
            |              "trafficType": "V"
            |            },
            |            {
            |              "roleName": "ENL",
            |              "trafficType": "R"
            |            },
            |            {
            |              "roleName": "ENL",
            |              "trafficType": "V"
            |            },
            |            {
            |              "roleName": "ENQ",
            |              "trafficType": "R"
            |            },
            |            {
            |              "roleName": "ENQ",
            |              "trafficType": "V"
            |            },
            |            {
            |              "roleName": "EXL",
            |              "trafficType": "R"
            |            },
            |            {
            |              "roleName": "EXL",
            |              "trafficType": "V"
            |            },
            |            {
            |              "roleName": "EXP",
            |              "trafficType": "R"
            |            },
            |            {
            |              "roleName": "EXP",
            |              "trafficType": "V"
            |            },
            |            {
            |              "roleName": "REC",
            |              "trafficType": "R"
            |            },
            |            {
            |              "roleName": "REC",
            |              "trafficType": "V"
            |            }
            |          ]
            |        }
            |      ]
            |    }
            |  }
            |]
            |""".stripMargin)

        def fakeRequest: FakeRequest[AnyContentAsMultipartFormData] = {
          val form: MultipartFormData[TemporaryFile] = MultipartFormData(
            dataParts = Map(),
            files = Seq(
              createFile("v1", v1Content),
              createFile("v2", v2Content)
            ),
            badParts = Nil
          )

          FakeRequest(POST, routes.CustomsOfficeListController.compare().url)
            .withMultipartFormDataBody(form)
        }

        val result = route(app, fakeRequest).value

        val expectedJson = Json.parse("""
            |{
            |  "count": 0,
            |  "diffs": []
            |}
            |""".stripMargin)

        status(result) mustEqual OK
        contentAsJson(result) mustEqual expectedJson
      }
    }

    "must return BadRequest" - {
      "when v1 file is invalid" in {

        val v1Content = Json.parse("""
            |{
            |  "data": [
            |    {
            |      "foo": "bar"
            |    }
            |  ]
            |}
            |""".stripMargin)

        val v2Content = Json.parse("""
            |[
            |  {
            |    "referenceNumber": "GB000008",
            |    "countryCode": "GB",
            |    "emailAddress": "babatunde.adewole@hmrc.gsi.gov.uk",
            |    "phoneNumber": "03000599436",
            |    "customsOfficeLsd": {
            |      "languageCode": "EN",
            |      "customsOfficeUsualName": "Heysham"
            |    },
            |    "customsOfficeTimetable": {
            |      "customsOfficeTimetableLine": [
            |        {
            |          "customsOfficeRoleTrafficCompetence": [
            |            {
            |              "roleName": "DES"
            |            }
            |          ]
            |        }
            |      ]
            |    }
            |  }
            |]
            |""".stripMargin)

        def fakeRequest: FakeRequest[AnyContentAsMultipartFormData] = {
          val form: MultipartFormData[TemporaryFile] = MultipartFormData(
            dataParts = Map(),
            files = Seq(
              createFile("v1", v1Content),
              createFile("v2", v2Content)
            ),
            badParts = Nil
          )

          FakeRequest(POST, routes.CustomsOfficeListController.compare().url)
            .withMultipartFormDataBody(form)
        }

        val result = route(app, fakeRequest).value

        status(result) mustEqual BAD_REQUEST
      }

      "when v2 file is invalid" in {

        val v1Content = Json.parse("""
            |{
            |  "data": [
            |    {
            |      "phoneNumber": "03000599436",
            |      "roles": [
            |        {
            |          "role": "DEP"
            |        }
            |      ],
            |      "name": "Heysham",
            |      "state": "valid",
            |      "id": "GB000008",
            |      "languageCode": "EN",
            |      "eMailAddress": "babatunde.adewole@hmrc.gsi.gov.uk",
            |      "activeFrom": "2018-12-01",
            |      "countryId": "GB"
            |    }
            |  ]
            |}
            |""".stripMargin)

        val v2Content = Json.parse("""
            |[
            |  {
            |    "foo": "bar"
            |  }
            |]
            |""".stripMargin)

        def fakeRequest: FakeRequest[AnyContentAsMultipartFormData] = {
          val form: MultipartFormData[TemporaryFile] = MultipartFormData(
            dataParts = Map(),
            files = Seq(
              createFile("v1", v1Content),
              createFile("v2", v2Content)
            ),
            badParts = Nil
          )

          FakeRequest(POST, routes.CustomsOfficeListController.compare().url)
            .withMultipartFormDataBody(form)
        }

        val result = route(app, fakeRequest).value

        status(result) mustEqual BAD_REQUEST
      }

      "when files missing" in {
        def fakeRequest: FakeRequest[AnyContentAsMultipartFormData] = {
          val form: MultipartFormData[TemporaryFile] = MultipartFormData(
            dataParts = Map(),
            files = Nil,
            badParts = Nil
          )

          FakeRequest(POST, routes.CustomsOfficeListController.compare().url)
            .withMultipartFormDataBody(form)
        }

        val result = route(app, fakeRequest).value

        status(result) mustEqual BAD_REQUEST
      }
    }
  }
}
