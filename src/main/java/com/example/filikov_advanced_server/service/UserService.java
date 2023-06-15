package com.example.filikov_advanced_server.service;

import com.example.filikov_advanced_server.dto.AuthDto;
import com.example.filikov_advanced_server.dto.LoginUserDto;
import com.example.filikov_advanced_server.dto.PublicUserView;
import com.example.filikov_advanced_server.dto.RegisterUserDto;
import com.example.filikov_advanced_server.responses.CustomSuccessResponse;

import java.util.List;

public interface UserService{
    public CustomSuccessResponse<LoginUserDto> registerUser(RegisterUserDto userDto);
    public CustomSuccessResponse<LoginUserDto> loginUser(AuthDto userDto);
    public CustomSuccessResponse<List<PublicUserView>> getUsers();
}
