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
import play.api.mvc.MessagesControllerComponents
import uk.gov.hmrc.http.{HeaderCarrier, HttpResponse}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ReferenceDataListController @Inject() (
  mcc: MessagesControllerComponents,
  connector: CustomsReferenceDataConnector
)(implicit ec: ExecutionContext)
    extends IngestionController(mcc) {

  override def ingest(body: JsValue)(implicit hc: HeaderCarrier): Future[HttpResponse] =
    connector.postReferenceDataList(body)
}
