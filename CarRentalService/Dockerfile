# Stage 1: Build all from root
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /build

# Copy the entire project so that Maven can resolve common-security
COPY ../.. .

# Build common-security and the CarService module
RUN mvn clean package -pl common-security,CarRentalService -am -DskipTests

# Stage 2: Runtime image
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy the built jar of CarService
COPY --from=builder /build/CarRentalService/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
