package com.kitnet.kitnet.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HellowController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World!";
    }
}