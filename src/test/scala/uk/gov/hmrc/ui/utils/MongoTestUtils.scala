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

package uk.gov.hmrc.ui.utils

import uk.gov.hmrc.ui.mongo.*

trait MongoTestUtils {

  val databaseName = "crs-fatca-reporting-frontend"
  val userAnswers  = "user-answers"

  def cleanUserAnswersCollection(): Unit =
    MongoService.dropMongoCollection(databaseName, userAnswers)

  def dropCollection(collectionName: String): Unit =
    MongoService.dropMongoCollection(databaseName, collectionName)
}
