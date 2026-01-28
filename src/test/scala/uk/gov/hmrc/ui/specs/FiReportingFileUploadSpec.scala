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

package uk.gov.hmrc.ui.specs

import uk.gov.hmrc.ui.pages.*
import uk.gov.hmrc.ui.specs.tags.*

class FiReportingFileUploadSpec extends BaseSpec {

  Feature("Upload File Journey") {

    Scenario("Upload Journey (Standard FI)", ReportingTests, SoloTests) {
      AuthLoginPage.loginAsOrganisationUser()
      When("The user hits the uploading page and submits a valid XML file")

      UploadFilePage
        .onPage()
        .fileUpload("valid-crs-xml.xml")
      And("They choose to send elections for the reporting period")
      ReportElectionsPage.selectYesAndContinue()

      And("They answer the CRS questions (contracts, dormant accounts, thresholds)")
      CrsContractsPage.selectYesAndContinue()
      CrsDormantAccountsPage.selectYesAndContinue()
      CrsThresholdsPage.selectYesAndContinue()

      Then("They continue from the Check your file details page")
      CheckYourFileDetailsPage.submitPage()

    }

    Scenario(
      "Upload journey for Fi is user CRS and the reporting period is not within CY-12 and CY",
      ReportingTests,
      SoloTests
    ) {
      AuthLoginPage.loginAsOrganisationUser()
      When("The user hits the uploading page and submits a valid XML file")
      UploadFilePage
        .onPage()
        .fileUpload("valid-crs-CY-xml.xml")
      And("Continues the journey to Check your file details page")
      CheckYourFileDetailsPage.onPage()

    }

    Scenario("Upload journey for Fi is user FATCA", ReportingTests, SoloTests) {
      AuthLoginPage.loginAsOrganisationUser()
      When("The user hits the uploading page and submits a valid XML file")
      UploadFilePage
        .onPage()
        .fileUpload("valid-fatca-xml-fiWithoutGIIN.xml")
      Then("The user provides the GIIN if required")
      RequiredGIINPage.maybeEnterGiin()
      And("Continues the journey for any elections made already for the reporting period")
      ReportElectionsPage.selectYesAndContinue()
      And("US Treasury regulations and any thresholds for FATCA")
      FatcaUSTreasuryRegulationsPage.selectYesAndContinue()
      FatcaThresholdsPage.selectYesAndContinue()
      Then("The user can review their file details and continue")
      CheckYourFileDetailsPage.submitPage()
      SendYourFilePage.submitFileForValidation()
      StillCheckingYourFilePage.onPage()

    }

    Scenario(
      "Upload journey for Fi is user FATCA and the reporting period is not within CY-12 and CY",
      ReportingTests,
      SoloTests
    ) {
      AuthLoginPage.loginAsOrganisationUser()
      When("The user hits the uploading page and submits a valid XML file")
      UploadFilePage
        .onPage()
        .fileUpload("valid-fatca-CY-xml.xml")
      And("the user provides the GIIN if required")
      RequiredGIINPage.maybeEnterGiin()
      And("Continues the journey to Check your file details page")
      CheckYourFileDetailsPage.onPage()
    }
  }
}
