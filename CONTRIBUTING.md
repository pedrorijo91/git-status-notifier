# How to contribute to status-notifier
---

:+1::tada: First off, thanks for taking the time to contribute! :tada::+1:

## How Can I Contribute?

### Reporting Bugs

This section guides you through submitting a bug report for status-notifier. Following these guidelines helps maintainers and the community understand your report, reproduce the behavior, and find related reports.

Bugs are tracked as [GitHub issues](https://github.com/pedrorijo91/status-notifier/issues). Create issues providing enough information to reproduce the error. Keep in mind that some problems you face can be due to API behavior.

* **Make sure there is no other [issue](https://github.com/pedrorijo91/status-notifier/issues)/[pull request](https://github.com/pedrorijo91/status-notifier/pulls) addressing such problem**.
* **Use a clear and descriptive title** for the issue to identify the problem.
* **Describe the exact steps which reproduce the problem** in as many details as possible.
* **Provide specific examples to demonstrate the steps**. Include links to files or GitHub projects, or copy/pasteable snippets, which you use in those examples. If you're providing snippets in the issue, use [Markdown code blocks](https://help.github.com/articles/markdown-basics/#multiple-lines).
* **Describe the behavior you observed after following the steps** and point out what exactly is the problem with that behavior. Possibly, include the stacktrace also.
* **Explain which behavior you expected to see instead and why.**

Include details about your configuration and environment:

* **Which version of Scala/sbt are you using?**
* **What's the name and version of the OS you're using**?
* **Do you have any other project dependency that can be conflicting with status-notifier?**.

### Creating a Pull Requests

Pull Requests are welcome from everyone
* **Create an issue before** creating a Pull Request and inform you are working on it, so that others know someone is working on that, and allowing to discuss the solution before starting to code.
* **Include tests**. If you are fixing some bug, it's because the current test set is incomplete or erroneous. Add/fix the respective test(s).
* **Solve conflicts**. To be approved and merged, the Pull Request must contain no conflicts.
* **Rebase, not merge**. Use `git rebase` (not `git merge`) to sync your work from time to time with the master branch.
* **Reference the issue** the Pull Request refers to.
* **Respect [commit message rules](#git-commit-messages)** for each commit included in the Pull Request
* **The Pull Request should get green status**. After creating your pull request make sure the build is passing on current [CI](https://travis-ci.org/pedrorijo91/status-notifier)
and that [Codacy](https://www.codacy.com/app/pedrorijo91/status-notifier) is also confident in the code quality.
* **[Optional] Use one of the [icons](#using-emoticons) to identify the type of PR**. Usage of visual clues provides a fast way to understand the goal of the Pull Request.

### Git Commit Messages

A good commit message is essential to easily understand its purpose. Among many available articles, you may check [this one](http://tbaggery.com/2008/04/19/a-note-about-git-commit-messages.html).
Here is a list of important rules about commit messages:

* Use the present tense ("Add feature" not "Added feature")
* Use the imperative mood ("Move cursor to..." not "Moves cursor to...")
* Limit the first line to 72 characters or less
* **[Optional]** Add more information on the body of the commit message. Body must be separated from the title with an empty line.
* **[Optional]** Use one of the [icons](#using-emoticons) to identify the type of commit. Usage of visual clues provides a fast way to understand the goal of each commit.


### Using emoticons
  * :construction: `:construction:` for work in progress
  * :recycle: `:recycle:` when refactoring and improving the format/structure of the code
  * :nail_care: `:nail_care:` for formatting issues
  * :racehorse: `:racehorse:` when improving performance
  * :memo: `:memo:` when writing docs
  * :boom: `:boom:` when making an hotfix
  * :bug: `:bug:` when fixing a bug
  * :skull: `:skull:` when removing deprecated/death code
  * :green_heart: `:green_heart:` when fixing the CI build
  * :white_check_mark: `:white_check_mark:` when adding tests
  * :lock: `:lock:` when dealing with security
  * :arrow_up: `:arrow_up:` when upgrading dependencies
  * :arrow_down: `:arrow_down:` when downgrading dependencies
