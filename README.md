# This PR is test for feature about Github Review.


# ppt-museum-webapp
[![Build Status](https://travis-ci.org/kaakaa/ppt-museum-webapp.svg?branch=master)](https://travis-ci.org/kaakaa/ppt-museum-webapp)
[![Coverage Status](https://coveralls.io/repos/github/kaakaa/ppt-museum-webapp/badge.svg?branch=master)](https://coveralls.io/github/kaakaa/ppt-museum-webapp?branch=master)

This is webapp for [kaakaa/ppt-museum: ppt/pptx file uploader](https://github.com/kaakaa/ppt-museum).

# Usage

see [kaakaa/ppt-museum: ppt/pptx file uploader](https://github.com/kaakaa/ppt-museum "kaakaa/ppt-museum: ppt/pptx file uploader")

# Build

## Task Dependencies

![https://raw.githubusercontent.com/kaakaa/ppt-museum-webapp/master/gradle_task_dependencies.png](https://raw.githubusercontent.com/kaakaa/ppt-museum-webapp/master/gradle_task_dependencies.png)

> created by [mmalohlava/gradle-visteg: Exports task execution graph as .dot file](https://github.com/mmalohlava/gradle-visteg)

## Build Application

```
./gradlew build
```

## Build Docker image

Additionally, We have task `buildDockerImage` for creating docker image.  
`buildDockerImage` task depends on `installDist` task and created docker image including outputs of `installDist` task.


```
./gradlew buildDockerImage 
```

## Release

After pushing all commits, execute follow command.

```
./gradlew release
```

> [researchgate/gradle-release: gradle-release is a plugin for providing a Maven-like release process for projects using Gradle](https://github.com/researchgate/gradle-release)

# Docker Image

[ppt-museum-web](https://hub.docker.com/r/kaakaa/ppt-museum-web/ "kaakaa/ppt-museum-web")
