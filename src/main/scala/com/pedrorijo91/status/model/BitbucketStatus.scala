package com.pedrorijo91.status.model

import com.pedrorijo91.status.Config
import com.pedrorijo91.status.model.BitbucketStatus.ApiStatus
import org.joda.time.DateTime
import play.api.Logger
import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, _}
import scala.util.Try

case class BitbucketStatus(updatedAt: DateTime, status: ApiStatus.Value, description: String) extends HostStatus {
  def datetime: String = updatedAt.toString(Config.dateTimeFormat)
}

object BitbucketStatus {

  private val logger = Logger(this.getClass)

  implicit val read: Reads[BitbucketStatus] = (
    (JsPath \ "page" \ "updated_at").read[String].map(new DateTime(_)) and
      (JsPath \ "status" \ "indicator").read[String].map(ApiStatus.findByName) and
      (JsPath \ "status" \ "description").read[String]
    ) (BitbucketStatus.apply _)

  object ApiStatus extends Enumeration {
    val None, Minor, Major, Critical, Unknown = Value

    def findByName(name: String): ApiStatus.Value = {
      Try(ApiStatus.withName(name.toLowerCase.capitalize)).getOrElse {
        logger.warn(s"Found unexpected Bitbucket API status: $name")
        Unknown
      }
    }
  }

}
