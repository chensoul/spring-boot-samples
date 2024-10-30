package com.chensoul.grpc.server;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class HelloServiceImpl extends com.chensoul.grpc.demo.HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void sayHello(com.chensoul.grpc.demo.HelloRequest request, StreamObserver<com.chensoul.grpc.demo.HelloResponse> responseObserver) {
        com.chensoul.grpc.demo.HelloResponse reply = com.chensoul.grpc.demo.HelloResponse.newBuilder()
                .setMessage("Hello ==> " + request.getName())
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

}
