FROM maven-with-local-dependencies AS build

COPY src /home/app/src
COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package


FROM openjdk:17-jdk-slim

COPY --from=build /home/app/target/notification-server-0.0.1-SNAPSHOT.jar /usr/local/notification-server/notification-server-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "/usr/local/notification-server/notification-server-0.0.1-SNAPSHOT.jar"]