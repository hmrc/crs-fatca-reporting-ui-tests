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
import uk.gov.hmrc.selenium.webdriver.Driver

object ProblemGiinNotSentPage extends BasePage {

  override val pageUrl: String = baseUrl + "/problem/giin-not-sent"

  private val headingSelector     = By.cssSelector("#main-content h1.govuk-heading-l")
  private val bodySelector        = By.cssSelector("#main-content p.govuk-body")
  private val backLinkSelector    = By.cssSelector(".govuk-back-link")
  private val warningTextSelector = By.cssSelector("#main-content .govuk-warning-text")

  val electionsSentPhrase: String =
    "We have received the elections for this financial institution, but have not been able to receive the GIIN. We need the GIIN to receive your file."

  val electionsNotProvidedPhrase: String =
    "We have not been able to receive the GIIN for this financial institution. We need the GIIN to receive your file."

  val electionsFailedTooPhrase: String =
    "We have not been able to receive the GIIN or elections for this financial institution. We need the GIIN to receive your file."

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

  def assertPageIsDisplayed(): Unit = {
    onPage()
    heading            shouldBe "We’re unable to receive your file"
    backLinkIsPresent  shouldBe false
    warningTextPresent shouldBe true
    warningText          should include("You must send your file later to complete the reporting process.")
  }

  def assertElectionsSentVariant(): Unit = {
    assertPageIsDisplayed()
    bodyText should include(electionsSentPhrase)
  }

  def assertElectionsNotProvidedVariant(): Unit = {
    assertPageIsDisplayed()
    bodyText should include(electionsNotProvidedPhrase)
  }

  def assertElectionsFailedTooVariant(): Unit = {
    assertPageIsDisplayed()
    bodyText should include(electionsFailedTooPhrase)
  }

}
