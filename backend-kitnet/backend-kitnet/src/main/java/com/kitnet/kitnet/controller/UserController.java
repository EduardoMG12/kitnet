package com.kitnet.kitnet.controller;

import com.kitnet.kitnet.dto.UserLoginDTO;
import com.kitnet.kitnet.dto.UserRegisterDTO;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterDTO dto) throws Exception {
        User registeredUser = userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginDTO dto) throws Exception {
        User loggedInUser = userService.login(dto);
        return ResponseEntity.ok(loggedInUser);
    }
}
