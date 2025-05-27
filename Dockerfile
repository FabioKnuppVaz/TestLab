FROM alpine:latest

RUN apk add --no-cache \
    openjdk17 \
    maven \
    bash

ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk \
    MAVEN_HOME=/usr/share/maven

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests
RUN chmod +x /app/scripts/start.sh

EXPOSE 8080

CMD ["/app/scripts/start.sh"]