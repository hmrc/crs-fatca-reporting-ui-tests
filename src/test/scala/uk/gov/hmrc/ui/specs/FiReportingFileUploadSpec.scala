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

import uk.gov.hmrc.ui.pages._
import uk.gov.hmrc.ui.specs.tags.*

class FiReportingFileUploadSpec extends BaseSpec {

  Feature("Upload File Journey") {

    Scenario("Upload Journey (Standard FI)", ReportingTests) {
      Given("The user logs in as an individual")
      AuthLoginPage.loginAsBasic()
      When("The user hits the uploading page and continues file upload journey")
      UploadFilePage.checkPage()

    }
    Scenario("Upload Journey for Fi is user", ReportingTests) {
      Given("The User log in as an organisation")
      AuthLoginPage.loginAsOrganisationUser()
      When("The user hits the uploading page and continues file upload journey")
      UploadFilePage.checkPage()

    }

    Scenario("Upload Journey for Organisation CT user", ReportingTests) {
      Given("The user login in as an organisation CT user")
      AuthLoginPage.loginAsAutoMatchedUser()
      When("The user hits the uploading page and continues file upload journey")
      UploadFilePage.checkPage()

    }
  }
}
