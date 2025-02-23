# syntax=docker/dockerfile:1

# https://docs.docker.com/reference/dockerfile/
# https://docs.docker.com/build/guide/multi-stage/

FROM maven:3.9.9-eclipse-temurin-21-alpine AS base
WORKDIR /build
COPY src src
RUN sed -i -E '159a <mirror>\n<id>aliyun</id>\n<name>Aliyun Mirror</name>\n<url>http://maven.aliyun.com/nexus/content/groups/public/</url>\n<mirrorOf>central</mirrorOf>\n</mirror>' /usr/share/maven/conf/settings.xml

FROM base AS test
WORKDIR /build
RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 \
    mvn test

FROM base AS package
WORKDIR /build
RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 \
    mvn package -DskipTests && \
    mv target/$(mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout).jar target/app.jar

FROM package AS extract
WORKDIR /build
RUN java -Djarmode=layertools -jar target/app.jar extract --destination target/extracted

FROM extract AS development
WORKDIR /build
RUN cp -r build/target/extracted/dependencies/. ./
RUN cp -r build/target/extracted/spring-boot-loader/. ./
RUN cp -r build/target/extracted/snapshot-dependencies/. ./
RUN cp -r build/target/extracted/application/. ./
CMD [ "java", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000'", "org.springframework.boot.loader.launch.JarLauncher" ]

FROM eclipse-temurin:21.0.6_7-jre-alpine AS final
RUN adduser --disabled-password --gecos "" appuser
USER appuser
WORKDIR /app
COPY --from=extract build/target/extracted/dependencies/ ./
COPY --from=extract build/target/extracted/spring-boot-loader/ ./
COPY --from=extract build/target/extracted/snapshot-dependencies/ ./
COPY --from=extract build/target/extracted/application/ ./
EXPOSE 8080
ENTRYPOINT [ "java",  "org.springframework.boot.loader.launch.JarLauncher" ]
