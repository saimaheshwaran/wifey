# First stage: build the application
FROM eclipse-temurin:21 as build
LABEL authors="saimaheshwaran"

WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN apt-get update && \
    apt-get install -y maven && \
    mvn clean package -DskipTests && \
    apt-get remove -y maven && \
    apt-get autoremove -y && \
    rm -rf /var/lib/apt/lists/*

# Second stage: create the runtime image
FROM eclipse-temurin:21
LABEL authors="saimaheshwaran"

WORKDIR /app

# Copy only the built JAR from the first stage
COPY --from=build /app/target/wifey-*.jar /app/wifey.jar

ENTRYPOINT ["java", "-jar", "wifey.jar"]