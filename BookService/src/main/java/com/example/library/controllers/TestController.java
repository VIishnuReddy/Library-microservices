package com.example.library.controllers;

import com.example.library.dtos.UserResponseDto;
import com.example.library.services.UserServiceClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final UserServiceClient userServiceClient;

    public TestController(UserServiceClient userServiceClient){
        this.userServiceClient=userServiceClient;
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable String id){
        return userServiceClient.getUserById(id);
    }
}
