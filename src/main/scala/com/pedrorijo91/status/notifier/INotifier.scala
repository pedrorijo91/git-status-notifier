package com.pedrorijo91.status.notifier

import com.pedrorijo91.status.model.{BitbucketStatus, GitHubStatus}
import play.api.Logger

trait INotifier {

  private val logger = Logger(this.getClass)

  val name: String

  def notify(prevStatus: GitHubStatus, status: GitHubStatus): Unit = {
    if (isEnabled) {
      sendNotification(prevStatus, status)
    } else {
      logger.debug(s"Notifier not enabled: $name")
    }
  }

  def notify(prevStatus: BitbucketStatus, status: BitbucketStatus): Unit = {
    if (isEnabled) {
      sendNotification(prevStatus, status)
    } else {
      logger.debug(s"Notifier not enabled: $name")
    }
  }

  def isEnabled: Boolean

  protected def sendNotification(prevStatus: GitHubStatus, status: GitHubStatus): Unit

  protected def sendNotification(prevStatus: BitbucketStatus, status: BitbucketStatus): Unit

}
