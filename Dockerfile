FROM eclipse-temurin:21
LABEL authors="saimaheshwaran"
ARG JAR_FILE=target/*.jar
COPY ./out/wifey.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]