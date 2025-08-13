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

import org.apache.pekko.stream.scaladsl.Source
import org.apache.pekko.util.ByteString
import play.api.libs.streams.Accumulator
import play.api.mvc.{Action, BodyParser, MessagesControllerComponents}
import uk.gov.hmrc.http.{HeaderCarrier, HttpResponse}
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import uk.gov.hmrc.play.http.HeaderCarrierConverter

import scala.concurrent.{ExecutionContext, Future}

abstract class IngestionController(
  mcc: MessagesControllerComponents
)(implicit ec: ExecutionContext)
    extends FrontendController(mcc) {

  def ingest(source: Source[ByteString, ?])(implicit hc: HeaderCarrier): Future[HttpResponse]

  private def streamParser: BodyParser[Source[ByteString, ?]] = BodyParser {
    _ =>
      Accumulator.source[ByteString].map(Right.apply)
  }

  def post(): Action[Source[ByteString, ?]] =
    Action(streamParser).async {
      request =>
        implicit val hc: HeaderCarrier = HeaderCarrierConverter.fromRequest(request)
        ingest(request.body).map(_.status).map(Status)
    }
}
