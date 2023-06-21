package com.example.filikov_advanced_server.dto.user_dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PutUserDtoResponse {

    String avatar;
    String email;
    UUID id;
    String name;
    String role;

}