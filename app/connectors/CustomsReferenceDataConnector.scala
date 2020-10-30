/*
 * Copyright 2020 HM Revenue & Customs
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

import java.io.File

import config.AppConfig
import javax.inject.Inject
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.Future

class CustomsReferenceDataConnector @Inject()(ws: WSClient, config: AppConfig) {

  private val h1 = ("Accept-Encoding", "gzip, deflate, br")
  private val h2 = ("Content-Type", "application/json")

  def referenceDataListPost(body: File): Future[WSResponse] = {

    val serviceUrl = s"${config.customsReferenceDataUrl}/reference-data-lists"

    ws.url(serviceUrl)
      .withHttpHeaders(h1, h2)
      .post(body)
  }

  def customsOfficeListPost(body: File): Future[WSResponse] = {

    val serviceUrl = s"${config.customsReferenceDataUrl}/customs-office-lists"

    ws.url(serviceUrl)
      .withHttpHeaders(h1, h2)
      .post(body)
  }

}
