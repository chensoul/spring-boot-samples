# spring-boot-grpc

## How to

Deploy grpc-interfaces to Maven Center

```bash
cd spring-boot-grpc/grpc-interfaces
mvn javadoc:javadoc javadoc:jar source:jar deploy -Dgpg.passphrase=xxxx
```

## References

- https://yidongnan.github.io/grpc-spring-boot-starter/zh-CN/server/getting-started.html
- https://grpc.io/docs/languages/java/quickstart/