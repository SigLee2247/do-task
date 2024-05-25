FROM openjdk:17

LABEL authors="siglee"

ARG JAR_FILE=build/libs/terra-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} terra-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=prod","-Duser.timezone=Asia/Seoul", "/terra-0.0.1-SNAPSHOT.jar"]
