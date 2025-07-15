# Use Java 21 base image
FROM eclipse-temurin:21-jdk-jammy

# Set working directory
WORKDIR /app

# Copy the JAR file
COPY target/room-service-1.0.jar room-service.jar

# Run the app
ENTRYPOINT ["java", "-jar", "room-service.jar"]
