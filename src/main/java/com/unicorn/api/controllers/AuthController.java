package com.unicorn.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicorn.api.domain.user.LoginRequest;
import com.unicorn.api.domain.user.RegisterRequest;
import com.unicorn.api.domain.user.User;
import com.unicorn.api.services.AuthService;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@AllArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest user) {
        return authService.login(user, authenticationManager);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest user) {
        return authService.register(user);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        return authService.updateUserRistred(user);
    }

    // @PostMapping("/change-password")
    // public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest
    // user) {
    // return authService.changePassword(user);
    // }

}
