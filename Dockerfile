# Start with a base image containing Java runtime
FROM openjdk:17-jdk-alpine AS build

# Set the current working directory inside the image
WORKDIR /wallt_app

# Copy gradle.war and build.gradle to the image
COPY gradlew .
COPY gradle gradle
COPY build.gradle .

# Grant permission to gradlew
RUN chmod +x ./gradlew

# Copy the rest of the application to the image
COPY src src

# Build the application
RUN ./gradlew build

# Start a new stage to keep our final image clean and small
FROM openjdk:17-jdk-alpine

# Set the current working directory inside the image
WORKDIR /wallt_app

# Copy the jar file from the build stage
COPY --from=build /wallt_app/build/libs/*.jar app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","app.jar"]