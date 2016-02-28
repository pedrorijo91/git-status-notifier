package com.pedrorijo91.status.actor

import com.pedrorijo91.status.Config
import com.pedrorijo91.status.model.GitHubStatus
import com.pedrorijo91.status.notifier.NotificationCenter
import play.api.data.validation.ValidationError
import play.api.libs.json.JsPath
import play.api.libs.ws.WSResponse
import scala.concurrent.ExecutionContext.Implicits.global

class GitHubVerifierActor extends StatusActor[GitHubStatus] {

  private val actorMessage = "github"

  val tick = context.system.scheduler.schedule(Config.initialDelay, Config.checkInterval, self, actorMessage)

  // docs: https://status.github.com/api
  private val githubURL = "https://status.github.com/api/status.json"

  def receive = {
    case `actorMessage` =>
      check(githubURL)
  }

  protected def validate(response: WSResponse): Either[Seq[(JsPath, Seq[ValidationError])], GitHubStatus] = {
    response.json.validate[GitHubStatus].asEither
  }

  protected def hasChanged(prev: GitHubStatus, current: GitHubStatus): Boolean = {
    prev.lastUpdated != current.lastUpdated && prev.status != current.status
  }

  protected def onChange(prev: GitHubStatus, current: GitHubStatus): Unit = {
    NotificationCenter.sendNotification(prev, current)
  }
}