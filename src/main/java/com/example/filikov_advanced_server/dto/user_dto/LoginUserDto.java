package com.example.filikov_advanced_server.dto.user_dto;

import com.example.filikov_advanced_server.entity.UserEntity;
import com.example.filikov_advanced_server.mapper.UserMapper;
import lombok.Data;

import java.util.UUID;

@Data
public class LoginUserDto {

    private String avatar;
    private String email;
    private UUID id;
    private String name;
    private String role;
    private String token;

}