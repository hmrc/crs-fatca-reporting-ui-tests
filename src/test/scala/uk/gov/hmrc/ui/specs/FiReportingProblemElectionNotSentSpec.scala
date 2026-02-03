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

class FiReportingProblemElectionNotSentSpec extends BaseSpec {

  Feature("FI Reporting - Upload and problem pages") {

    Scenario("CRS file submission shows elections-not-sent problem page", ReportingTests, SoloTests) {

      And("The user logs in as an organisation")
      AuthLoginPage.loginAsOrganisationUser()
      When("The user hits the uploading page and submits a valid XML file")
      UploadFilePage
        .onPage()
        .fileUpload("valid-crs-election-not-sent-xml.xml")

      And("They choose not to send elections for the reporting period")
      ReportElectionsPage.selectNoAndContinue()

      Then("They continue from the Check your file details page")
      CheckYourFileDetailsPage.submitPage()
      And("They confirm and send the file")
      SendYourFilePage.submitFileForValidation()
      Then("The elections-not-sent problem page is displayed (election not provided)")
      ProblemElectionNotSentPage.onPage()
      ProblemElectionNotSentPage.assertElectionNotSentVariant()
    }

    Scenario("FATCA file submission shows elections-not-sent problem page", ReportingTests, SoloTests) {

      And("The user logs in as an organisation")
      AuthLoginPage.loginAsOrganisationUser()
      When("The user hits the uploading page and submits a valid XML file")
      UploadFilePage
        .onPage()
        .fileUpload("valid-fatca-election-not-sent-giin-to-be-provided.xml")

      And("They enter a GIIN if prompted")
      RequiredGIINPage.maybeEnterGiin()

      And("They choose to send elections for the reporting period")
      ReportElectionsPage.selectYesAndContinue()

      And("They answer the FATCA questions (US treasury regulations and thresholds)")
      FatcaUSTreasuryRegulationsPage.selectYesAndContinue()
      FatcaThresholdsPage.selectYesAndContinue()

      Then("They continue from the Check your file details page")
      CheckYourFileDetailsPage.submitPage()
      And("They confirm and send the file")
      SendYourFilePage.submitFileForValidation()
      Then("The elections-not-sent problem page is displayed (GIIN received, elections not sent)")
      ProblemElectionNotSentPage.onPage()
      ProblemElectionNotSentPage.assertGiinReceivedElectionsNotSentVariant()
    }
  }
}
