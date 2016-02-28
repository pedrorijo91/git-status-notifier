package com.pedrorijo91.status.actor

import akka.actor.Actor
import com.pedrorijo91.status.model.HostStatus
import com.pedrorijo91.status.{ClientHelper, Config}
import play.api.Logger
import play.api.data.validation.ValidationError
import play.api.libs.json.JsPath
import play.api.libs.ws.WSResponse
import scala.concurrent.Await
import scala.util.{Failure, Success}

trait StatusActor[T <: HostStatus] extends Actor {

  private val logger = Logger(this.getClass)

  private val requestTimeout = Config.requestTimeout

  protected var lastStatusOpt: Option[T] = None

  protected def validate(response: WSResponse): Either[Seq[(JsPath, Seq[ValidationError])], T]

  protected def hasChanged(prev: T, current: T): Boolean

  protected def onChange(prev: T, current: T): Any

  def check(url: String) = {

    val result = ClientHelper.withClient {
      client =>
        val promise = client.url(url).get()
        Await.result(promise, requestTimeout)
    }

    result match {
      case Failure(e) =>
        logger.error(s"Failure executing request: $e")
        println(s"Failure executing request: $e")

      case Success(response) =>
        logger.debug(s"Success response: (${response.status}) ${response.json}")
        val statusResponse = validate(response)

        statusResponse match {
          case Left(e) =>
            logger.error(s"Error parsing json answer: $e")

          case Right(status) =>
            logger.debug(s"Parsed json answer: $status")
            lastStatusOpt.foreach {
              lastStatus =>
                if(hasChanged(lastStatus, status)) {
                  logger.info(s"Status has changed: $lastStatus vs $status")
                  onChange(lastStatus, status)
                } else {
                  logger.info(s"Status has not changed: $lastStatus vs $status")
                }
            }
            lastStatusOpt = Some(status)
        }
    }
  }
}