package com.example.API.Gateway.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

    private static final Logger log = LoggerFactory.getLogger(FallBackController.class);
    @GetMapping("/fallback/books")
    public ResponseEntity<String> bookServiceFallback(){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Book service temporarily unavailable");
    }
}
