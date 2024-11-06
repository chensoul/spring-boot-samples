# Spring Boot Docker

java dockerfile

- eclipse-temurin:21-jre-alpine
- eclipse-temurin:21-jre-jammy
- eclipse-temurin:21-jre-focal
- eclipse-temurin:21-jre-noble

## How to

### Build image using precompiled jar

```bash
mvn clean package

docker build -t spring-precompiled .

docker images |grep spring-precompile
```

Image Size - 235MB

### Build image using precompiled jar with layered image

```shell
mvn clean package

docker build -f layered.Dockerfile -t spring-layered .

docker images |grep spring-layered
```

Image Size - 235MB

### Build image using maven

```bash
docker build -f maven.Dockerfile -t spring-maven .

docker images |grep spring-maven
```

Image Size - 671MB

### Build image using maven with docker-compose file

```bash
docker compose up -d
```

### Build image using multistage maven

```shell
docker build -f multistage.maven.Dockerfile -t spring-multistage-maven .

docker images |grep spring-multistage-maven
```

Image Size - 235MB

### Build image using multistage maven with layered image

```shell
docker build -f multistage.maven.layered.Dockerfile -t spring-multistage-layered-maven .

docker images |grep spring-multistage-layered-maven
```

> Use `docker init` command to generate the layered stage dockerfile

Image Size - 235MB

### Build image using buildpacks

```bash
mvn spring-boot:build-image -Dspring-boot.build-image.imageName=spring-boot

docker images |grep spring-boot
```

Image Size - 347MB

### Build image using Jib

```shell
mvn compile jib:dockerBuild

docker images |grep spring-jib
```

Image Size - 305MB

### Build image using fabric8 docker maven plugin

```bash
mvn docker:build

docker images |grep spring-fabric8
```

Image Size - 308MB

### Build image using spotify maven plugin

```bash
mvn dockerfile:build

docker images |grep spring-spotify
```

> Notice: The plugin does not support arm platform

### Build image using kubernetes maven plugin

```bash
mvn k8s:build

docker images |grep spring-kubernetes
```

Image Size - 321MB

### Build image using codehaus exec maven plugin

See https://github.com/odedia/spring-petclinic-microservices/blob/main/pom.xml

```bash
mvn exec:exec docker

docker images |grep spring-boot-docker
```

Image Size - 232MB

### Clean images

```bash
docker image rm -f spring-precompile spring-maven spring-multistage-maven spring-multistage-layered-maven spring-layered spring-fabric8 spring-jib spring-boot
```

## References

- [使用Spring Boot创建docker image](https://www.cnblogs.com/flydean/p/13824496.html)
- [https://github.com/jhipster/jhipster-lite/blob/main/Dockerfile](https://github.com/jhipster/jhipster-lite/blob/main/Dockerfile)
- [https://docs.spring.io/spring-boot/reference/packaging/container-images/dockerfiles.html](https://docs.spring.io/spring-boot/reference/packaging/container-images/dockerfiles.html)
- <https://medium.com/javarevisited/docker-with-spring-boot-part-1-create-images-using-docker-file-2a65b2a357ee>
- https://dzone.com/articles/ways-to-reduce-jvm-docker-image-size
- https://dzone.com/articles/best-practices-java-memory-arguments-for-container
- https://github.com/cloud-native-java-with-k8s-livelessons