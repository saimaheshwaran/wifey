FROM eclipse-temurin:21
LABEL authors="saimaheshwaran"

WORKDIR /app

COPY out/wifey-2.0.0.jar  /app/wifey.jar

ENTRYPOINT ["java", "-jar", "wifey.jar"]