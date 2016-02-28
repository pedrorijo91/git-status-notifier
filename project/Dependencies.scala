import sbt._

object Dependencies {

  // Play framework
  lazy val playWS = "com.typesafe.play" %% "play-ws" % "2.3.9"
  lazy val scalatest = "org.scalatest" %% "scalatest" % "2.2.5" % "test"
  lazy val slack = "com.github.gilbertw1" %% "slack-scala-client" % "0.1.3" // 0.1.4 http://mvnrepository.com/artifact/com.github.gilbertw1/slack-scala-client_2.11
  lazy val hipchat = "com.imadethatcow" %% "hipchat-scala" % "0.2" // 1.0
  lazy val config = "com.iheart" %% "ficus" % "1.2.0"
  lazy val sendgridJ =  "com.sendgrid" % "sendgrid-java" % "2.2.2"
}
