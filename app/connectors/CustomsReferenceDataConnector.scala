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
import uk.gov.hmrc.http.HttpReads.Implicits._
import uk.gov.hmrc.http.client.HttpClientV2
import uk.gov.hmrc.http.{HeaderCarrier, HttpResponse, StringContextOps}

import java.io.File
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class CustomsReferenceDataConnector @Inject()(http: HttpClientV2, config: AppConfig) {

  private val headers = Seq(
    "Accept-Encoding" -> "gzip, deflate, br",
    "Content-Type" -> "application/json",
    "Accept" -> config.acceptHeader
  )


  def referenceDataListPost(body: File)(implicit ec: ExecutionContext, hc: HeaderCarrier): Future[HttpResponse] = {
    val url = url"${config.customsReferenceDataUrl}/reference-data-lists"
    http
      .post(url)
      .setHeader(headers: _*)
      .withBody(body)
      .execute[HttpResponse]
  }

  def customsOfficeListPost(body: File)(implicit ec: ExecutionContext, hc: HeaderCarrier): Future[HttpResponse] = {
    val url = url"${config.customsReferenceDataUrl}/customs-office-lists"
    http
      .post(url)
      .setHeader(headers: _*)
      .withBody(body)
      .execute[HttpResponse]
  }

  def referenceDataListGet(listName: String)(implicit ec: ExecutionContext, hc: HeaderCarrier): Future[HttpResponse] = {
    val url = url"${config.customsReferenceDataUrl}/lists/$listName"
    http
      .get(url)
      .setHeader(headers: _*)
      .execute[HttpResponse]
  }

}
