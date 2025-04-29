package com.frontend_thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin") // Todas as URLs neste controller começarão com /admin
public class AuthController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard"; // Retorna o arquivo dashboard.html dentro da pasta admin
    }
}