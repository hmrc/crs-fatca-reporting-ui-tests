/*
 * Copyright 2026 HM Revenue & Customs
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

class FiReportingProblemGiinNotSentSpec extends BaseSpec {

  Feature("FI Reporting - GIIN not sent problem page") {

    Scenario("GIIN not sent - elections sent successfully", ReportingTests, SoloTests) {

      And("The user logs in as an auto matched user")
      AuthLoginPage.loginAsAutoMatchedUser()

      When("The user uploads a valid CRS XML file")
      UploadFilePage.onPage().fileUpload("valid-fatca-giin-not-sent-election-sent-and-election-already-provided.xml")

      And("They enter a GIIN if prompted")
      RequiredGIINPage.maybeEnterGiin()

      And("They choose to send elections for the reporting period")
      ReportElectionsPage.selectYesAndContinue()

      And("They answer the FATCA questions (US treasury regulations and thresholds)")
      FatcaUSTreasuryRegulationsPage.selectYesAndContinue()
      FatcaThresholdsPage.selectYesAndContinue()

      And("They continue from Check your file details")
      CheckYourFileDetailsPage.submitPage()

      And("They confirm and send the file")
      SendYourFilePage.submitFileForValidation()

      Then("The GIIN not sent problem page is displayed with elections-sent content")
      ProblemGiinNotSentPage.onPage()
      ProblemGiinNotSentPage.assertElectionsSentVariant()
    }

    Scenario("GIIN not sent - elections not provided", ReportingTests, SoloTests) {

      And("The user logs in as an auto matched user")
      AuthLoginPage.loginAsAutoMatchedUser()

      When("The user uploads a valid CRS XML file")
      UploadFilePage.onPage().fileUpload("valid-fatca-giin-not-sent-election-sent-and-election-already-provided.xml")

      And("They enter a GIIN if prompted")
      RequiredGIINPage.maybeEnterGiin()

      And("They choose not to send elections for the reporting period")
      ReportElectionsPage.selectNoAndContinue()

      And("They continue from Check your file details")
      CheckYourFileDetailsPage.submitPage()

      And("They confirm and send the file")
      SendYourFilePage.submitFileForValidation()

      Then("The GIIN not sent problem page is displayed with elections-not-provided content")
      ProblemGiinNotSentPage.onPage()
      ProblemGiinNotSentPage.assertElectionsNotProvidedVariant()
    }

    Scenario("GIIN not sent - elections failed", ReportingTests, SoloTests) {

      And("The user logs in as an auto matched user")
      AuthLoginPage.loginAsAutoMatchedUser()

      When("The user uploads a valid CRS XML file")
      UploadFilePage.onPage().fileUpload("valid-fatca-giin-not-sent-election-failed.xml")

      And("They enter a GIIN if prompted")
      RequiredGIINPage.maybeEnterGiin()

      And("They choose to send elections for the reporting period")
      ReportElectionsPage.selectYesAndContinue()

      And("They answer the FATCA questions (US treasury regulations and thresholds)")
      FatcaUSTreasuryRegulationsPage.selectYesAndContinue()
      FatcaThresholdsPage.selectYesAndContinue()

      And("They continue from Check your file details")
      CheckYourFileDetailsPage.submitPage()

      And("They confirm and send the file")
      SendYourFilePage.submitFileForValidation()

      Then("The GIIN not sent problem page is displayed with elections-failed-too content")
      ProblemGiinNotSentPage.onPage()
      ProblemGiinNotSentPage.assertElectionsFailedTooVariant()
    }
  }
}
