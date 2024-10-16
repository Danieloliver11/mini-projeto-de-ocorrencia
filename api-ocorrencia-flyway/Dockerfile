FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/api-ocorrencia-flyway-0.0.1-SNAPSHOT.jar /app/api-ocorrencia-flyway-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/app/api-ocorrencia-flyway-0.0.1-SNAPSHOT.jar"]

EXPOSE 8082