package com.example.filikov_advanced_server.controller;

import com.example.filikov_advanced_server.dto.PutUserDto;
import com.example.filikov_advanced_server.error.ValidationConstants;
import com.example.filikov_advanced_server.exception.CustomException;
import com.example.filikov_advanced_server.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @GetMapping
    public ResponseEntity getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserInfoById(@PathVariable @Size(min = 36, max = 36, message = ValidationConstants.MAX_UPLOAD_SIZE_EXCEEDED) String id){
        return ResponseEntity.ok(userService.getUserInfoById(UUID.fromString(id)));
    }

    @GetMapping("/info")
    public ResponseEntity getUserInfo(Authentication authentication){
        String id = authentication.getName();
        return ResponseEntity.ok(userService.getUserInfo(UUID.fromString(id)));
    }

    @DeleteMapping
    public ResponseEntity deleteUser(Authentication authentication){
        String id = authentication.getName();
        return ResponseEntity.ok(userService.deleteUser(UUID.fromString(id)));
    }

    @PutMapping
    public ResponseEntity replaceUser(@Valid @RequestBody PutUserDto putUserDto, Authentication authentication){
        String id = authentication.getName();
        return ResponseEntity.ok(userService.replaceUser(putUserDto ,UUID.fromString(id)));
    }
}