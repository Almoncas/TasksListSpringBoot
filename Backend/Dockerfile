# syntax=docker/dockerfile:1

FROM openjdk:11

EXPOSE 8080

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY src ./src

ENTRYPOINT ["./mvnw", "spring-boot:run"]