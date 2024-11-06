package com.chensoul.grpc.client;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GrpcClientController {

    @Autowired
    private GrpcClientService grpcClientService;

    @RequestMapping("/")
    public Map<String,String> printMessage(@RequestParam(defaultValue = "user") String name) {
        return Map.of("reply", grpcClientService.receiveGreeting(name).getMessage());
    }

}
