FROM anapsix/alpine-java
MAINTAINER kaakaa <stooner.hoe@gmail.com>

ENV ARTIFACT ppt-museum-webapp
ADD build/install/${ARTIFACT} /usr/local/src

ENTRYPOINT ["sh", "-c", "/usr/local/src/bin/${ARTIFACT}"]
