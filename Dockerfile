FROM eclipse-temurin:21
LABEL authors="saimaheshwaran"

WORKDIR /app

COPY out/wifey.jar  /app/wifey.jar

ENTRYPOINT ["java", "-jar", "wifey.jar"]