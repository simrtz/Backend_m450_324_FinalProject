FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B  # Lade alle Maven-Abhängigkeiten offline
COPY src ./src
RUN mvn clean package -DskipTests # Baue das Projekt ohne Tests


FROM openjdk:17-slim AS runtime
WORKDIR /app
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
