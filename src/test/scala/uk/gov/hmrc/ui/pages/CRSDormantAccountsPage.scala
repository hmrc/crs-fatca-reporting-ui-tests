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

object CRSDormantAccountsPage extends BasePage {

  override val pageUrl: String = baseUrl + "elections/crs/dormant-accounts"
  val reportableAccountForCrsYesId: By = By.id("value")
  val reportableAccountForCrsNoId: By  = By.id("value-no")
  
  def checkPage(): Unit =
    onPage(pageUrl)
    
  def reportableAccountsForCrsYes(): Unit = {
    onPage(pageUrl)
    click(reportableAccountForCrsYesId)
    click(submitButtonId)
  }
  
  def reportableAccountsForCrsNo(): Unit = {
    onPage(pageUrl)
    click(reportableAccountForCrsNoId)
    click(submitButtonId)
  }
}
