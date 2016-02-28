package com.pedrorijo91.status

import akka.actor.{ActorSystem, Props}
import com.pedrorijo91.status.actor.{BitbucketVerifierActor, GitHubVerifierActor}

object Daemon {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("status-notifier")

    if (Config.githubEnabled) {
      val gitHubActor = system.actorOf(Props[GitHubVerifierActor], "gitHubActor")
    }

    if (Config.bitbucketEnabled) {
      val bitbucketActor = system.actorOf(Props[BitbucketVerifierActor], "bitbucketActor")
    }

  }
}
