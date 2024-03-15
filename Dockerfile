# Start with a base image containing Java runtime
FROM openjdk:17-jdk-alpine

# The application's .jar file
ARG JAR_FILE=build/libs/*.jar

# cd into the app directory
WORKDIR /app

# Copy the application's jar file to the container
COPY ${JAR_FILE} wallt_app-0.0.1-SNAPSHOT.jar

# Run the jar file
ENTRYPOINT ["java","-jar","wallt_app-0.0.1-SNAPSHOT.jar"]