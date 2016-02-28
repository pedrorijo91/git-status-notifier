package com.pedrorijo91.status.notifier

import com.pedrorijo91.status.Config
import com.pedrorijo91.status.model.{BitbucketStatus, GitHubStatus}
import play.api.Logger
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.implicitConversions
import slack.api.BlockingSlackApiClient
import slack.models.Attachment

object SlackNotifier extends INotifier {

  private val logger = Logger(this.getClass)

  val name = this.getClass.getCanonicalName

  def isEnabled: Boolean = Config.slackEnabled

  protected def sendNotification(prevStatus: GitHubStatus, status: GitHubStatus): Unit = {

    val message = s"Github API status changed"
    val attachText = s"Current status: ${status.status} (${status.datetime})\nPrevious status: ${prevStatus.status} (${prevStatus.datetime})"
    val attachment = Attachment(color = calculateColor(status), text = Some(attachText))

    send(message, attachText, attachment)
  }

  protected def sendNotification(prevStatus: BitbucketStatus, status: BitbucketStatus): Unit = {

    val message = s"Bitbucket API status changed"
    val attachText = s"Current status: ${status.status} (${status.datetime})\nPrevious status: ${prevStatus.status} (${prevStatus.datetime})"
    val attachment = Attachment(color = calculateColor(status), text = Some(attachText))

    send(message, attachText, attachment)
  }

  private def send(message: String, attachText: String, attachment: Attachment) = {
    val token = Config.slackToken
    val roomId = Config.slackRoom

    token.fold {
      logger.error("Missing slack auth token")
    } {
      tk =>
        val slackClient = BlockingSlackApiClient(tk)
        val response = slackClient.postChatMessage(roomId, message, attachments = Some(Seq(attachment)))
        logger.debug(s"Slack response: $response")
    }
  }

  private def calculateColor(status: GitHubStatus): Option[String] = {
    status.status match {
      case GitHubStatus.ApiStatus.Good => Some(SlackColor.Green)
      case GitHubStatus.ApiStatus.Minor => Some(SlackColor.Yellow)
      case GitHubStatus.ApiStatus.Major => Some(SlackColor.Red)
      case GitHubStatus.ApiStatus.Unknown => None
    }
  }

  private def calculateColor(status: BitbucketStatus): Option[String] = {
    status.status match {
      case BitbucketStatus.ApiStatus.None => Some(SlackColor.Green)
      case BitbucketStatus.ApiStatus.Minor => Some(SlackColor.Yellow)
      case BitbucketStatus.ApiStatus.Major => Some(SlackColor.Red)
      case BitbucketStatus.ApiStatus.Critical => Some(SlackColor.Red)
      case BitbucketStatus.ApiStatus.Unknown => None
    }
  }

  private object SlackColor extends Enumeration {
    val Green = Value("good")
    val Yellow = Value("warning")
    val Red = Value("danger")

    implicit def convToString(slackColor: SlackColor.Value): String = slackColor.toString
  }

}


