FROM maven-with-local-dependencies AS build

COPY src /home/app/src
COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:17-jdk-slim

COPY target/oauth-server-0.0.1-SNAPSHOT.jar /usr/local/oauth-server/oauth-server-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "/usr/local/oauth-server/oauth-server-0.0.1-SNAPSHOT.jar"]