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
import uk.gov.hmrc.ui.utils.MongoTestUtils

class FiReportingProblemElectionNotSentSpec extends BaseSpec with MongoTestUtils {

  Feature("FI Reporting - Upload and problem pages") {

    Scenario("CRS file submission shows elections-not-sent problem page", ReportingTests, SoloTests) {

      Given("Mongo is clean")
      cleanUserAnswersCollection()

      And("The User logs in as an organisation")
      AuthLoginPage.loginAsOrganisationUser()

      When("The user hits the uploading page and submits a valid CRS XML file")
      UploadFilePage
        .onPage()
        .fileUpload("valid-crs-election-not-sent-xml.xml")

      And("They choose to send elections for the reporting period")
      ReportElectionsPage.selectYesAndContinue()

      And("They answer the CRS questions (contracts, dormant accounts, thresholds)")
      CrsContractsPage.selectYesAndContinue()
      CrsDormantAccountsPage.selectYesAndContinue()
      CrsThresholdsPage.selectYesAndContinue()

      Then("They continue from the Check your file details page")
      CheckYourFileDetailsPage.submitPage()
      And("They confirm and send the file")
      SendYourFilePage.submitFileForValidation()
      Then("The elections-not-sent problem page is displayed")
      ProblemElectionNotSentPage.onPage()
    }
  }
}
