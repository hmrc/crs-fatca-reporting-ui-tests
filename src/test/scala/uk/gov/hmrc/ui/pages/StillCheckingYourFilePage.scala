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
import org.scalatest.concurrent.Eventually.*
import org.scalatest.concurrent.Eventually.PatienceConfig
import org.scalatest.time.{Minutes, Seconds, Span}
import org.scalatest.matchers.should.Matchers._

object StillCheckingYourFilePage extends BasePage {

  override val pageUrl: String = baseUrl + "/still-checking-your-file"
  private val statusSelector =
    By.xpath("//dt[text()='Result of automatic checks']/following-sibling::dd//strong")

  def refreshForUpdates(): this.type = {
    onPage(pageUrl)
    click(submitButtonId)
    this
  }


  def getStatus: String =
    driver.findElement(statusSelector).getText.trim

  def waitUntilFileProcessed(): String = {

    implicit val patienceConfig: PatienceConfig =
      PatienceConfig(timeout = Span(2, Minutes), interval = Span(2, Seconds))

    var finalStatus = ""

    eventually {
      refreshForUpdates()
      finalStatus = getStatus
      finalStatus should not be "Pending"
    }

    finalStatus
  }
}