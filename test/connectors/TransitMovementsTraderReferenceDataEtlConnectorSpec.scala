/*
 * Copyright 2022 HM Revenue & Customs
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

import com.github.tomakehurst.wiremock.client.WireMock.{aResponse, post, urlEqualTo}
import org.scalatest.OptionValues
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import play.api.libs.ws.WSResponse
import uk.gov.hmrc.http.HeaderCarrier

import scala.concurrent.Future

class TransitMovementsTraderReferenceDataEtlConnectorSpec
  extends AnyFreeSpec
    with Matchers
    with ScalaFutures
    with IntegrationPatience
    with OptionValues
    with WiremockSuite {

  override protected def portConfigKey: String = "microservice.services.transit-movements-trader-reference-data-etl.port"

  lazy val connector: TransitMovementsTraderReferenceDataEtlConnector = app.injector.instanceOf[TransitMovementsTraderReferenceDataEtlConnector]

  implicit val headerCarrier: HeaderCarrier = HeaderCarrier()

  "TransitMovementsTraderReferenceDataEtlConnector" - {

    "referenceDataImport" - {
      "must return status Ok" in {

        server.stubFor(
          post(urlEqualTo("/transit-movements-trader-reference-data-etl/schedule-action/reference-data"))
            .willReturn(
              aResponse()
                .withStatus(200)
            )
        )

        val result: Future[WSResponse] = connector.referenceDataImport()

        result.futureValue.status mustBe 200
      }
    }
  }

}
