# ppt-museum-webapp
[![Build Status](https://travis-ci.org/kaakaa/ppt-museum-webapp.svg?branch=master)](https://travis-ci.org/kaakaa/ppt-museum-webapp)
[![Coverage Status](https://coveralls.io/repos/github/kaakaa/ppt-museum-webapp/badge.svg?branch=master)](https://coveralls.io/github/kaakaa/ppt-museum-webapp?branch=master)

This is webapp for [kaakaa/ppt-museum: ppt/pptx file uploader](https://github.com/kaakaa/ppt-museum).

# Build

## Task Dependencies

[https://raw.githubusercontent.com/kaakaa/ppt-museum-webapp/master/gradle_task_dependencies.png](https://raw.githubusercontent.com/kaakaa/ppt-museum-webapp/master/gradle_task_dependencies.png)

> created by [mmalohlava/gradle-visteg: Exports task execution graph as .dot file](https://github.com/mmalohlava/gradle-visteg)

## Build Application

```
./gradlew build
```

## Build Docker image

Additionally, We have task `buildDockerImage` for creating docker image.  
`buildDockerImage` task depends on `installDist` task and creating docker image include archives of `installDist` task.


```
./gradlew buildDockerImage 
```

