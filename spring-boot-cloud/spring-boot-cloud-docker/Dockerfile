FROM eclipse-temurin:21.0.6_7-jre-alpine
ARG ARTIFACT_NAME=target/*.jar
COPY ${ARTIFACT_NAME}.jar app.jar
ARG EXPOSED_PORT=8080
EXPOSE ${EXPOSED_PORT}
ENTRYPOINT ["java","-jar","app.jar"]

