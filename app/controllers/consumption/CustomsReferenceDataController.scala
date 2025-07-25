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

package controllers.consumption

import connectors.CustomsReferenceDataConnector
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import uk.gov.hmrc.play.http.HeaderCarrierConverter

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class CustomsReferenceDataController @Inject() (
  mcc: MessagesControllerComponents,
  connector: CustomsReferenceDataConnector
)(implicit val ec: ExecutionContext)
    extends FrontendController(mcc) {

  def get(listName: String): Action[AnyContent] =
    Action.async {
      request =>
        implicit val hc: HeaderCarrier = HeaderCarrierConverter.fromRequest(request)
        connector.getList(listName, request.queryString).map {
          result =>
            result.status match {
              case OK        => Ok(result.json)
              case NOT_FOUND => NotFound(s"$listName not found")
              case _         => InternalServerError
            }
        }
    }
}
