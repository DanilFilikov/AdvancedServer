package com.example.filikov_advanced_server.service;

import com.example.filikov_advanced_server.dto.PublicUserView;
import com.example.filikov_advanced_server.responses.CustomSuccessResponse;

import java.util.List;
import java.util.UUID;

public interface UserService{
  
    public CustomSuccessResponse<List<PublicUserView>> getUsers();
    public CustomSuccessResponse<PublicUserView> getUserInfoById(UUID id);
    public CustomSuccessResponse<PublicUserView> getUserInfo(String email);

}
