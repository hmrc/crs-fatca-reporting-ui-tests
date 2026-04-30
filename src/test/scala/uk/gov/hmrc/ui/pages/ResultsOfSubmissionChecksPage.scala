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

package uk.gov.hmrc.ui.pages

import org.openqa.selenium.By

object ResultsOfSubmissionChecksPage extends BasePage {

  override val pageUrl: String = baseUrl + "/result-of-submission-checks"

  val gotoConfirmation: By = By.id("go-to-confirmation")
  val checkErrors: By      = By.id("check-errors")
  val checkProblem: By     = By.id("check-problem")
  val uploadFileAgain: By  = By.id("upload-file-again")
  val contactUs: By        = By.id("contact-us")

  def clickGotoConfirmation(): this.type = {
    click(gotoConfirmation)
    this
  }

  def clickCheckErrors(): this.type = {
    click(checkErrors)
    this
  }

  def clickCheckProblem(): this.type = {
    click(checkProblem)
    this
  }

  def clickContactUs(): this.type = {
    click(contactUs)
    this
  }
}
