/*
 * Copyright 2023 HM Revenue & Customs
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
import play.api.libs.ws.{WSClient, WSResponse}

import java.io.File
import javax.inject.Inject
import scala.concurrent.Future

class CustomsReferenceDataConnector @Inject()(ws: WSClient, config: AppConfig) {

  private val headers = Seq(
    "Accept-Encoding" -> "gzip, deflate, br",
    "Content-Type" -> "application/json",
    "Accept" -> config.acceptHeader
  )


  def referenceDataListPost(body: File): Future[WSResponse] = {
    val url = s"${config.customsReferenceDataUrl}/reference-data-lists"

    ws.url(url)
      .withHttpHeaders(headers: _*)
      .post(body)
  }

  def customsOfficeListPost(body: File): Future[WSResponse] = {
    val url = s"${config.customsReferenceDataUrl}/customs-office-lists"

    ws.url(url)
      .withHttpHeaders(headers: _*)
      .post(body)
  }

  def referenceDataListGet(listName: String): Future[WSResponse] = {
    val url = s"${config.customsReferenceDataUrl}/lists/$listName"

    ws.url(url)
      .withHttpHeaders(headers: _*)
      .get()
  }

}
