FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/Enterprise-Project-0.0.1-SNAPSHOT.jar /app/5e-spells-backend.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/5e-spells-backend.jar"]