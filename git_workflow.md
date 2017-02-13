# 基于 Git 的工作流程指南

本文采用[gitlab flow](https://docs.gitlab.com/ee/workflow/gitlab_flow.html)作为基于 git 的工作流指南。它是 Gitlab.com 的推荐做法。它既能适应不同开发环境的弹性，又有单一主分支的简单和便利性。

## 上游优先 upstream first

 Gitlab flow 的最大原则叫做"上游优先"（upsteam first），即只存在一个主分支master，它是所有其他分支的"上游"。只有上游分支采纳的代码变化，才能应用到其他分支。

## 发布流程

Gitlab flow 支持两种项目风格：持续发布和版本发布。

### 持续发布项目

![branches & environments](https://docs.gitlab.com/ee/workflow/environment_branches.png)

对于"持续发布"的项目，它建议在master分支以外，再建立不同的环境分支。比如，"开发环境"的分支是master，"预发环境"的分支是pre-production，"生产环境"的分支是production。

开发分支是预发分支的"上游"，预发分支又是生产分支的"上游"。代码的变化，必须由"上游"向"下游"发展。比如，生产环境出现了bug，这时就要新建一个功能分支，先把它合并到master，确认没有问题，再cherry-pick到pre-production，这一步也没有问题，才进入production。

只有紧急情况，才允许跳过上游，直接合并到下游分支。

**注意：可根据实际情况创建分支。比如 master -> qa -> stg -> prod。**

### 版本发布项目

![branches & releases](https://docs.gitlab.com/ee/workflow/release_branches.png)

对于"版本发布"的项目，建议的做法是每一个稳定版本，都要从master分支拉出一个分支，比如2-3-stable、2-4-stable等等。

以后，只有修补bug（图中的 cherry-pick），才允许将代码从master分支合并到这些分支中。并且此时应该更新版本分支的补丁版本号，并用这个版本号打一个tag。如图中所示的情况，比如我们cherry-pick了一个commit至2-3-stable分支，则此时在此分支上打一个2-3-1-stable的tag出来。

**版本号请按[Semantic Versioning](http://semver.org/)中的规范来进行定义。**

## 特性和修订分支

当开发人员收到新的特性开发需求和 bug 报告时，应从 master 分支中创建出新的分支进行工作。待任务完成后，再合并回 master  分支。

特性和修订分支，可以采用以下规则命名:

* 特性分支
    * feature/name 例： feature/salary_report
    * feature/number_name 例： feature/11\_salary\_report(如有对应的需求编号）
* 修订分支
    * hotfix/name 例： hotfix/wrong_number
    * hotfix/number_name 例： hotfix/17\_wrong\_number(如有对应的需求编号）




无论是持续集成还是版本发布项目，主干分支只有 master 一个。根据上游优先原则，无论特性还是修订分支，都必须先合并进 master 分支，再合并进其它分支。