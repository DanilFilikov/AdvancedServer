package com.example.filikov_advanced_server.impl;

import com.example.filikov_advanced_server.dto.AuthDto;
import com.example.filikov_advanced_server.dto.LoginUserDto;
import com.example.filikov_advanced_server.dto.PublicUserView;
import com.example.filikov_advanced_server.dto.RegisterUserDto;
import com.example.filikov_advanced_server.entity.UserEntity;
import com.example.filikov_advanced_server.error.ValidationConstants;
import com.example.filikov_advanced_server.exception.CustomException;
import com.example.filikov_advanced_server.mapper.UserMapper;
import com.example.filikov_advanced_server.responses.CustomSuccessResponse;
import com.example.filikov_advanced_server.repository.UserRepo;
import com.example.filikov_advanced_server.security.jwt.JwtTokenProvider;
import com.example.filikov_advanced_server.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public CustomSuccessResponse<List<PublicUserView>> getUsers(){
        List<PublicUserView> users = userRepo.findAll().stream()
                .map(UserMapper.INSTANCE::entityToPublicUserView)
                .toList();
        return CustomSuccessResponse.getSuccessResponse(users);
    }
}