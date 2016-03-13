FROM java
MAINTAINER kaakaa <stooner.hoe@gmail.com>

ENV ARTIFACT ppt-museum-webapp
ENV VERSION 0.0.4-SNAPSHOT

ADD build/install/${ARTIFACT} /usr/local/src

ENTRYPOINT ["sh", "-c", "/usr/local/src/bin/${ARTIFACT}"]
