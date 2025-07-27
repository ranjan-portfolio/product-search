# Use a lightweight JDK base image
FROM eclipse-temurin:17.0.8_7-jdk

# Set the working directory in the container
WORKDIR /app

# Add the Spring Boot fat JAR to the container
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Expose the port your Spring Boot app runs on (adjust if needed)
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]