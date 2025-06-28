FROM eclipse-temurin:21
LABEL authors="saimaheshwaran"

ENV dbconnect=jdbc:postgresql://h4so0o088gc8ko80gkok4gc4:5432/postgres

WORKDIR /app

COPY target/wifey-2.0.0.jar  /app/wifey.jar

ENTRYPOINT ["java", "-jar", "wifey.jar"]