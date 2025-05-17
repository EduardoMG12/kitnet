package com.kitnet.kitnet.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/register")
    public String helloWorld() {
        return "Hello, World!";
    }

//    @PostMapping("/login")
//    public
}