package com.example.library.controllers;

import com.example.library.dtos.UserResponseDto;
import com.example.library.services.UserServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RefreshScope
public class TestController {

    private final UserServiceClient userServiceClient;

    public TestController(UserServiceClient userServiceClient){
        this.userServiceClient=userServiceClient;
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable String id){
        return userServiceClient.getUserById(id);
    }

    @Value("${book.message}")
    private String message;

    @GetMapping("/config-test")
    public String configTest(){
        return message;
    }
}
