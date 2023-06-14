package com.example.demo.impl;

import com.example.demo.dto.LoginUserDto;
import com.example.demo.dto.RegisterUserDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.error.ValidationConstants;
import com.example.demo.exception.CustomException;
import com.example.demo.model.CustomSuccessResponse;
import com.example.demo.repository.UserRepo;
import com.example.demo.security.jwt.JwtTokenProvider;
import com.example.demo.service.UserService;
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
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDto.getName());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setEmail(userDto.getEmail());
        userEntity.setAvatar(userDto.getAvatar());
        userEntity.setRole(userDto.getRole());
        userRepo.save(userEntity);
        String token = jwtTokenProvider.createToken(userDto.getEmail());
        return CustomSuccessResponse.getSuccessResponse(LoginUserDto.mapToDto(userEntity, token));
    }
}