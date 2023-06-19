package com.example.filikov_advanced_server.services.impl;

import com.example.filikov_advanced_server.dto.AuthDto;
import com.example.filikov_advanced_server.dto.LoginUserDto;
import com.example.filikov_advanced_server.dto.RegisterUserDto;
import com.example.filikov_advanced_server.entity.UserEntity;
import com.example.filikov_advanced_server.error.ValidationConstants;
import com.example.filikov_advanced_server.exception.CustomException;
import com.example.filikov_advanced_server.mapper.UserMapper;
import com.example.filikov_advanced_server.repository.UserRepo;
import com.example.filikov_advanced_server.responses.CustomSuccessResponse;
import com.example.filikov_advanced_server.security.jwt.JwtTokenProvider;
import com.example.filikov_advanced_server.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public CustomSuccessResponse<LoginUserDto> registerUser(RegisterUserDto userDto) {
        if (userRepo.existsByEmail(userDto.getEmail())) {
            throw new CustomException(ValidationConstants.USER_ALREADY_EXISTS);
        }
        UserEntity userEntity = UserMapper.INSTANCE.registerDtoToEntity(userDto);
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepo.save(userEntity);
        LoginUserDto response = UserMapper.INSTANCE.entityToLoginUserDto(userEntity);
        response.setToken(jwtTokenProvider.createToken(userEntity.getId()));
        return CustomSuccessResponse.getSuccessResponse(response);
    }

    @Override
    public CustomSuccessResponse<LoginUserDto> loginUser(AuthDto authDto) {
        if (!userRepo.existsByEmail(authDto.getEmail())) {
            throw new CustomException(ValidationConstants.USER_NOT_FOUND);
        }
        if (!passwordEncoder.matches(authDto.getPassword(), userRepo.findByEmail(authDto.getEmail()).get().getPassword())) {
            throw new CustomException(ValidationConstants.PASSWORD_NOT_VALID);
        }
        UserEntity userEntity = userRepo.findByEmail(authDto.getEmail()).get();
        LoginUserDto response = UserMapper.INSTANCE.entityToLoginUserDto(userEntity);
        response.setToken(jwtTokenProvider.createToken(userEntity.getId()));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEntity.getId(), authDto.getPassword()));
        return CustomSuccessResponse.getSuccessResponse(response);
    }
}
