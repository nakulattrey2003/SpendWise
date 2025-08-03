# FROM eclipse-temurin:17-jre
# WORKDIR /app
# COPY target/spendwise-0.0.1-SNAPSHOT.jar spendwise-v1.0_app.jar
# EXPOSE 9090
# ENTRYPOINT ["java", "-jar", "spendwise-v1.0_app.jar"]

# Stage 1: Build the app
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the app
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/spendwise-0.0.1-SNAPSHOT.jar spendwise-v1.0_app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "spendwise-v1.0_app.jar"]
