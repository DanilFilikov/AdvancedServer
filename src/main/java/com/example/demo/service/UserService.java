package com.example.demo.service;

import com.example.demo.dto.LoginUserDto;
import com.example.demo.dto.RegisterUserDto;
import com.example.demo.model.CustomSuccessResponse;

public interface UserService{
    public CustomSuccessResponse<LoginUserDto> registerUser(RegisterUserDto userDto);
}
