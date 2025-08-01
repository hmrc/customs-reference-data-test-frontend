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

import base.SpecBase
import models.ApiDataSource._
import org.scalacheck.Gen
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import play.api.libs.json.{JsArray, Json}

class CodeListSpec extends SpecBase with ScalaCheckPropertyChecks {

  "CodeList" - {
    "when valid code list" - {
      "AdditionalInformation" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-06-20</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="AdditionalInformationCode">00200</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Several occurrences of documents and parties</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("AdditionalInformation").value

        result.name mustEqual "AdditionalInformation"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-06-20",
            "code"        -> "00200",
            "description" -> "Several occurrences of documents and parties"
          )
        )
      }

      "AdditionalReference" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-01-18</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="DocumentType">C651</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Electronic administrative document (e-AD), as referred to in Article 3(1) of Reg. (EC) No 684/2009</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("AdditionalReference").value

        result.name mustEqual "AdditionalReference"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"        -> "valid",
            "activeFrom"   -> "2024-01-18",
            "documentType" -> "C651",
            "description"  -> "Electronic administrative document (e-AD), as referred to in Article 3(1) of Reg. (EC) No 684/2009"
          )
        )
      }

      "AdditionalSupplyChainActorRoleCode" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-07-09</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="Role">CS</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Consolidator</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("AdditionalSupplyChainActorRoleCode").value

        result.name mustEqual "AdditionalSupplyChainActorRoleCode"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-07-09",
            "role"        -> "CS",
            "description" -> "Consolidator"
          )
        )
      }

      "AuthorisationTypeDeparture" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-07-09</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="AuthorisationType">C521</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">ACR - Authorisation for the status of...</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("AuthorisationTypeDeparture").value

        result.name mustEqual "AuthorisationTypeDeparture"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-07-09",
            "code"        -> "C521",
            "description" -> "ACR - Authorisation for the status of..."
          )
        )
      }

      "AuthorisationTypeDestination" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-07-09</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="AuthorisationType">C520</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">ACT - Authorisation for the status of...</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("AuthorisationTypeDestination").value

        result.name mustEqual "AuthorisationTypeDestination"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-07-09",
            "code"        -> "C520",
            "description" -> "ACT - Authorisation for the status of..."
          )
        )
      }

      "BusinessRejectionTypeDepExp" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-06-20</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="BusinessRejectionTypeDepExpCode">013</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Amendment rejection</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("BusinessRejectionTypeDepExp").value

        result.name mustEqual "BusinessRejectionTypeDepExp"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-06-20",
            "code"        -> "013",
            "description" -> "Amendment rejection"
          )
        )
      }

      "CUSCode" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2021-02-22</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="CUSCode">0010001-6</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">https://ec.europa.eu/taxation_customs/dds2/ecics/chemicalsubstance_details.jsp?Lang=en &amp; Cus=0010001-6</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("CUSCode").value

        result.name mustEqual "CUSCode"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"      -> "valid",
            "activeFrom" -> "2021-02-22",
            "code"       -> "0010001-6"
          )
        )
      }

      "ControlType" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-07-09</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="Code">10</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Documentary controls</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("ControlType").value

        result.name mustEqual "ControlType"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-07-09",
            "code"        -> "10",
            "description" -> "Documentary controls"
          )
        )
      }

      "CountryAddressPostcodeBased" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-02-21</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="CountryCode">IE</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Ireland</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("CountryAddressPostcodeBased").value

        result.name mustEqual "CountryAddressPostcodeBased"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-02-21",
            "code"        -> "IE",
            "description" -> "Ireland"
          )
        )
      }

      "CountryAddressPostcodeOnly" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-02-21</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="CountryCode">IE</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Ireland</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("CountryAddressPostcodeOnly").value

        result.name mustEqual "CountryAddressPostcodeOnly"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-02-21",
            "code"        -> "IE",
            "description" -> "Ireland"
          )
        )
      }

      "CountryCodesCTC" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-02-21</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="CountryCode">CH</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Switzerland</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("CountryCodesCTC").value

        result.name mustEqual "CountryCodesCTC"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-02-21",
            "code"        -> "CH",
            "description" -> "Switzerland"
          )
        )
      }

      "CountryCodesCommonTransit" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-02-21</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="CountryCode">AD</ns4:dataItem>
            <ns4:dataItem name="TccEntryDate">19000101</ns4:dataItem>
            <ns4:dataItem name="NctsEntryDate">19000101</ns4:dataItem>
            <ns4:dataItem name="GeoNomenclatureCode">043</ns4:dataItem>
            <ns4:dataItem name="CountryRegimeCode">TOC</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Andorra</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("CountryCodesCommonTransit").value

        result.name mustEqual "CountryCodesCommonTransit"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-02-21",
            "code"        -> "AD",
            "description" -> "Andorra"
          )
        )
      }

      "CountryCodesCommunity" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-02-21</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="CountryCode">AT</ns4:dataItem>
            <ns4:dataItem name="TccEntryDate">19000101</ns4:dataItem>
            <ns4:dataItem name="NctsEntryDate">19000101</ns4:dataItem>
            <ns4:dataItem name="GeoNomenclatureCode">038</ns4:dataItem>
            <ns4:dataItem name="CountryRegimeCode">EEC</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Austria</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("CountryCodesCommunity").value

        result.name mustEqual "CountryCodesCommunity"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-02-21",
            "code"        -> "AT",
            "description" -> "Austria"
          )
        )
      }

      "CountryCodesForAddress" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-02-21</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="CountryCode">AD</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Andorra</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("CountryCodesForAddress").value

        result.name mustEqual "CountryCodesForAddress"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-02-21",
            "code"        -> "AD",
            "description" -> "Andorra"
          )
        )
      }

      "CountryCodesFullList" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-02-21</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="CountryCode">AD</ns4:dataItem>
            <ns4:dataItem name="TccEntryDate">19000101</ns4:dataItem>
            <ns4:dataItem name="NctsEntryDate">19000101</ns4:dataItem>
            <ns4:dataItem name="GeoNomenclatureCode">043</ns4:dataItem>
            <ns4:dataItem name="CountryRegimeCode">TOC</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Andorra</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("CountryCodesFullList").value

        result.name mustEqual "CountryCodesFullList"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-02-21",
            "code"        -> "AD",
            "description" -> "Andorra"
          )
        )
      }

      "CountryCustomsSecurityAgreementArea" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-02-21</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="CountryCode">AD</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Andorra</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("CountryCustomsSecurityAgreementArea").value

        result.name mustEqual "CountryCustomsSecurityAgreementArea"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-02-21",
            "code"        -> "AD",
            "description" -> "Andorra"
          )
        )
      }

      "CountryWithoutZip" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2023-06-07</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="CountryCode">AD</ns4:dataItem>
          </ns3:RDEntry>

        val result = CodeList("CountryWithoutZip").value

        result.name mustEqual "CountryWithoutZip"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"      -> "valid",
            "activeFrom" -> "2023-06-07",
            "code"       -> "AD"
          )
        )
      }

      "CurrencyCodes" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-02-21</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="Currency">BGN</ns4:dataItem>
            <ns4:dataItem name="RateValue">1.9558</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Bulgarian lev</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("CurrencyCodes").value

        result.name mustEqual "CurrencyCodes"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-02-21",
            "currency"    -> "BGN",
            "description" -> "Bulgarian lev"
          )
        )
      }

      "CustomsOffices" in {
        val xml =
          <ns1:RDEntry>
            <ns2:RDEntryStatus>
              <ns3:state>valid</ns3:state>
              <ns3:activeFrom>2019-01-01</ns3:activeFrom>
            </ns2:RDEntryStatus>
            <ns2:dataItem name="ReferenceNumber">AD000002</ns2:dataItem>
            <ns2:dataItem name="ReferenceNumberMainOffice">AD000003</ns2:dataItem>
            <ns2:dataItem name="ReferenceNumberHigherAuthority">AD000003</ns2:dataItem>
            <ns2:dataItem name="ReferenceNumberCompetentAuthorityOfEnquiry">AD000003</ns2:dataItem>
            <ns2:dataItem name="ReferenceNumberCompetentAuthorityOfRecovery">AD000003</ns2:dataItem>
            <ns2:dataItem name="CountryCode">AD</ns2:dataItem>
            <ns2:dataItem name="UnLocodeId">ALV</ns2:dataItem>
            <ns2:dataItem name="NctsEntryDate">20070614</ns2:dataItem>
            <ns2:dataItem name="NearestOffice">FR002510</ns2:dataItem>
            <ns2:dataItem name="PostalCode">AD200</ns2:dataItem>
            <ns2:dataItem name="PhoneNumber">+ (376) 755125</ns2:dataItem>
            <ns2:dataItem name="FaxNumber">+ (376) 856202</ns2:dataItem>
            <ns2:dataItem name="EMailAddress">duana.pasdelacasa@andorra.ad</ns2:dataItem>
            <ns2:dataItem name="GeoInfoCode">FR/AD</ns2:dataItem>
            <ns2:dataItem name="TraderDedicated">0</ns2:dataItem>
            <ns2:dataGroup name="CustomsOfficeLsd">
              <ns2:dataItem name="LanguageCode">EN</ns2:dataItem>
              <ns2:dataItem name="CustomsOfficeUsualName">DCNJ PORTA</ns2:dataItem>
              <ns2:dataItem name="StreetAndNumber">BORDER PAS DE LA CASA</ns2:dataItem>
              <ns2:dataItem name="City">PAS DE LA CASA</ns2:dataItem>
              <ns2:dataItem name="PrefixSuffixFlag">0</ns2:dataItem>
              <ns2:dataItem name="SpaceToAdd">0</ns2:dataItem>
            </ns2:dataGroup>
            <ns2:dataGroup name="CustomsOfficeLsd">
              <ns2:dataItem name="LanguageCode">ES</ns2:dataItem>
              <ns2:dataItem name="CustomsOfficeUsualName">DCNJ PORTA</ns2:dataItem>
              <ns2:dataItem name="StreetAndNumber">PAS DE LA CASA</ns2:dataItem>
              <ns2:dataItem name="City">PAS DE LA CASA</ns2:dataItem>
              <ns2:dataItem name="PrefixSuffixFlag">0</ns2:dataItem>
              <ns2:dataItem name="SpaceToAdd">0</ns2:dataItem>
            </ns2:dataGroup>
            <ns2:dataGroup name="CustomsOfficeLsd">
              <ns2:dataItem name="LanguageCode">FR</ns2:dataItem>
              <ns2:dataItem name="CustomsOfficeUsualName">DCNJ PORTA</ns2:dataItem>
              <ns2:dataItem name="StreetAndNumber">PAS DE LA CASA</ns2:dataItem>
              <ns2:dataItem name="City">PAS DE LA CASA</ns2:dataItem>
              <ns2:dataItem name="PrefixSuffixFlag">0</ns2:dataItem>
              <ns2:dataItem name="SpaceToAdd">0</ns2:dataItem>
            </ns2:dataGroup>
            <ns2:dataGroup name="CustomsOfficeTimetable">
              <ns2:dataItem name="SeasonCode">1</ns2:dataItem>
              <ns2:dataItem name="SeasonName">ALL YEAR</ns2:dataItem>
              <ns2:dataItem name="SeasonStartDate">20180101</ns2:dataItem>
              <ns2:dataItem name="SeasonEndDate">20991231</ns2:dataItem>
              <ns2:dataGroup name="CustomsOfficeTimetableLine">
                <ns2:dataItem name="DayInTheWeekBeginDay">1</ns2:dataItem>
                <ns2:dataItem name="OpeningHoursTimeFirstPeriodFrom">0800</ns2:dataItem>
                <ns2:dataItem name="OpeningHoursTimeFirstPeriodTo">2000</ns2:dataItem>
                <ns2:dataItem name="DayInTheWeekEndDay">5</ns2:dataItem>
                <ns2:dataGroup name="CustomsOfficeRoleTrafficCompetence">
                  <ns2:dataItem name="Role">DEP</ns2:dataItem>
                  <ns2:dataItem name="TrafficType">R</ns2:dataItem>
                </ns2:dataGroup>
                <ns2:dataGroup name="CustomsOfficeRoleTrafficCompetence">
                  <ns2:dataItem name="Role">DES</ns2:dataItem>
                  <ns2:dataItem name="TrafficType">R</ns2:dataItem>
                </ns2:dataGroup>
                <ns2:dataGroup name="CustomsOfficeRoleTrafficCompetence">
                  <ns2:dataItem name="Role">TRA</ns2:dataItem>
                  <ns2:dataItem name="TrafficType">R</ns2:dataItem>
                </ns2:dataGroup>
              </ns2:dataGroup>
              <ns2:dataGroup name="CustomsOfficeTimetableLine">
                <ns2:dataItem name="DayInTheWeekBeginDay">6</ns2:dataItem>
                <ns2:dataItem name="OpeningHoursTimeFirstPeriodFrom">0800</ns2:dataItem>
                <ns2:dataItem name="OpeningHoursTimeFirstPeriodTo">1200</ns2:dataItem>
                <ns2:dataItem name="DayInTheWeekEndDay">6</ns2:dataItem>
                <ns2:dataGroup name="CustomsOfficeRoleTrafficCompetence">
                  <ns2:dataItem name="Role">DEP</ns2:dataItem>
                  <ns2:dataItem name="TrafficType">R</ns2:dataItem>
                </ns2:dataGroup>
                <ns2:dataGroup name="CustomsOfficeRoleTrafficCompetence">
                  <ns2:dataItem name="Role">DES</ns2:dataItem>
                  <ns2:dataItem name="TrafficType">R</ns2:dataItem>
                </ns2:dataGroup>
                <ns2:dataGroup name="CustomsOfficeRoleTrafficCompetence">
                  <ns2:dataItem name="Role">TRA</ns2:dataItem>
                  <ns2:dataItem name="TrafficType">R</ns2:dataItem>
                </ns2:dataGroup>
              </ns2:dataGroup>
            </ns2:dataGroup>
          </ns1:RDEntry>

        val result = CodeList("CustomsOffices").value

        result.name mustEqual "customsOffices"

        result.source mustEqual ColDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"        -> "valid",
            "activeFrom"   -> "2019-01-01",
            "languageCode" -> "EN",
            "name"         -> "DCNJ PORTA",
            "phoneNumber"  -> "+ (376) 755125",
            "eMailAddress" -> "duana.pasdelacasa@andorra.ad",
            "id"           -> "AD000002",
            "countryId"    -> "AD",
            "roles" -> JsArray(
              Seq(
                Json.obj("role" -> "DEP"),
                Json.obj("role" -> "DES"),
                Json.obj("role" -> "TRA")
              )
            )
          ),
          Json.obj(
            "state"        -> "valid",
            "activeFrom"   -> "2019-01-01",
            "languageCode" -> "ES",
            "name"         -> "DCNJ PORTA",
            "phoneNumber"  -> "+ (376) 755125",
            "eMailAddress" -> "duana.pasdelacasa@andorra.ad",
            "id"           -> "AD000002",
            "countryId"    -> "AD",
            "roles" -> JsArray(
              Seq(
                Json.obj("role" -> "DEP"),
                Json.obj("role" -> "DES"),
                Json.obj("role" -> "TRA")
              )
            )
          ),
          Json.obj(
            "state"        -> "valid",
            "activeFrom"   -> "2019-01-01",
            "languageCode" -> "FR",
            "name"         -> "DCNJ PORTA",
            "phoneNumber"  -> "+ (376) 755125",
            "eMailAddress" -> "duana.pasdelacasa@andorra.ad",
            "id"           -> "AD000002",
            "countryId"    -> "AD",
            "roles" -> JsArray(
              Seq(
                Json.obj("role" -> "DEP"),
                Json.obj("role" -> "DES"),
                Json.obj("role" -> "TRA")
              )
            )
          )
        )
      }

      "DeclarationType" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-06-20</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="DeclarationTypeCode">T</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Mixed consignments comprising...</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("DeclarationType").value

        result.name mustEqual "DeclarationType"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-06-20",
            "code"        -> "T",
            "description" -> "Mixed consignments comprising..."
          )
        )
      }

      "DeclarationTypeAdditional" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-07-09</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="Code">A</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">for a standard customs declaration (under Article 162 of the Code)</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("DeclarationTypeAdditional").value

        result.name mustEqual "DeclarationTypeAdditional"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-07-09",
            "code"        -> "A",
            "description" -> "for a standard customs declaration (under Article 162 of the Code)"
          )
        )
      }

      "DeclarationTypeItemLevel" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-06-20</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="DeclarationTypeCode">T1</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Goods not having the customs status of Union goods, which are placed under the common transit procedure.</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("DeclarationTypeItemLevel").value

        result.name mustEqual "DeclarationTypeItemLevel"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-06-20",
            "code"        -> "T1",
            "description" -> "Goods not having the customs status of Union goods, which are placed under the common transit procedure."
          )
        )
      }

      "DeclarationTypeSecurity" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-03-18</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="DeclarationTypeSecurityCode">0</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Not used for safety and security purposes</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("DeclarationTypeSecurity").value

        result.name mustEqual "DeclarationTypeSecurity"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-03-18",
            "code"        -> "0",
            "description" -> "Not used for safety and security purposes"
          )
        )
      }

      "DocumentTypeExcise" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-03-18</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="PreviousDocumentTypeCode">C651</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">AAD - Administrative Accompanying Document (EMCS)</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("DocumentTypeExcise").value

        result.name mustEqual "DocumentTypeExcise"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-03-18",
            "code"        -> "C651",
            "description" -> "AAD - Administrative Accompanying Document (EMCS)"
          )
        )
      }

      "FunctionalErrorCodesIeCA" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-03-18</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="Code">12</ns4:dataItem>
            <ns4:dataItem name="Remark">Value of an element in a message is outside...</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Codelist violation</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("FunctionalErrorCodesIeCA").value

        result.name mustEqual "FunctionalErrorCodesIeCA"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-03-18",
            "code"        -> "12",
            "description" -> "Codelist violation"
          )
        )
      }

      "GuaranteeType" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-07-09</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="GuaranteeTypeCode">0</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Guarantee waiver</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("GuaranteeType").value

        result.name mustEqual "GuaranteeType"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-07-09",
            "code"        -> "0",
            "description" -> "Guarantee waiver"
          )
        )
      }

      "GuaranteeTypeWithGRN" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-07-09</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="GuaranteeTypeCode">0</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Guarantee waiver</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("GuaranteeTypeWithGRN").value

        result.name mustEqual "GuaranteeTypeWithGRN"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-07-09",
            "code"        -> "0",
            "description" -> "Guarantee waiver"
          )
        )
      }

      "GuaranteeTypeEUNonTIR" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-07-09</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="GuaranteeTypeCode">0</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Guarantee waiver</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("GuaranteeTypeEUNonTIR").value

        result.name mustEqual "GuaranteeTypeEUNonTIR"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-07-09",
            "code"        -> "0",
            "description" -> "Guarantee waiver"
          )
        )
      }

      "GuaranteeTypeCTC" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-07-09</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="GuaranteeTypeCode">0</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Guarantee waiver</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("GuaranteeTypeCTC").value

        result.name mustEqual "GuaranteeTypeCTC"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-07-09",
            "code"        -> "0",
            "description" -> "Guarantee waiver"
          )
        )
      }

      "GuaranteeTypeWithReference" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-07-09</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="GuaranteeTypeCode">0</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Guarantee waiver</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("GuaranteeTypeWithReference").value

        result.name mustEqual "GuaranteeTypeWithReference"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-07-09",
            "code"        -> "0",
            "description" -> "Guarantee waiver"
          )
        )
      }

      "HScode" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2023-05-20</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="Code">010121</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">See http://ec.europa.eu/taxation_customs/dds2/taric/measures.jsp?Lang=en &amp; Taric=010121</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("HScode").value

        result.name mustEqual "HScode"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"      -> "valid",
            "activeFrom" -> "2023-05-20",
            "code"       -> "010121"
          )
        )
      }

      "IncidentCode" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-05-21</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="Code">1</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">The carrier is obliged to deviate from...</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("IncidentCode").value

        result.name mustEqual "IncidentCode"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-05-21",
            "code"        -> "1",
            "description" -> "The carrier is obliged to deviate from..."
          )
        )
      }

      "InvalidGuaranteeReason" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-06-20</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="InvalidGuaranteeReasonCode">G01</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Guarantee does not exist</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("InvalidGuaranteeReason").value

        result.name mustEqual "InvalidGuaranteeReason"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-06-20",
            "code"        -> "G01",
            "description" -> "Guarantee does not exist"
          )
        )
      }

      "KindOfPackages" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-02-21</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="KindOfPackages">1A</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Drum, steel</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("KindOfPackages").value

        result.name mustEqual "KindOfPackages"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-02-21",
            "code"        -> "1A",
            "description" -> "Drum, steel"
          )
        )
      }

      "KindOfPackagesBulk" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-02-21</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="KindOfPackages">VG</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Bulk, gas (at 1031 mbar and 15°C)</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("KindOfPackagesBulk").value

        result.name mustEqual "KindOfPackagesBulk"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-02-21",
            "code"        -> "VG",
            "description" -> "Bulk, gas (at 1031 mbar and 15°C)"
          )
        )
      }

      "KindOfPackagesUnpacked" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-02-21</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="KindOfPackages">NE</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Unpacked or unpackaged</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("KindOfPackagesUnpacked").value

        result.name mustEqual "KindOfPackagesUnpacked"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-02-21",
            "code"        -> "NE",
            "description" -> "Unpacked or unpackaged"
          )
        )
      }

      "Nationality" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-02-21</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="CountryCode">AD</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Andorra</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("Nationality").value

        result.name mustEqual "Nationality"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-02-21",
            "code"        -> "AD",
            "description" -> "Andorra"
          )
        )
      }

      "PreviousDocumentExportType" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-03-18</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="PreviousDocumentTypeCode">N830</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Goods declaration for exportation</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("PreviousDocumentExportType").value

        result.name mustEqual "PreviousDocumentExportType"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-03-18",
            "code"        -> "N830",
            "description" -> "Goods declaration for exportation"
          )
        )
      }

      "PreviousDocumentType" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-03-18</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="PreviousDocumentTypeCode">C512</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">SDE - Authorisation to use simplified...</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("PreviousDocumentType").value

        result.name mustEqual "PreviousDocumentType"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-03-18",
            "code"        -> "C512",
            "description" -> "SDE - Authorisation to use simplified..."
          )
        )
      }

      "PreviousDocumentUnionGoods" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-03-18</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="PreviousDocumentTypeCode">C612</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Internal Community transit Declaration — Article 227 of the Code</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("PreviousDocumentUnionGoods").value

        result.name mustEqual "PreviousDocumentUnionGoods"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-03-18",
            "code"        -> "C612",
            "description" -> "Internal Community transit Declaration — Article 227 of the Code"
          )
        )
      }

      "QualifierOfIdentificationIncident" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-03-18</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="QualifierOfTheIdentification">U</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">UN/LOCODE</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("QualifierOfIdentificationIncident").value

        result.name mustEqual "QualifierOfIdentificationIncident"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-03-18",
            "qualifier"   -> "U",
            "description" -> "UN/LOCODE"
          )
        )
      }

      "QualifierOfTheIdentification" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-03-18</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="QualifierOfTheIdentification">T</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Postal code</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("QualifierOfTheIdentification").value

        result.name mustEqual "QualifierOfTheIdentification"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-03-18",
            "qualifier"   -> "T",
            "description" -> "Postal code"
          )
        )
      }

      "RejectionCodeDepartureExport" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-06-20</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="RejectionDepartureExportCode">12</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Message with functional error(s) - Violation of Rules &amp; Conditions</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("RejectionCodeDepartureExport").value

        result.name mustEqual "RejectionCodeDepartureExport"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-06-20",
            "code"        -> "12",
            "description" -> "Message with functional error(s) - Violation of Rules & Conditions"
          )
        )
      }

      "RepresentativeStatusCode" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-03-08</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="RepresentativeStatusCode">2</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Representative - direct representation (within the meaning of Article 18(1) of the Code)</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("RepresentativeStatusCode").value

        result.name mustEqual "RepresentativeStatusCode"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-03-08",
            "code"        -> "2",
            "description" -> "Representative - direct representation (within the meaning of Article 18(1) of the Code)"
          )
        )
      }

      "RequestedDocumentType" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2022-09-07</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="DocumentType">C085</ns4:dataItem>
            <ns4:dataItem name="TransportDocument">0</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Common Health Entry Document for...</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("RequestedDocumentType").value

        result.name mustEqual "RequestedDocumentType"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2022-09-07",
            "code"        -> "C085",
            "description" -> "Common Health Entry Document for..."
          )
        )
      }

      "SpecificCircumstanceIndicatorCode" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-03-18</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="Code">A20</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Express consignments in the context of exit summary declarations</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("SpecificCircumstanceIndicatorCode").value

        result.name mustEqual "SpecificCircumstanceIndicatorCode"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-03-18",
            "code"        -> "A20",
            "description" -> "Express consignments in the context of exit summary declarations"
          )
        )
      }

      "SupportingDocumentType" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-07-09</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="SupportingDocumentCode">C085</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Common Health Entry Document for...</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("SupportingDocumentType").value

        result.name mustEqual "SupportingDocumentType"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-07-09",
            "code"        -> "C085",
            "description" -> "Common Health Entry Document for..."
          )
        )
      }

      "TransportChargesMethodOfPayment" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-06-27</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="TransportChargesMethodOfPayment">A</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Payment in cash</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("TransportChargesMethodOfPayment").value

        result.name mustEqual "TransportChargesMethodOfPayment"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-06-27",
            "method"      -> "A",
            "description" -> "Payment in cash"
          )
        )
      }

      "TransportDocumentType" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-05-29</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="Type">N235</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Container list</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("TransportDocumentType").value

        result.name mustEqual "TransportDocumentType"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-05-29",
            "code"        -> "N235",
            "description" -> "Container list"
          )
        )
      }

      "TransportModeCode" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-06-20</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="TransportModeCode">1</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Maritime Transport</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("TransportModeCode").value

        result.name mustEqual "TransportModeCode"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-06-20",
            "code"        -> "1",
            "description" -> "Maritime Transport"
          )
        )
      }

      "TypeOfIdentificationOfMeansOfTransport" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-07-09</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="TypeOfIdentification">10</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">IMO Ship Identification Number</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("TypeOfIdentificationOfMeansOfTransport").value

        result.name mustEqual "TypeOfIdentificationOfMeansOfTransport"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-07-09",
            "type"        -> "10",
            "description" -> "IMO Ship Identification Number"
          )
        )
      }

      "TypeOfIdentificationOfMeansOfTransportActive" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-05-29</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="TypeOfIdentificationofMeansOfTransportActiveCode">10</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">IMO Ship Identification Number</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("TypeOfIdentificationofMeansOfTransportActive").value

        result.name mustEqual "TypeOfIdentificationofMeansOfTransportActive"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-05-29",
            "code"        -> "10",
            "description" -> "IMO Ship Identification Number"
          )
        )
      }

      "TypeOfLocation" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-03-18</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="TypeOfLocation">A</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">Designated location</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("TypeOfLocation").value

        result.name mustEqual "TypeOfLocation"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-03-18",
            "type"        -> "A",
            "description" -> "Designated location"
          )
        )
      }

      "UnDangerousGoodsCode" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-02-21</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="UnDangerousGoodsCode">0004</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">AMMONIUM PICRATE dry or wetted with less than 10% water, by mass</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("UnDangerousGoodsCode").value

        result.name mustEqual "UnDangerousGoodsCode"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-02-21",
            "code"        -> "0004",
            "description" -> "AMMONIUM PICRATE dry or wetted with less than 10% water, by mass"
          )
        )
      }

      "UnLocodeExtended" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2023-04-13</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="UnLocodeExtendedCode">ADALV</ns4:dataItem>
            <ns4:dataItem name="Name">Andorra la Vella</ns4:dataItem>
            <ns4:dataItem name="Function">--34-6--</ns4:dataItem>
            <ns4:dataItem name="Status">AI</ns4:dataItem>
            <ns4:dataItem name="Date">0601</ns4:dataItem>
            <ns4:dataItem name="Coordinates">4230N 00131E</ns4:dataItem>
            <ns4:dataItem name="Comment">Muy Vella</ns4:dataItem>
          </ns3:RDEntry>

        val result = CodeList("UnLocodeExtended").value

        result.name mustEqual "UnLocodeExtended"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"                -> "valid",
            "activeFrom"           -> "2023-04-13",
            "unLocodeExtendedCode" -> "ADALV",
            "name"                 -> "Andorra la Vella"
          )
        )
      }

      "Unit" in {
        val xml =
          <ns3:RDEntry>
            <ns4:RDEntryStatus>
              <ns5:state>valid</ns5:state>
              <ns5:activeFrom>2024-07-09</ns5:activeFrom>
            </ns4:RDEntryStatus>
            <ns4:dataItem name="Unit">ASV</ns4:dataItem>
            <ns4:LsdList>
              <ns7:description lang="en">%vol</ns7:description>
            </ns4:LsdList>
          </ns3:RDEntry>

        val result = CodeList("Unit").value

        result.name mustEqual "Unit"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "state"       -> "valid",
            "activeFrom"  -> "2024-07-09",
            "code"        -> "ASV",
            "description" -> "%vol"
          )
        )
      }

      "AdditionalInformationCodeSubset" in {
        val xml =
          <ns19:RDEntry>
            <ns20:RDEntryStatus>
              <ns21:state>valid</ns21:state>
              <ns21:activeFrom>2024-10-18</ns21:activeFrom>
              <ns21:changeJustification>IM582632 IM746136</ns21:changeJustification>
            </ns20:RDEntryStatus>
            <ns20:dataItem name="Code">A00</ns20:dataItem>
            <ns20:LsdList>
              <ns23:description lang="en">Amendment is not possible</ns23:description>
            </ns20:LsdList>
          </ns19:RDEntry>

        val result = CodeList("AdditionalInformationCodeSubset").value

        result.name mustEqual "AdditionalInformationCodeSubset"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "key"   -> "A00",
            "value" -> "Amendment is not possible"
          )
        )
      }

      "BusinessRejectionTypeTED2Dep" in {
        val xml =
          <ns19:RDEntry>
            <ns20:RDEntryStatus>
              <ns21:state>valid</ns21:state>
              <ns21:activeFrom>2024-12-11</ns21:activeFrom>
              <ns21:changeJustification>IM555918 IM743640 IM169339</ns21:changeJustification>
            </ns20:RDEntryStatus>
            <ns20:dataItem name="BusinessRejectionTypeTED2DepCode">014</ns20:dataItem>
            <ns20:LsdList>
              <ns23:description lang="en">Rejection of CD014D - DECLARATION INVALIDATION REQUEST ENS (C_DEC_INV_ENS)</ns23:description>
            </ns20:LsdList>
          </ns19:RDEntry>

        val result = CodeList("BusinessRejectionTypeTED2Dep").value

        result.name mustEqual "BusinessRejectionTypeTED2Dep"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "key"   -> "014",
            "value" -> "Rejection of CD014D - DECLARATION INVALIDATION REQUEST ENS (C_DEC_INV_ENS)"
          )
        )
      }

      "BusinessRejectionTypeTra" in {
        val xml =
          <ns19:RDEntry>
            <ns20:RDEntryStatus>
              <ns21:state>valid</ns21:state>
              <ns21:activeFrom>2024-12-11</ns21:activeFrom>
              <ns21:changeJustification>IM555920 IM743640 IM169339</ns21:changeJustification>
            </ns20:RDEntryStatus>
            <ns20:dataItem name="BusinessRejectionTypeTraCode">117</ns20:dataItem>
            <ns20:LsdList>
              <ns23:description lang="en">Rejection of CC117D - PRESENTATION NOTIFICATION AT OFFICE OF TRANSIT (E_TRA_PRE_NOT)</ns23:description>
            </ns20:LsdList>
          </ns19:RDEntry>

        val result = CodeList("BusinessRejectionTypeTra").value

        result.name mustEqual "BusinessRejectionTypeTra"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "key"   -> "117",
            "value" -> "Rejection of CC117D - PRESENTATION NOTIFICATION AT OFFICE OF TRANSIT (E_TRA_PRE_NOT)"
          )
        )
      }

      "CountryCodesOptout" in {
        val xml =
          <ns19:RDEntry>
            <ns20:RDEntryStatus>
              <ns21:state>valid</ns21:state>
              <ns21:activeFrom>2024-01-17</ns21:activeFrom>
              <ns21:changeJustification>IM640024</ns21:changeJustification>
            </ns20:RDEntryStatus>
            <ns20:dataItem name="CountryCode">CH</ns20:dataItem>
            <ns20:dataItem name="TccEntryDate">19000101</ns20:dataItem>
            <ns20:dataItem name="NctsEntryDate">19000101</ns20:dataItem>
            <ns20:dataItem name="GeoNomenclatureCode">039</ns20:dataItem>
            <ns20:dataItem name="CountryRegimeCode">TOC</ns20:dataItem>
            <ns20:LsdList>
              <ns23:description lang="en">Switzerland</ns23:description>
            </ns20:LsdList>
          </ns19:RDEntry>

        val result = CodeList("CountryCodesOptout").value

        result.name mustEqual "CountryCodesOptout"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "key"   -> "CH",
            "value" -> "Switzerland"
          )
        )
      }

      "CountryCodesWithAddress" in {
        val xml =
          <ns19:RDEntry>
            <ns20:RDEntryStatus>
              <ns21:state>valid</ns21:state>
              <ns21:activeFrom>2024-10-01</ns21:activeFrom>
              <ns21:changeJustification>IM640024 IM732889</ns21:changeJustification>
            </ns20:RDEntryStatus>
            <ns20:dataItem name="CountryCode">AD</ns20:dataItem>
            <ns20:LsdList>
              <ns23:description lang="en">Andorra</ns23:description>
            </ns20:LsdList>
          </ns19:RDEntry>

        val result = CodeList("CountryCodesWithAddress").value

        result.name mustEqual "CountryCodesWithAddress"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "key"   -> "AD",
            "value" -> "Andorra"
          )
        )
      }

      "FunctionErrorCodesTED" in {
        val xml =
          <ns19:RDEntry>
            <ns20:RDEntryStatus>
              <ns21:state>valid</ns21:state>
              <ns21:activeFrom>2024-12-11</ns21:activeFrom>
              <ns21:changeJustification>IM559781 IM169339</ns21:changeJustification>
            </ns20:RDEntryStatus>
            <ns20:dataItem name="Code">100</ns20:dataItem>
            <ns20:LsdList>
              <ns23:description lang="en">Not satisfactory data quality</ns23:description>
            </ns20:LsdList>
          </ns19:RDEntry>

        val result = CodeList("FunctionErrorCodesTED").value

        result.name mustEqual "FunctionErrorCodesTED"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "key"   -> "100",
            "value" -> "Not satisfactory data quality"
          )
        )
      }

      "RejectionCodeTransit" in {
        val xml =
          <ns19:RDEntry>
            <ns20:RDEntryStatus>
              <ns21:state>valid</ns21:state>
              <ns21:activeFrom>2024-12-11</ns21:activeFrom>
              <ns21:changeJustification>IM555915 IM743640 IM169339</ns21:changeJustification>
            </ns20:RDEntryStatus>
            <ns20:dataItem name="RejectionCodeTransitCode">1</ns20:dataItem>
            <ns20:LsdList>
              <ns23:description lang="en">MRN unknown</ns23:description>
            </ns20:LsdList>
          </ns19:RDEntry>

        val result = CodeList("RejectionCodeTransit").value

        result.name mustEqual "RejectionCodeTransit"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "key"   -> "1",
            "value" -> "MRN unknown"
          )
        )
      }

      "Role" in {
        val xml =
          <ns19:RDEntry>
            <ns20:RDEntryStatus>
              <ns21:state>valid</ns21:state>
              <ns21:activeFrom>2024-12-11</ns21:activeFrom>
              <ns21:changeJustification>IM640024 IM753869 IM169339 IM169339 IM169339</ns21:changeJustification>
            </ns20:RDEntryStatus>
            <ns20:dataItem name="Role">ACE</ns20:dataItem>
            <ns20:LsdList>
              <ns23:description lang="en">Customs authority responsible for granting the authorisation of authorised consignee for Union transit</ns23:description>
            </ns20:LsdList>
          </ns19:RDEntry>

        val result = CodeList("Role").value

        result.name mustEqual "Role"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "key"   -> "ACE",
            "value" -> "Customs authority responsible for granting the authorisation of authorised consignee for Union transit"
          )
        )
      }

      "XmlErrorCodes" in {
        val xml =
          <ns19:RDEntry>
            <ns20:RDEntryStatus>
              <ns21:state>valid</ns21:state>
              <ns21:activeFrom>2024-09-30</ns21:activeFrom>
              <ns21:changeJustification>IM686707 IM743640 IM745431 IM169339 IM169339 IM169339</ns21:changeJustification>
            </ns20:RDEntryStatus>
            <ns20:dataItem name="XmlErrorCodesCode">12</ns20:dataItem>
            <ns20:LsdList>
              <ns23:description lang="en">Incorrect enumeration</ns23:description>
            </ns20:LsdList>
          </ns19:RDEntry>

        val result = CodeList("XmlErrorCodes").value

        result.name mustEqual "XmlErrorCodes"

        result.source mustEqual RefDataFeed

        result.json(xml) mustEqual Seq(
          Json.obj(
            "key"   -> "12",
            "value" -> "Incorrect enumeration"
          )
        )
      }
    }

    "when invalid code list" in {
      forAll(Gen.alphaNumStr) {
        str =>
          val result = CodeList(str)
          result must not be defined
      }
    }
  }
}
