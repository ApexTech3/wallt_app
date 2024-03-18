## Build stage
#FROM openjdk:17-jdk-alpine AS build
#WORKDIR /workspace
#COPY . .
#RUN chmod +x ./gradlew
#RUN ./gradlew build
#
## Package stage
#FROM openjdk:17-jdk-alpine
#COPY --from=build /build/libs/*.jar app.jar
#ENTRYPOINT ["java","-jar","app.jar"]

# Start with a base image containing Java runtime
FROM openjdk:17-jdk-alpine

# The application's .jar file
ARG JAR_FILE=build/libs/*.jar


COPY build/libs/*.jar wallt_app-0.0.1-SNAPSHOT.jar

# Run the jar file
ENTRYPOINT ["java","-jar","wallt_app-0.0.1-SNAPSHOT.jar"]