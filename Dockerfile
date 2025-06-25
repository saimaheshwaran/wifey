FROM eclipse-temurin:21
LABEL authors="saimaheshwaran"
ARG JAR_FILE=target/*.jar
COPY ./target/wifey-2.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]