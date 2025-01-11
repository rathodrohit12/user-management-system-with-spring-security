# Stage 1: Build the app
FROM openjdk:21-jdk-slim as build

# Install Maven
RUN apt-get update && apt-get install -y maven

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml to the working directory
COPY pom.xml .

# Download the dependencies (this helps with caching the layers)
RUN mvn dependency:go-offline

# Copy the source code to the container
COPY src ./src

# Build the Spring Boot application using Maven
RUN mvn clean package -DskipTests

# Stage 2: Run the app
FROM openjdk:21-jdk-slim

# Set the working directory for the runtime image
WORKDIR /app

# Copy the jar file from the build stage into the final stage
COPY --from=build /app/target/*.jar /app/user-management-system.jar

# Expose the port your app will run on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/user-management-system.jar"]
