# syntax=docker/dockerfile:1

# Étape 1 : compilation (les outils de build ne sont jamais livrés)
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B -q dependency:go-offline
COPY src ./src
# Les tests tournent dans la pipeline (phase 5), pas dans le build de l'image
RUN mvn -B -q package -DskipTests

# Étape 2 : exécution (image minimale, sans shell, non-root)
FROM gcr.io/distroless/java21-debian12:nonroot
WORKDIR /app
COPY --from=build /app/target/orders-api-*.jar /app/app.jar
USER 65532:65532
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "/app/app.jar"]