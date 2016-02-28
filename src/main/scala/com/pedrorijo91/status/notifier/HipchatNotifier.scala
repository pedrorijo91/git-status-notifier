package com.pedrorijo91.status.notifier

import com.imadethatcow.hipchat.common.enums.Color.Color
import com.imadethatcow.hipchat.common.enums.{Color, MessageFormat}
import com.imadethatcow.hipchat.rooms.RoomNotifier
import com.pedrorijo91.status.Config
import com.pedrorijo91.status.model.{BitbucketStatus, GitHubStatus}
import play.api.Logger
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.{global => iGlobal}

object HipchatNotifier extends INotifier {

  private val logger = Logger(this.getClass)

  val name = this.getClass.getCanonicalName

  def isEnabled: Boolean = Config.hipchatEnabled

  protected def sendNotification(prevStatus: GitHubStatus, status: GitHubStatus): Unit = {
    val message = s"GitHub status changed\nCurrent status: ${status.status} (${status.datetime})\nPrevious status: ${prevStatus.status} (${prevStatus.datetime})"
    val color = calculateColor(status)

    send(message, color)
  }

  protected def sendNotification(prevStatus: BitbucketStatus, status: BitbucketStatus): Unit = {
    val message = s"Bitbucket status changed\nCurrent status: ${status.status} (${status.datetime})\nPrevious status: ${prevStatus.status} (${prevStatus.datetime})"
    val color = calculateColor(status)

    send(message, color)
  }

  private def send(message: String, color: Color) = {
    val maybeToken = Config.hipchatToken
    val maybeRoom = Config.hipchatRoom

    maybeToken.flatMap {
      token =>
        maybeRoom.map {
          room =>

            val roomNotifier = new RoomNotifier(token)

            val response = Await.result(roomNotifier.sendNotification(room, message, color, notify = true, messageFormat = MessageFormat.text), Config.requestTimeout)
            logger.debug(s"Hipchat response: $response")
            response
        }
    }.getOrElse {
      logger.error("Hipchat configurations missing")
    }
  }

  private def calculateColor(status: GitHubStatus): Color.Value = {
    status.status match {
      case GitHubStatus.ApiStatus.Good => Color.green
      case GitHubStatus.ApiStatus.Minor => Color.yellow
      case GitHubStatus.ApiStatus.Major => Color.red
      case GitHubStatus.ApiStatus.Unknown => Color.gray
    }
  }

  private def calculateColor(status: BitbucketStatus): Color.Value = {
    status.status match {
      case BitbucketStatus.ApiStatus.None => Color.green
      case BitbucketStatus.ApiStatus.Minor => Color.yellow
      case BitbucketStatus.ApiStatus.Major => Color.red
      case BitbucketStatus.ApiStatus.Critical => Color.red
      case BitbucketStatus.ApiStatus.Unknown => Color.gray
    }
  }
}
