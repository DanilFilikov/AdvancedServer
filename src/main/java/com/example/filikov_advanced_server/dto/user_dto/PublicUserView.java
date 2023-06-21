package com.example.filikov_advanced_server.dto.user_dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PublicUserView {

    private String avatar;
    private String email;
    private UUID id;
    private String name;
    private String role;

}