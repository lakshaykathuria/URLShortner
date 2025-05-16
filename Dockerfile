# === Build Stage ===
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy source code
COPY . .

# Build the app
RUN mvn clean package -DskipTests

# === Run Stage ===
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy built JAR from previous stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
