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

package uk.gov.hmrc.ui.pages

import org.openqa.selenium.By
import uk.gov.hmrc.selenium.webdriver.Driver

object ProblemElectionNotSentPage extends BasePage {

  override val pageUrl: String = baseUrl + "/problem/elections-not-sent"

  private val headingSelector     = By.cssSelector("#main-content h1.govuk-heading-l")
  private val bodySelector        = By.cssSelector("#main-content p.govuk-body")
  private val backLinkSelector    = By.cssSelector(".govuk-back-link")
  private val warningTextSelector = By.cssSelector("#main-content .govuk-warning-text")

  val expectedWarningText: String =
    "You must finish sending your file to complete the reporting process."

  val commonBodyPhrase: String =
    "You can still send your file without the elections, then add them after in the service."

  val giinReceivedElectionsNotReceivedPhrase: String =
    "We have received the GIIN for this financial institution, but have not been able to receive the elections."

  def heading: String =
    Driver.instance.findElement(headingSelector).getText.trim

  def bodyText: String =
    Driver.instance.findElement(bodySelector).getText.trim

  def backLinkIsPresent: Boolean =
    !Driver.instance.findElements(backLinkSelector).isEmpty

  def warningTextPresent: Boolean =
    !Driver.instance.findElements(warningTextSelector).isEmpty

  def warningText: String =
    Driver.instance.findElement(warningTextSelector).getText.trim

  def finishSendingFile(): this.type = {
    submitPage()
    waitForSpinnerCycle(30)
    this
  }

  def assertPageIsDisplayed(): Unit = {
    onPage()
    heading            shouldBe "There is a problem with sending the elections"
    backLinkIsPresent  shouldBe false
    warningTextPresent shouldBe true
    warningText          should include(expectedWarningText)
  }

  def assertElectionNotSentVariant(): Unit = {
    assertPageIsDisplayed()
    bodyText should include(commonBodyPhrase)
  }

  def assertGiinReceivedElectionsNotSentVariant(): Unit = {
    assertPageIsDisplayed()
    bodyText should include(giinReceivedElectionsNotReceivedPhrase)
  }

}
