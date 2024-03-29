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

import config.AppConfig
import play.api.libs.json.Json
import play.api.libs.ws.{WSClient, WSResponse}

import javax.inject.Inject
import scala.concurrent.Future

class TransitMovementsTraderReferenceDataEtlConnector @Inject()(ws: WSClient, config: AppConfig) {

  def referenceDataImport(): Future[WSResponse] = {
    val serviceUrl = s"${config.transitMovementsTraderReferenceDataEtlUrl}/schedule-action/reference-data"

    ws.url(serviceUrl)
      .withHttpHeaders(("Content-Type", "application/json"))
      .post(Json.obj())
  }

}
