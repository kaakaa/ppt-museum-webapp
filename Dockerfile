FROM java
MAINTAINER kaakaa <stooner.hoe@gmail.com>

ENV VERSION 0.0.3-SNAPSHOT
RUN curl -L https://github.com/kaakaa/ppt-museum-webapp/releases/download/${VERSION}/ppt-museum-${VERSION}.zip \
                -o /opt/ppt-museum-${VERSION}.zip
RUN unzip /opt/ppt-museum-${VERSION}.zip -d /usr/local/src
RUN rm -f /opt/ppt-museum-${VERSION}.zip

ENTRYPOINT ["/usr/local/src/ppt-museum-${VERSION}/bin/ppt-museum"]
