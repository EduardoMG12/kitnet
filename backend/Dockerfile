FROM openjdk:21-jdk-slim-bullseye AS builder

RUN apt-get update && apt-get upgrade -y && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY gradlew .
RUN chmod +x gradlew 
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .

COPY src/ src/

# Constrói a aplicação usando Gradle.
# `clean build` garante uma construção limpa.
# `-x test` pula a execução dos testes para agilizar a construção da imagem.
# `--no-daemon` evita problemas com o daemon do Gradle dentro do contêiner.
RUN ./gradlew clean build -x test --no-daemon

FROM openjdk:21-jdk-slim-bullseye

RUN apt-get update && apt-get upgrade -y && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]