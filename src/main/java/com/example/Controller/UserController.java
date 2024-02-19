package com.example.Controller;

import Services.UserService;
import com.example.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/users/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        List<String> validationMessages = userService.validatePassword(user.getPassword());
        if (!validationMessages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationMessages);
        }

        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email already exists. Please login.");
        }

        userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
    }
}