# ---- СТАДИЯ 1: сборка ----
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -B -ntp -DskipTests dependency:resolve

COPY src ./src
RUN mvn -B -DskipTests clean package

# ---- СТАДИЯ 2: рантайм ----
FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
