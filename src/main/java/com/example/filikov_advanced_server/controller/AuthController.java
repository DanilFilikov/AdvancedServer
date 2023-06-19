package com.example.filikov_advanced_server.controller;

import com.example.filikov_advanced_server.dto.RegisterUserDto;
import com.example.filikov_advanced_server.dto.AuthDto;
import com.example.filikov_advanced_server.service.AuthService;
import com.example.filikov_advanced_server.service.UserService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegisterUserDto userDto){
        return ResponseEntity.ok(authService.registerUser(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody AuthDto authDto){
        return ResponseEntity.ok(authService.loginUser(authDto));
    }
}