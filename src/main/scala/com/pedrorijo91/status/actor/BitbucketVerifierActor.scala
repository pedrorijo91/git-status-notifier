package com.pedrorijo91.status.actor

import com.pedrorijo91.status.Config
import com.pedrorijo91.status.model.BitbucketStatus
import com.pedrorijo91.status.notifier.NotificationCenter
import play.api.data.validation.ValidationError
import play.api.libs.json.JsPath
import play.api.libs.ws.WSResponse
import scala.concurrent.ExecutionContext.Implicits.global

class BitbucketVerifierActor extends StatusActor[BitbucketStatus] {

  private val actorMessage = "bitbucket"

  val tick = context.system.scheduler.schedule(Config.initialDelay, Config.checkInterval, self, actorMessage)

  // docs: http://status.bitbucket.org/api
  private val bitbucketUrl = "http://bqlf8qjztdtr.statuspage.io/api/v2/status.json"

  def receive = {
    case `actorMessage` =>
      check(bitbucketUrl)
  }

  protected def validate(response: WSResponse): Either[Seq[(JsPath, Seq[ValidationError])], BitbucketStatus] = {
    response.json.validate[BitbucketStatus].asEither
  }

  protected def hasChanged(prev: BitbucketStatus, current: BitbucketStatus): Boolean = {
    prev.updatedAt != current.updatedAt && prev.status != current.status
  }

  protected def onChange(prev: BitbucketStatus, current: BitbucketStatus): Unit = {
    NotificationCenter.sendNotification(prev, current)
  }
}