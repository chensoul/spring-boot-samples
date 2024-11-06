package com.chensoul.grpc.client;


import com.chensoul.grpc.demo.HelloRequest;
import com.chensoul.grpc.demo.HelloResponse;
import com.chensoul.grpc.demo.HelloServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class GrpcClientService {

    @GrpcClient("local-grpc-server")
    private HelloServiceGrpc.HelloServiceBlockingStub helloServiceStub;

    public HelloResponse receiveGreeting(String name) {
        HelloRequest request = HelloRequest.newBuilder()
                .setName(name)
                .build();
        return helloServiceStub.sayHello(request);
    }

}
