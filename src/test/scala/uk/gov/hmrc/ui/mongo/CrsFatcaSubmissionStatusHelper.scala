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

package uk.gov.hmrc.ui.mongo

import uk.gov.hmrc.ui.mongo

object CrsFatcaSubmissionStatusHelper {

  private val database   = "crs-fatca-reporting-frontend"
  private val collection = "user-answers"

  private def journeyId: String =
    MongoService
      .getCollectionData(database, collection)("_id")
      .asString()
      .getValue

  def setGiinSent(sent: Boolean): Unit =
    MongoService.setField(database, collection, journeyId, "data.giinAndElectionStatus.giinStatus", sent)

  def setElectionsSent(sent: Boolean): Unit =
    MongoService.setField(database, collection, journeyId, "data.giinAndElectionStatus.electionStatus", sent)

  def setRequiresElections(required: Boolean): Unit =
    MongoService.setField(database, collection, journeyId, "data.requiresElections", required)

  /**
   * presets matching journey-map variants
   */
  def giinFailedElectionsSent(): Unit = {
    setGiinSent(false)
    setRequiresElections(true)
    setElectionsSent(true)
  }

  def giinFailedNoElectionsSent(): Unit = {
    setGiinSent(false)
    setRequiresElections(false) 
    setElectionsSent(false)     
  }

  def giinFailedElectionsFailed(): Unit = {
    setGiinSent(false)
    setRequiresElections(true)
    setElectionsSent(false)
  }
}
