package com.healtcheck.labeng.controllers;

import com.healtcheck.labeng.dtos.UserLoginDTO;
import com.healtcheck.labeng.dtos.UserRegisterDTO;
import com.healtcheck.labeng.entities.User;
import com.healtcheck.labeng.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        User response = userService.register(userRegisterDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        User response = userService.login(userLoginDTO);
        return ResponseEntity.ok(response);
    }
}
