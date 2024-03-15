# Start with a base image containing Java runtime
FROM openjdk:17-jdk-alpine

# The application's .jar file
ARG JAR_FILE=build/libs/*.jar


COPY build/libs/*.jar wallt_app-0.0.1-SNAPSHOT.jar

# Run the jar file
ENTRYPOINT ["java","-jar","wallt_app-0.0.1-SNAPSHOT.jar"]