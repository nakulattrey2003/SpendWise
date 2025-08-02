FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/spendwise-0.0.1-SNAPSHOT.jar spendwise-v1.0_app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "spendwise-v1.0_app.jar"]