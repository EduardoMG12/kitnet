FROM openjdk:21-jdk-slim-bullseye AS builder

RUN apt-get update && apt-get upgrade -y && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY gradlew .
RUN chmod +x gradlew 
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .

COPY src/ src/

RUN ./gradlew clean build -x test --no-daemon

FROM openjdk:21-jdk-slim-bullseye

USER root

RUN apt-get update && apt-get upgrade -y && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]