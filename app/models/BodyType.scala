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

package models

import uk.gov.hmrc.http.HeaderCarrier

sealed trait BodyType

object BodyType {

  case object JSON extends BodyType
  case object XML extends BodyType

  def apply(hc: HeaderCarrier): Option[BodyType] =
    hc.headers(Seq("Content-Type")) match {
      case headers if headers.map(_._2).contains("application/xml") => Some(XML)
      case headers if headers.map(_._2).contains("application/json") => Some(JSON)
      case _ => None
    }
}
