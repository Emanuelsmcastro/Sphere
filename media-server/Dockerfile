FROM maven-with-local-dependencies AS build

COPY src /home/app/src
COPY videos /home/app/videos
COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package -DskipTests=true


FROM openjdk:17-jdk-slim

RUN apt-get update && \
    apt-get install -y ffmpeg && \
    rm -rf /var/lib/apt/lists/*

COPY --from=build /home/app/target/media-server-0.0.1-SNAPSHOT.jar /usr/local/media-server/media-server-0.0.1-SNAPSHOT.jar

COPY --from=build /home/app/videos /usr/local/media-server/videos
CMD ["java", "-jar", "/usr/local/media-server/media-server-0.0.1-SNAPSHOT.jar"]