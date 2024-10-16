FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/api-ocorrencia-0.0.1-SNAPSHOT.jar /app/api-ocorrencia-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/app/api-ocorrencia-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080