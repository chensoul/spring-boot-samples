FROM maven:3.9.11-eclipse-temurin-21-alpine
WORKDIR /app
COPY pom.xml .
# https://www.baeldung.com/maven-offline
RUN mvn dependency:go-offline
#RUN mvn dependency:resolve
COPY src src
RUN mvn package
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/spring-boot-docker-0.0.1-SNAPSHOT.jar"]