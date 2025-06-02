FROM alpine:3.20 AS builder
RUN apk add --no-cache openjdk17 maven
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk \
    MAVEN_CONFIG=/root/.m2
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests


FROM alpine:3.20
RUN apk add --no-cache openjdk17-jre
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk \
    PATH="${JAVA_HOME}/bin:${PATH}"
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]