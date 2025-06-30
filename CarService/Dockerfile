# Stage 1: Build with common-security
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /build

# Copy all source code (parent should include common-security)
COPY ../.. .

# Build common-security and user-service
RUN mvn clean package -pl common-security,car-service -am -DskipTests
# Stage 2: Runtime
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=builder /build/car-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
