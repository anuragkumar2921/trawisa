# Stage 1: Build the application
FROM openjdk:17-jdk-slim AS build
WORKDIR /workspace

# Copy Gradle wrapper and scripts
COPY gradlew gradlew.bat /workspace/
COPY gradle /workspace/gradle
RUN chmod +x /workspace/gradlew

# Copy build files
COPY build.gradle /workspace/
COPY settings.gradle /workspace/

# Copy application source
COPY src /workspace/src
COPY baseApp /workspace/baseApp

# Build the application
RUN ./gradlew bootJar --no-daemon

# Stage 2: Runtime
FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /workspace/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
