/*
 * Copyright 2023 HM Revenue & Customs
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

import org.openqa.selenium.{By, WebDriver}
import org.openqa.selenium.support.ui.{ExpectedConditions, FluentWait, Wait}
import org.scalatest.Assertion
import org.scalatest.matchers.should.Matchers
import uk.gov.hmrc.selenium.component.PageObject
import uk.gov.hmrc.selenium.webdriver.Driver
import uk.gov.hmrc.ui.conf.TestConfiguration
import uk.gov.hmrc.ui.utils.IdGenerators
import java.time.Duration

trait BasePage extends Matchers with IdGenerators with PageObject {

  case class PageNotFoundException(message: String) extends Exception(message)

  val pageUrl: String
  val baseUrl: String            = TestConfiguration.url("crs-fatca-reporting-frontend") + ""
  private val submitButtonId: By = By.id("submit")
  private val backLinkText: By   = By.linkText("Back")
  private val pageHeader: By     = By.tagName("h1")

  private def fluentWait: Wait[WebDriver] = new FluentWait[WebDriver](Driver.instance)
    .withTimeout(Duration.ofSeconds(2))
    .pollingEvery(Duration.ofMillis(200))

  def onPage(url: String = this.pageUrl): Unit = fluentWait.until(ExpectedConditions.urlToBe(url))

  def clickOnBackLink(): Unit = {
    onPage()
    click(backLinkText)
  }

  def submitOnPageById(): Unit = {
    onPage()
    click(submitButtonId)
  }

  def checkH1(h1: String): Assertion =
    getText(pageHeader) should include(h1)

}
