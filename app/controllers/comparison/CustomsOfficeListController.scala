/*
 * Copyright 2025 HM Revenue & Customs
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

package controllers.comparison

import models.CustomsOffice
import play.api.libs.Files
import play.api.libs.json.*
import play.api.mvc.MultipartFormData.FilePart
import play.api.mvc.{Action, MessagesControllerComponents, MultipartFormData}
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import javax.inject.Inject
import scala.concurrent.ExecutionContext
import scala.io.Source
import scala.util.{Failure, Success, Try}

class CustomsOfficeListController @Inject() (
  mcc: MessagesControllerComponents
)(implicit val ec: ExecutionContext)
    extends FrontendController(mcc) {

  def compare(): Action[MultipartFormData[Files.TemporaryFile]] =
    Action(parse.multipartFormData) {
      request =>
        def parseFile(file: FilePart[Files.TemporaryFile]): Try[Seq[CustomsOffice]] = {
          val source  = Source.fromFile(file.ref.path.toFile)
          val content = source.getLines().mkString("\n")
          source.close()
          Json.parse(content) match {
            case JsArray(values) =>
              Try(values.toSeq.map(_.as[CustomsOffice]))
            case JsObject(underlying) =>
              underlying.get("data") match {
                case Some(JsArray(values)) =>
                  Try(values.toSeq.map(_.as[CustomsOffice]))
                case _ =>
                  Failure(new Exception(s"${file.filename} contains invalid data"))
              }
            case _ =>
              Failure(new Exception(s"${file.filename} contains invalid data"))
          }
        }

        (request.body.file("v1"), request.body.file("v2")) match {
          case (Some(v1File), Some(v2File)) =>
            (parseFile(v1File), parseFile(v2File)) match {
              case (Success(v1Data), Success(v2Data)) =>
                val diffs = v1Data.diff(v2Data).map(Json.toJson)
                Ok(Json.obj("diffs" -> JsArray(diffs)))
              case _ =>
                BadRequest("One or more of the files contain invalid data")
            }
          case _ =>
            BadRequest("Please attach two files called 'v1' and 'v2'")
        }
    }
}
