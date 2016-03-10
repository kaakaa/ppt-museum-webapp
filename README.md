# ppt-museum-webapp
[![Build Status](https://travis-ci.org/kaakaa/ppt-museum-webapp.svg?branch=master)](https://travis-ci.org/kaakaa/ppt-museum-webapp)
[![Coverage Status](https://coveralls.io/repos/github/kaakaa/ppt-museum-webapp/badge.svg?branch=master)](https://coveralls.io/github/kaakaa/ppt-museum-webapp?branch=master)

This is webapp for [kaakaa/ppt-museum: ppt/pptx file uploader](https://github.com/kaakaa/ppt-museum).

# Build

## Build Application.

```
./gradlew build
```

## Build Docker image

```
./gradlew installDist

docker build -t ppt-museum-web .
```
