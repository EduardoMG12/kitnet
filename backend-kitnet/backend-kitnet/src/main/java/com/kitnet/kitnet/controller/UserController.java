package com.kitnet.kitnet.controller;

import com.kitnet.kitnet.dto.UserLoginDTO;
import com.kitnet.kitnet.dto.UserRegisterDTO;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody @Valid UserRegisterDTO dto) throws Exception {
        return userService.register(dto);
    }

    @PostMapping("/login")
    public User login(@RequestBody @Valid UserLoginDTO dto) throws Exception {
        return userService.login(dto);
    }
}
