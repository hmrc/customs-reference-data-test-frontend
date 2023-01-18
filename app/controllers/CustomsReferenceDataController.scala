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

package controllers

import connectors.CustomsReferenceDataConnector
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents, Request}
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import java.io.File
import javax.inject.Inject
import scala.concurrent.ExecutionContext

class CustomsReferenceDataController @Inject()(
                                                mcc: MessagesControllerComponents,
                                                connector: CustomsReferenceDataConnector
                                              )(implicit val ec: ExecutionContext) extends FrontendController(mcc) {

  def referenceDataListPost(): Action[File] =
    Action(parse.file(to = new File("/tmp/test.gz"))).async {
      request: Request[File] => {
        connector.referenceDataListPost(request.body).map {
          result =>
            result.status match {
              case ACCEPTED => Accepted
              case _        => BadRequest(s"Failed: ${result.status} - ${result.body}")
            }
        }
      }
    }

  def customsOfficeListPost(): Action[File] =
    Action(parse.file(to = new File("/tmp/test.gz"))).async {
      request: Request[File] => {
        connector.customsOfficeListPost(request.body).map {
          result =>
            result.status match {
              case ACCEPTED => Accepted
              case _        => BadRequest(s"Failed: ${result.status} - ${result.body}")
            }
        }
      }
    }

  def referenceDataListGet(listName: String): Action[AnyContent] =
    Action.async {
      _ =>
        connector.referenceDataListGet(listName).map {
          result =>
            result.status match {
              case OK        => Ok(result.json)
              case NOT_FOUND => NotFound(s"$listName not found")
              case _         => InternalServerError
            }
        }
    }

}
