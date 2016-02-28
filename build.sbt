name := """status-notifier"""

version := "1.0"

scalaVersion := "2.11.7"

resolvers += Resolver.jcenterRepo
resolvers += Resolver.typesafeRepo("releases")

libraryDependencies ++= Seq(
  Dependencies.scalatest,
  Dependencies.playWS,
  Dependencies.slack,
  Dependencies.hipchat,
  Dependencies.sendgridJ,
  Dependencies.config
)


