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

import base.SpecBase
import play.api.libs.json.Json

class CustomsOfficeSpec extends SpecBase {

  "reads" - {
    "must successfully read" - {
      "when v1" in {
        val json = Json.parse("""
            |{
            |    "phoneNumber": "03000599436",
            |    "roles": [
            |        {
            |            "role": "DEP"
            |        }
            |    ],
            |    "name": "Heysham",
            |    "state": "valid",
            |    "id": "GB000008",
            |    "languageCode": "EN",
            |    "eMailAddress": "babatunde.adewole@hmrc.gsi.gov.uk",
            |    "activeFrom": "2018-12-01",
            |    "countryId": "GB"
            |}
            |""".stripMargin)

        val result = json.as[CustomsOffice]

        result mustEqual CustomsOffice(
          languageCode = "EN",
          name = "Heysham",
          phoneNumber = Some("03000599436"),
          emailAddress = Some("babatunde.adewole@hmrc.gsi.gov.uk"),
          id = "GB000008",
          countryId = "GB",
          roles = Set("DEP")
        )
      }

      "when v2" in {
        val json = Json.parse("""
            |{
            |    "referenceNumber": "GB000008",
            |    "referenceNumberMainOffice": null,
            |    "referenceNumberHigherAuthority": "GB000001",
            |    "referenceNumberCompetentAuthorityOfEnquiry": "GB000001",
            |    "referenceNumberCompetentAuthorityOfRecovery": "GB000001",
            |    "referenceNumberTakeover": null,
            |    "countryCode": "GB",
            |    "emailAddress": "babatunde.adewole@hmrc.gsi.gov.uk",
            |    "unLocodeId": null,
            |    "nctsEntryDate": null,
            |    "nearestOffice": null,
            |    "postalCode": "SS99 1AA",
            |    "phoneNumber": "03000599436",
            |    "faxNumber": "03000599000",
            |    "telexNumber": null,
            |    "geoInfoCode": "Q",
            |    "regionCode": null,
            |    "traderDedicated": false,
            |    "dedicatedTraderLanguageCode": null,
            |    "dedicatedTraderName": null,
            |    "customsOfficeSpecificNotesCodes": [],
            |    "customsOfficeLsd": {
            |        "city": "Harwich",
            |        "languageCode": "EN",
            |        "spaceToAdd": false,
            |        "customsOfficeUsualName": "Heysham",
            |        "prefixSuffixFlag": false,
            |        "streetAndNumber": "c/o Simps Team, Custom Hse, Main Rd"
            |    },
            |    "customsOfficeTimetable": {
            |        "seasonCode": 1,
            |        "seasonStartDate": "2018-09-01",
            |        "seasonEndDate": "2018-12-31",
            |        "customsOfficeTimetableLine": [
            |            {
            |                "dayInTheWeekEndDay": 5,
            |                "openingHoursTimeFirstPeriodFrom": "09:00:00",
            |                "openingHoursTimeSecondPeriodTo": "00:00:00",
            |                "dayInTheWeekBeginDay": 1,
            |                "openingHoursTimeSecondPeriodFrom": "00:00:00",
            |                "openingHoursTimeFirstPeriodTo": "17:00:00",
            |                "customsOfficeRoleTrafficCompetence": [
            |                    {
            |                        "roleName": "DEP",
            |                        "trafficType": "AIR"
            |                    }
            |                ]
            |            }
            |        ]
            |    }
            |}
            |""".stripMargin)

        val result = json.as[CustomsOffice]

        result mustEqual CustomsOffice(
          languageCode = "EN",
          name = "Heysham",
          phoneNumber = Some("03000599436"),
          emailAddress = Some("babatunde.adewole@hmrc.gsi.gov.uk"),
          id = "GB000008",
          countryId = "GB",
          roles = Set("DEP")
        )
      }
    }
  }
}
