FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/*.jar
WORKDIR /
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT exec java ${JAVA_OPTS} -jar /app.jar