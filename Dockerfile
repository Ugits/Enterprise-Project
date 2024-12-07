# Stage 1: Build and Test the Application
FROM gradle:7.6.0-jdk17 AS build

# Set the working directory inside the container
WORKDIR /home/gradle/project

# Copy Gradle Wrapper and build files
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Grant execute permissions for the Gradle Wrapper
RUN chmod +x gradlew

# Copy the source code
COPY src src

# Run tests
RUN ./gradlew test --no-daemon

# Build the application
RUN ./gradlew build --no-daemon

# Stage 2: Create the Runtime Image
FROM openjdk:17-jdk-slim

# Set the working directory for the runtime environment
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /home/gradle/project/build/libs/Enterprise-Project-0.0.1-SNAPSHOT.jar /app/5e-spells-backend.jar

# Expose the application's port
EXPOSE 8080

# Define the entry point to run the application
ENTRYPOINT ["java", "-jar", "/app/5e-spells-backend.jar"]
