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
import org.apache.pekko.stream.scaladsl.Source
import org.apache.pekko.util.ByteString
import play.api.libs.ws.DefaultBodyWritables
import play.api.libs.ws.DefaultBodyWritables.*
import uk.gov.hmrc.http.client.{HttpClientV2, given}
import uk.gov.hmrc.http.{HeaderCarrier, HttpResponse, StringContextOps}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class CustomsReferenceDataConnector @Inject() (http: HttpClientV2, config: AppConfig) {

  def postReferenceDataList(body: Source[ByteString, ?])(implicit ec: ExecutionContext, hc: HeaderCarrier): Future[HttpResponse] = {
    val url = url"${config.customsReferenceDataUrl}/reference-data-lists"
    http
      .post(url)
      .setHeader(
        "Accept"       -> "application/vnd.hmrc.2.0+json",
        "Content-Type" -> "application/json"
      )
      .withBody(body)
      .execute[HttpResponse]
  }

  def postCustomsOfficeLists(body: Source[ByteString, ?])(implicit ec: ExecutionContext, hc: HeaderCarrier): Future[HttpResponse] = {
    val url = url"${config.customsReferenceDataUrl}/customs-office-lists"
    http
      .post(url)
      .setHeader(
        "Accept"       -> "application/vnd.hmrc.2.0+json",
        "Content-Type" -> "application/json"
      )
      .withBody(body)
      .execute[HttpResponse]
  }

  def getList(listName: String, queryString: Map[String, Seq[String]])(implicit ec: ExecutionContext, hc: HeaderCarrier): Future[HttpResponse] = {
    val queryParameters = queryString.toSeq.flatMap {
      case (key, values) =>
        values.map {
          value => key -> value
        }
    }
    val url = url"${config.customsReferenceDataUrl}/lists/$listName?$queryParameters"
    http
      .get(url)
      .setHeader(hc.headers(Seq("Accept"))*)
      .execute[HttpResponse]
  }

}
