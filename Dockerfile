FROM java
MAINTAINER kaakaa <stooner.hoe@gmail.com>

ENV ARTIFACT ppt-museum-webapp
ENV VERSION 0.0.3-SNAPSHOT
RUN curl -L https://github.com/kaakaa/${ARTIFACT}/releases/download/${VERSION}/${ARTIFACT}-${VERSION}.zip \
                -o /opt/${ARTIFACT}-${VERSION}.zip
RUN unzip /opt/${ARTIFACT}-${VERSION}.zip -d /usr/local/src
RUN rm -f /opt/${ARTIFACT}-${VERSION}.zip

ENTRYPOINT ["/usr/local/src/${ARTIFACT}-${VERSION}/bin/${ARTIFACT}"]
