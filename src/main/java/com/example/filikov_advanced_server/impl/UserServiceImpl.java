package com.example.filikov_advanced_server.impl;

import com.example.filikov_advanced_server.dto.LoginUserDto;
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

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public CustomSuccessResponse<LoginUserDto> registerUser(RegisterUserDto userDto) {
        if(userRepo.existsByEmail(userDto.getEmail())) {
           throw new CustomException(ValidationConstants.USER_ALREADY_EXISTS);
        }
        UserEntity userEntity = UserMapper.INSTANCE.registerDtoToEntity(userDto);
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        LoginUserDto response = UserMapper.INSTANCE.entityToLoginUserDto(userEntity);
        userRepo.save(userEntity);
        String token = jwtTokenProvider.createToken(userDto.getEmail());
        response.setToken(token);
        return CustomSuccessResponse.getSuccessResponse(response);
    }
}