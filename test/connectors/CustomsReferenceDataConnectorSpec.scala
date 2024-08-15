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
import models.BodyType
import org.scalatest.OptionValues
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import uk.gov.hmrc.http.{HeaderCarrier, HttpResponse}

import java.io.File
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class CustomsReferenceDataConnectorSpec
  extends AnyFreeSpec
    with Matchers
    with ScalaFutures
    with IntegrationPatience
    with OptionValues
    with WiremockSuite {

  override protected def portConfigKey: String = "microservice.services.customs-reference-data.port"

  lazy val connector: CustomsReferenceDataConnector = app.injector.instanceOf[CustomsReferenceDataConnector]

  implicit val headerCarrier: HeaderCarrier = HeaderCarrier()

  "CustomsReferenceDataConnector" - {

    "referenceDataListPost" - {
      "must return status Accepted" - {
        "when XML" in {
          server.stubFor(
            post(urlEqualTo("/customs-reference-data/test-only/reference-data-lists"))
              .willReturn(
                aResponse()
                  .withStatus(202)
              )
          )

          val tempFile = File.createTempFile("test", ".gz")

          val result: Future[HttpResponse] = connector.referenceDataListPost(tempFile, BodyType.XML)

          result.futureValue.status mustBe 202

          tempFile.deleteOnExit()
        }

        "when JSON" in {
          server.stubFor(
            post(urlEqualTo("/customs-reference-data/reference-data-lists"))
              .willReturn(
                aResponse()
                  .withStatus(202)
              )
          )

          val tempFile = File.createTempFile("test", ".gz")

          val result: Future[HttpResponse] = connector.referenceDataListPost(tempFile, BodyType.JSON)

          result.futureValue.status mustBe 202

          tempFile.deleteOnExit()
        }
      }
    }

    "customsOfficeListPost" - {
      "must return status Accepted" - {
        "when XML" in {
          server.stubFor(
            post(urlEqualTo("/customs-reference-data/test-only/customs-office-lists"))
              .willReturn(
                aResponse()
                  .withStatus(202)
              )
          )

          val tempFile = File.createTempFile("test", ".gz")

          val result: Future[HttpResponse] = connector.customsOfficeListPost(tempFile, BodyType.XML)

          result.futureValue.status mustBe 202

          tempFile.deleteOnExit()
        }

        "when JSON" in {
          server.stubFor(
            post(urlEqualTo("/customs-reference-data/customs-office-lists"))
              .willReturn(
                aResponse()
                  .withStatus(202)
              )
          )

          val tempFile = File.createTempFile("test", ".gz")

          val result: Future[HttpResponse] = connector.customsOfficeListPost(tempFile, BodyType.JSON)

          result.futureValue.status mustBe 202

          tempFile.deleteOnExit()
        }
      }
    }

    "referenceDataListGet" - {
      "must return status Accepted" in {

        val listName = "list-name"

        server.stubFor(
          get(urlEqualTo(s"/customs-reference-data/lists/$listName"))
            .willReturn(
              aResponse()
                .withStatus(200)
            )
        )

        val result: Future[HttpResponse] = connector.referenceDataListGet(listName)

        result.futureValue.status mustBe 200
      }
    }
  }

}
