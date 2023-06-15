package com.example.filikov_advanced_server.service;

import com.example.filikov_advanced_server.dto.PublicUserView;
import com.example.filikov_advanced_server.responses.CustomSuccessResponse;

import java.util.List;

public interface UserService{
    public CustomSuccessResponse<List<PublicUserView>> getUsers();
}
