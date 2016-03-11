# Git host status notifier

[![Build Status](https://travis-ci.org/pedrorijo91/git-status-notifier.svg?branch=master)](https://travis-ci.org/pedrorijo91/git-status-notifier) [![Codacy Badge](https://api.codacy.com/project/badge/grade/f233e9027e244deca43349164d733e8f)](https://www.codacy.com/app/pedrorijo91/git-status-notifier)
---

### Git host status notifier

CLI application written in Scala to notify when your favorite Git host is down.

At this moment it supports:

* [Github](https://status.github.com/)
* [Bitbucket](http://status.bitbucket.org/)
* [Gitlab](https://status.gitlab.com/) (roadmap)

Notifications are sent trough:

* [Slack](https://slack.com/)
* [Hipchat](http://hipchat.com/)
* Email (with [sendgrid](https://sendgrid.com/))
* SMS (with [Twillio](https://www.twilio.com/)) (roadmap)

### Usage

Set your configurations and type:

`sbt run`

#### Configurations

All configurations are defined in `src/main/resources/application.conf`

Some settings for the check are available:

* request timeout
* time between checks
* date time format (message display)

Each notification channel must be enabled (by default are disabled).
Besides that, each notification channel requires some settings (token, room, etc)

##### Slack
Slack requires to:

* define the token
* define the channel (optional: by default posts to `#general`)
* invite bot to channel

More info at [slack docs](https://my.slack.com/services/new/bot)

##### Hipchat
Hipchat requires to:

* define the token
* define the channel

To create the bot go to the room and look for the triple dot (upper left), click on `integrations`, and then `build your own`

#### Email - Sendgrid
To send emails Sendgrid is used.
Sendgrid requires to:

* define the token
* define the sender
* define the email receiver(s)

### TODO

* Gitlab API monitor
* SMS notifications, possibly using Twillio API

### Contributing

Check [CONTRIBUTING.md](CONTRIBUTING.md)

### Issue tracking

After checking already reported issues, report your issues to [Github issues](https://github.com/pedrorijo91/status-notifier/issues)
