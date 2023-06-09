package com.example.filikov_advanced_server.services;

import com.example.filikov_advanced_server.dto.user_dto.AuthDto;
import com.example.filikov_advanced_server.dto.user_dto.LoginUserDto;
import com.example.filikov_advanced_server.dto.user_dto.RegisterUserDto;
import com.example.filikov_advanced_server.responses.CustomSuccessResponse;

public interface AuthService {
    public CustomSuccessResponse<LoginUserDto> registerUser(RegisterUserDto userDto);
    public CustomSuccessResponse<LoginUserDto> loginUser(AuthDto userDto);
}
