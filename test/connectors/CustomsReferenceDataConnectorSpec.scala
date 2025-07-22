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

package connectors

import com.github.tomakehurst.wiremock.client.WireMock.{aResponse, get, post, urlEqualTo}
import org.scalatest.OptionValues
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import play.api.libs.json.Json
import uk.gov.hmrc.http.{HeaderCarrier, HttpResponse}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class CustomsReferenceDataConnectorSpec extends AnyFreeSpec with Matchers with ScalaFutures with IntegrationPatience with OptionValues with WiremockSuite {

  override protected def portConfigKey: String = "microservice.services.customs-reference-data.port"

  lazy val connector: CustomsReferenceDataConnector = app.injector.instanceOf[CustomsReferenceDataConnector]

  implicit val headerCarrier: HeaderCarrier = HeaderCarrier()

  "CustomsReferenceDataConnector" - {

    "referenceDataListPost" - {
      "must return status Accepted" in {
        server.stubFor(
          post(urlEqualTo("/customs-reference-data/reference-data-lists"))
            .willReturn(
              aResponse()
                .withStatus(202)
            )
        )

        val body = Json.obj("foo" -> "bar")

        val result: Future[HttpResponse] = connector.postReferenceDataList(body)

        result.futureValue.status mustEqual 202
      }
    }

    "customsOfficeListPost" - {
      "must return status Accepted" in {
        server.stubFor(
          post(urlEqualTo("/customs-reference-data/customs-office-lists"))
            .willReturn(
              aResponse()
                .withStatus(202)
            )
        )

        val body = Json.obj("foo" -> "bar")

        val result: Future[HttpResponse] = connector.postCustomsOfficeLists(body)

        result.futureValue.status mustEqual 202
      }
    }

    "referenceDataListGet" - {
      val listName = "list-name"

      "must return ok" - {
        "without query parameters" in {
          server.stubFor(
            get(urlEqualTo(s"/customs-reference-data/lists/$listName"))
              .willReturn(
                aResponse()
                  .withStatus(200)
              )
          )

          val result: Future[HttpResponse] = connector.getList(listName, Map.empty)

          result.futureValue.status mustEqual 200
        }

        "with a query parameter" in {
          server.stubFor(
            get(urlEqualTo(s"/customs-reference-data/lists/$listName?country=GB"))
              .willReturn(
                aResponse()
                  .withStatus(200)
              )
          )

          val result: Future[HttpResponse] = connector.getList(listName, Map("country" -> Seq("GB")))

          result.futureValue.status mustEqual 200
        }

        "with multiple query parameters" in {
          server.stubFor(
            get(urlEqualTo(s"/customs-reference-data/lists/$listName?country=GB&role=TRA"))
              .willReturn(
                aResponse()
                  .withStatus(200)
              )
          )

          val result: Future[HttpResponse] = connector.getList(listName, Map("country" -> Seq("GB"), "role" -> Seq("TRA")))

          result.futureValue.status mustEqual 200
        }

        "with multiple queries on the same parameter" in {
          server.stubFor(
            get(urlEqualTo(s"/customs-reference-data/lists/$listName?country=GB&country=XI"))
              .willReturn(
                aResponse()
                  .withStatus(200)
              )
          )

          val result: Future[HttpResponse] = connector.getList(listName, Map("country" -> Seq("GB", "XI")))

          result.futureValue.status mustEqual 200
        }
      }
    }
  }
}
