package com.pedrorijo91.status

import com.typesafe.config.ConfigFactory
import net.ceedubs.ficus.Ficus._
import scala.concurrent.duration._

object Config {

  private val config = ConfigFactory.load()

  private def getBoolean(path: String): Option[Boolean] = config.as[Option[Boolean]](path)

  private def getInt(path: String): Option[Int] = config.as[Option[Int]](path)

  private def getString(path: String): Option[String] = config.as[Option[String]](path)

  private def getSet(path: String): Option[Set[String]] = config.as[Option[Set[String]]](path)

  // general
  def requestTimeout: FiniteDuration = getInt("request.timeout.seconds").getOrElse(10) second

  def initialDelay: FiniteDuration = getInt("check.initial.delay.millis").getOrElse(500) millis

  def checkInterval: FiniteDuration = getInt("check.interval.seconds").getOrElse(60) second

  def dateTimeFormat: String = getString("datetime.print.format").getOrElse("yyyy/MM/dd HH:mm:ss")

  // hosts
  def githubEnabled: Boolean = getBoolean("check.github.enabled").getOrElse(false)

  def bitbucketEnabled: Boolean = getBoolean("check.bitbucket.enabled").getOrElse(false)

  // slack
  def slackEnabled: Boolean = getBoolean("notification.slack.enabled").getOrElse(false)

  def slackToken: Option[String] = getString("notification.slack.token")

  def slackRoom: String = getString("notification.slack.room").getOrElse("#general")

  // hipchat
  def hipchatEnabled: Boolean = getBoolean("notification.hipchat.enabled").getOrElse(false)

  def hipchatToken: Option[String] = getString("notification.hipchat.token")

  def hipchatRoom: Option[String] = getString("notification.hipchat.room")

  // email
  def emailEnabled: Boolean = getBoolean("notification.email.enabled").getOrElse(false)

  def sendgridToken: Option[String] = getString("notification.sendgrid.token")

  def sendgridToEmails: Set[String] = getSet("notification.sendgrid.to.emails").getOrElse(Set.empty[String])

  def sendgridToName: Option[String] = getString("notification.sendgrid.to.name")

  def sendgridFromEmail: Option[String] = getString("notification.sendgrid.from.email")

  def sendgridFromName: Option[String] = getString("notification.sendgrid.from.name")
}
