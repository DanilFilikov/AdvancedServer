package com.example.filikov_advanced_server.service;

import com.example.filikov_advanced_server.dto.AuthDto;
import com.example.filikov_advanced_server.dto.LoginUserDto;
import com.example.filikov_advanced_server.dto.RegisterUserDto;
import com.example.filikov_advanced_server.responses.CustomSuccessResponse;

public interface UserService{
    public CustomSuccessResponse<LoginUserDto> registerUser(RegisterUserDto userDto);
    public CustomSuccessResponse<LoginUserDto> loginUser(AuthDto userDto);

}
