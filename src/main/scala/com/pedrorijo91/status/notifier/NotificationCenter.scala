package com.pedrorijo91.status.notifier

import com.pedrorijo91.status.model.{BitbucketStatus, GitHubStatus}

object NotificationCenter {

  def sendNotification(prevStatus: GitHubStatus, status: GitHubStatus): Unit = {

    SlackNotifier.notify(prevStatus, status)

    HipchatNotifier.notify(prevStatus, status)

    EmailNotifier.notify(prevStatus, status)
  }

  def sendNotification(prevStatus: BitbucketStatus, status: BitbucketStatus): Unit = {

    SlackNotifier.notify(prevStatus, status)

    HipchatNotifier.notify(prevStatus, status)

    EmailNotifier.notify(prevStatus, status)
  }

}
