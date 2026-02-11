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

import org.mongodb.scala.bson.*
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.model.{Filters, Updates}
import org.mongodb.scala.model.Indexes.{compoundIndex, descending}
import org.mongodb.scala.{MongoClient, MongoCollection, Observable}

import scala.concurrent.Await
import scala.concurrent.duration.*
import java.util.concurrent.TimeUnit
import scala.language.postfixOps

object MongoService {

  private val timeout: FiniteDuration = 10.seconds

  def dropMongoCollection(dbName: String, collectionName: String): Unit = {
    val mongoClient: MongoClient = MongoClient()
    try
      Await.result(
        mongoClient
          .getDatabase(dbName)
          .getCollection(collectionName)
          .drop()
          .head(),
        timeout
      )
    finally
      mongoClient.close()
  }

  def getCollectionData(dbName: String, collectionName: String): Document = {
    val mongoClient                           = MongoClient()
    val collection: MongoCollection[Document] = mongoClient
      .getDatabase(dbName)
      .getCollection(collectionName)

    val observable: Observable[Document] = collection.find().sort(descending("lastUpdated"))
    def document(): Document             = Await.result(observable.head(), Duration(10, TimeUnit.SECONDS))

    val retrievedRecord = document()
    mongoClient.close()
    retrievedRecord
  }

  def setField(dbName: String, collection: String, id: String, fieldPath: String, value: Any): Unit = {
    val mongoClient: MongoClient = MongoClient()

    val query = Filters.eq("_id", id)

    val bsonValue: BsonValue = value match {
      case b: Boolean    => BsonBoolean(b)
      case s: String     => BsonString(s)
      case i: Int        => BsonInt32(i)
      case l: Long       => BsonInt64(l)
      case d: Double     => BsonDouble(d)
      case bd: BsonValue => bd
      case other         => BsonString(other.toString)
    }

    val update = Updates.set(fieldPath, bsonValue)

    Await.result(
      mongoClient
        .getDatabase(dbName)
        .getCollection(collection)
        .updateOne(query, update)
        .head(),
      timeout
    )

    mongoClient.close()
  }

  def setFileStatus(dbName: String, collection: String, id: String, status: String) = {

    val mongoClient: MongoClient = MongoClient()
    val query                    = Filters.eq("_id", id)
    val updateStatus             = Updates.set("status", BsonDocument(status))

    Await.result(
      mongoClient
        .getDatabase(dbName)
        .getCollection(collection)
        .updateOne(query, updateStatus)
        .head(),
      2 seconds
    )
    mongoClient.close()
  }
}
