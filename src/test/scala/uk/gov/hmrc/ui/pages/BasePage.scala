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

import org.openqa.selenium.By
import org.openqa.selenium.support.ui.Select
import org.scalatest.Assertion
import org.scalatest.matchers.should.Matchers
import uk.gov.hmrc.ui.conf.TestConfiguration
import uk.gov.hmrc.ui.utils.IdGenerators
import uk.gov.hmrc.ui.driver.BrowserDriver

trait BasePage extends BrowserDriver with Matchers with IdGenerators {

  case class PageNotFoundException(message: String) extends Exception(message)

  val pageUrl: String
  val baseUrl: String            = TestConfiguration.url("crs-fatca-reporting-frontend") + ""
  private val submitButtonId: By = By.id("submit")
  private val pageHeader: By     = By.tagName("h1")

  def navigateTo(url: String): Unit =
    driver.navigate().to(url)

  def onPage(url: String = this.pageUrl): Unit =
    if (driver.getCurrentUrl != url)
      throw PageNotFoundException(s"Expected '$url' page, but found '${driver.getCurrentUrl}' page.")

  def sendTextById(id: By, textToEnter: String): Unit = {
    driver.findElement(id).clear()
    driver.findElement(id).sendKeys(textToEnter)
  }

  def selectDropdownById(id: By): Select = new Select(driver.findElement(id: By))

  def clickOnById(id: By): Unit =
    driver.findElement(id).click()

  def submitPageById(): Unit =
    driver.findElement(submitButtonId).click()

  def submitOnPageById(): Unit = {
    onPage()
    driver.findElement(submitButtonId).click()
  }

  def checkH1(h1: String): Assertion =
    driver.findElement(pageHeader).getText should include(h1)

}
