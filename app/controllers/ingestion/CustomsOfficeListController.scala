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

package controllers.ingestion

import connectors.CustomsReferenceDataConnector
import play.api.libs.json.JsValue
import play.api.mvc.{Action, MessagesControllerComponents}
import uk.gov.hmrc.http.{HeaderCarrier, HttpResponse}
import utils.XmlToJsonConverter.CustomsOfficeListXmlToJsonConverter

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import scala.xml.NodeSeq

class CustomsOfficeListController @Inject()(
  mcc: MessagesControllerComponents,
  connector: CustomsReferenceDataConnector,
  converter: CustomsOfficeListXmlToJsonConverter
)(implicit ec: ExecutionContext)
    extends IngestionController[CustomsOfficeListXmlToJsonConverter](mcc, converter) {

  /**
    * Download the Customs Office List (COL) zip file from https://ec.europa.eu/taxation_customs/dds2/rd/rd_download_home.jsp?Lang=en
    * Unzip the download, cd into it and run `gzip COL-Generic-YYYYMMDD.xml` where `YYYYMMDD` is today's date
    * Attach this to the request body as a binary
    */
  override def post(): Action[NodeSeq] = super.post()

  override def ingest(body: JsValue)(implicit hc: HeaderCarrier): Future[HttpResponse] =
    connector.postCustomsOfficeLists(body)
}
