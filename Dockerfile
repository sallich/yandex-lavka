FROM openjdk:17.0.1-jdk-slim

ARG JAR_FILE=build/libs/*SNAPSHOT.jar

WORKDIR /opt/app

COPY . .

RUN ./gradlew build

RUN cp ${JAR_FILE} yandex-lavka.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","yandex-lavka.jar"]
