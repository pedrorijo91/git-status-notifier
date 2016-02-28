package com.pedrorijo91.status.model

import com.pedrorijo91.status.Config
import com.pedrorijo91.status.model.GitHubStatus.ApiStatus
import org.joda.time.DateTime
import play.api.Logger
import play.api.libs.functional.syntax._
import play.api.libs.json._
import scala.util.Try

case class GitHubStatus(status: ApiStatus.Value, lastUpdated: DateTime) extends HostStatus {
  def datetime: String = lastUpdated.toString(Config.dateTimeFormat)
}

object GitHubStatus {

  private val logger = Logger(this.getClass)

  implicit val read: Reads[GitHubStatus] = (
    (JsPath \ "status").read[String].map(ApiStatus.findByName) and
      (JsPath \ "last_updated").read[String].map(new DateTime(_))
    ) (GitHubStatus.apply _)

  object ApiStatus extends Enumeration {
    val Good, Minor, Major, Unknown = Value

    def findByName(name: String): ApiStatus.Value = {
      Try(ApiStatus.withName(name.toLowerCase.capitalize)).getOrElse {
        logger.warn(s"Found unexpected Github API status: $name")
        Unknown
      }
    }
  }

}
