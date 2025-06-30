# Stage 1: Build all from root
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /build

# Copy the entire project so that Maven can resolve common-security
COPY ../.. .

# Build common-security and the target service module
RUN mvn clean package -pl common-security,User-Service -am -DskipTests

# Stage 2: Runtime image
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy the built jar of this service
COPY --from=builder /build/User-Service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
