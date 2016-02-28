package com.pedrorijo91.status.notifier

import com.pedrorijo91.status.Config
import com.pedrorijo91.status.model.{BitbucketStatus, GitHubStatus}
import com.sendgrid.SendGrid
import com.sendgrid.SendGrid.Email
import org.joda.time.DateTime
import play.api.Logger
;

object EmailNotifier extends INotifier {

  private val logger = Logger(this.getClass)

  val name = this.getClass.getCanonicalName

  def isEnabled: Boolean = Config.emailEnabled

  protected def sendNotification(prevStatus: GitHubStatus, status: GitHubStatus): Unit = {
    val subject = s"Github status changed ${new DateTime().toString("yyyy-MM-dd")}"
    val message = s"Github status changed\n\nCurrent status: ${status.status} (${status.datetime})\n\nPrevious status: ${prevStatus.status} (${prevStatus.datetime})"

    send(subject, message)
  }

  protected def sendNotification(prevStatus: BitbucketStatus, status: BitbucketStatus): Unit = {
    val subject = s"Bitbucket status changed ${new DateTime().toString("yyyy-MM-dd")}"
    val message = s"Bitbucket status changed\n\nCurrent status: ${status.status} (${status.datetime})\n\nPrevious status: ${prevStatus.status} (${prevStatus.datetime})"

    send(subject, message)
  }

  private def send(subject: String, message: String) = {
    val token = Config.sendgridToken

    token.fold {
      logger.error("Missing sendgrid token for email notification")
    } {
      tk =>
        val toEmails: Set[String] = Config.sendgridToEmails

        if(toEmails.isEmpty) {
          logger.error("Missing required email notification receiver(s)")
        } else {
          Config.sendgridFromEmail.fold {
            logger.error("Missing required email notification sender")
          } {
            fromEmail =>
              val email = new Email()

              email.setSubject(subject)
              email.setText(message)
              email.addTo(toEmails.toArray)
              email.setFrom(fromEmail)

              Config.sendgridToName.map(email.addToName)
              Config.sendgridFromName.map(email.setFromName)

              val sendgrid = new SendGrid(tk)
              val response = sendgrid.send(email)
              logger.debug(s"Sendgrid response: ${response.getCode} -> ${response.getStatus} - ${response.getMessage}")

              response.getCode match {
                case code if code >= 200 && code < 300 =>
                  logger.info(s"Success response code: $code. Status: ${response.getStatus} - Message: ${response.getMessage}")
                case code if code >= 400 && code < 500 =>
                  logger.error(s"Error response code: $code. Status: ${response.getStatus} - Message: ${response.getMessage}")
                case code if code >= 500 && code < 600 =>
                  logger.error(s"API call unsuccessful code: $code. Status: ${response.getStatus} - Message: ${response.getMessage}")
                case code =>
                  logger.warn(s"Unexpected response code: $code. Status: ${response.getStatus} - Message: ${response.getMessage}")
              }
          }
        }
    }
  }
}
