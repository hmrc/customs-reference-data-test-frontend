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

package models

import play.api.libs.functional.syntax.*
import play.api.libs.json.*

case class CustomsOffice(
  languageCode: String,
  name: String,
  phoneNumber: Option[String],
  emailAddress: Option[String],
  id: String,
  countryId: String,
  roles: Set[String]
)

object CustomsOffice {

  private val v1Reads: Reads[CustomsOffice] = {
    val roleReads: Reads[Set[String]] =
      (__ \ "roles").read[Seq[JsObject]].map {
        _.map {
          role => (role \ "role").as[String]
        }.toSet
      }

    (
      (__ \ "languageCode").read[String] and
        (__ \ "name").read[String] and
        (__ \ "phoneNumber").readNullable[String] and
        (__ \ "eMailAddress").readNullable[String] and
        (__ \ "id").read[String] and
        (__ \ "countryId").read[String] and
        roleReads
    )(CustomsOffice.apply)
  }

  private val v2Reads: Reads[CustomsOffice] = {
    val roleReads: Reads[Set[String]] =
      (__ \ "customsOfficeTimetable" \ "customsOfficeTimetableLine").read[Seq[JsObject]].map {
        _.flatMap {
          timetable =>
            (timetable \ "customsOfficeRoleTrafficCompetence").as[Seq[JsObject]].map {
              role => (role \ "roleName").as[String]
            }
        }.toSet
      }

    (
      (__ \ "customsOfficeLsd" \ "languageCode").read[String] and
        (__ \ "customsOfficeLsd" \ "customsOfficeUsualName").read[String] and
        (__ \ "phoneNumber").readNullable[String] and
        (__ \ "emailAddress").readNullable[String] and
        (__ \ "referenceNumber").read[String] and
        (__ \ "countryCode").read[String] and
        roleReads
    )(CustomsOffice.apply)
  }

  implicit val reads: Reads[CustomsOffice] = v1Reads orElse v2Reads

  implicit val writes: Writes[CustomsOffice] = Json.writes[CustomsOffice]
}
