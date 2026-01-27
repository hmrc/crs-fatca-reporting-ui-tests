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
import uk.gov.hmrc.ui.mongo.CrsFatcaSubmissionStatusHelper
import uk.gov.hmrc.ui.specs.tags.*
import uk.gov.hmrc.ui.utils.MongoTestUtils

class FiReportingProblemGiinNotSentSpec extends BaseSpec {

  Feature("FI Reporting - GIIN not sent problem page") {

    Scenario("GIIN not sent - elections sent successfully", ReportingTests, SoloTests) {

      Given("Mongo is clean")
      cleanUserAnswersCollection()

      And("The user logs in as an organisation")
      AuthLoginPage.loginAsAutoMatchedUser()

      When("The user uploads a valid CRS XML file")
      UploadFilePage.onPage().fileUpload("valid-fatca-giin-not-sent-xml.xml")

      RequiredGIINPage.maybeEnterGiin()

      /** The code below is for the election send journey. This will be implemented as part of ticket DAC6-3830.
        */

//      And("They choose to send elections")
//      ReportElectionsPage.selectYesAndContinue()
//
//      And("They answer CRS questions")
//      CrsContractsPage.selectYesAndContinue()
//      CrsDormantAccountsPage.selectYesAndContinue()
//      CrsThresholdsPage.selectYesAndContinue()

      And("They continue from Check your file details")
      CheckYourFileDetailsPage.submitPage()

      And("GIIN fails but elections are recorded as sent")
      CrsFatcaSubmissionStatusHelper.giinFailedElectionsSent()

      And("They confirm and send the file")
      SendYourFilePage.submitFileForValidation()

      Then("The GIIN not sent problem page is displayed with elections-sent content")
      ProblemGiinNotSentPage.onPage()
      ProblemGiinNotSentPage.assertPageIsDisplayed()

    }

  }
}
