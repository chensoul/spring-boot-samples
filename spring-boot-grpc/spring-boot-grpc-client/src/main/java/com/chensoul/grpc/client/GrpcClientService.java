package com.chensoul.grpc.client;


import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class GrpcClientService {

    @GrpcClient("local-grpc-server")
    private com.chensoul.grpc.demo.HelloServiceGrpc.HelloServiceBlockingStub helloServiceStub;

    public com.chensoul.grpc.demo.HelloResponse receiveGreeting(String name) {
        com.chensoul.grpc.demo.HelloRequest request = com.chensoul.grpc.demo.HelloRequest.newBuilder()
                .setName(name)
                .build();
        return helloServiceStub.sayHello(request);
    }

}
