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
import uk.gov.hmrc.ui.conf.{Enrolment, TestConfiguration}

object AuthLoginPage extends BasePage {

  override val pageUrl: String = TestConfiguration.url("auth-login-stub") + "/gg-sign-in"

  private val redirectUrl: String = TestConfiguration.url("crs-fatca-reporting-frontend")

  private val redirectionUrlById: By = By.id("redirectionUrl")
  private val affinityGroupById: By  = By.id("affinityGroupSelect")
  private val authSubmitById: By     = By.id("submit-top")

  private val enrolment: Enrolment             = TestConfiguration.enrolmentConfig.individual
  private val autoMatchedEnrolment: Enrolment  = TestConfiguration.enrolmentConfig.autoMatchedUser
  private val organisationEnrolment: Enrolment = TestConfiguration.enrolmentConfig.organisationUser
  private val enrolmentKeyById: By             = By.id("enrolment[0].name")
  private val enrolmentIdentifierById: By      = By.id("input-0-0-name")
  private val enrolmentValueById: By           = By.id("input-0-0-value")

  private val presetDropDownById: By    = By.id("presets-dropdown")
  private val presetSubmitById: By      = By.id("add-preset")
  private val identifierCTField: By     = By.id("input-4-0-value")
  private val identifierCTValue: String = generateUtr(validCtUtr)

  def loadPage(): this.type = {
    get(pageUrl)
    onPage(pageUrl)
    this
  }

//  def selectAffinityGroup(affinityGroup: String): Unit =
//    selectDropdownById(affinityGroupById).selectByVisibleText(affinityGroup)

  private def addCtPreset(): Unit = {
    selectByVisibleText(presetDropDownById, "CT")
//    selectDropdownById(presetDropDownById).selectByVisibleText("CT")
    click(presetSubmitById)
    sendKeys(identifierCTField, identifierCTValue)
  }

  def loginAsBasic(): this.type = {
    loadPage()
    sendKeys(redirectionUrlById, redirectUrl)
    sendKeys(enrolmentKeyById, enrolment.key)
    sendKeys(enrolmentIdentifierById, enrolment.identifier)
    sendKeys(enrolmentValueById, enrolment.value)
    click(authSubmitById)
    this
  }

  def loginAsOrganisationUser(): this.type = {
    loadPage()
    sendKeys(redirectionUrlById, redirectUrl)
    selectByVisibleText(affinityGroupById, "Organisation")
//    selectAffinityGroup("Organisation")
    sendKeys(enrolmentKeyById, organisationEnrolment.key)
    sendKeys(enrolmentIdentifierById, organisationEnrolment.identifier)
    sendKeys(enrolmentValueById, organisationEnrolment.value)
    addCtPreset()
    click(authSubmitById)
    this
  }

  def loginAsAutoMatchedUser(): this.type = {
    loadPage()
    sendKeys(redirectionUrlById, redirectUrl)
    selectByVisibleText(affinityGroupById, "Organisation")
//    selectAffinityGroup("Organisation")
    sendKeys(enrolmentKeyById, autoMatchedEnrolment.key)
    sendKeys(enrolmentIdentifierById, autoMatchedEnrolment.identifier)
    sendKeys(enrolmentValueById, autoMatchedEnrolment.value)
    addCtPreset()
    click(authSubmitById)
    this
  }
}
