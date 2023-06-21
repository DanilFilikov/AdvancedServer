package com.example.filikov_advanced_server.services;

import com.example.filikov_advanced_server.dto.user_dto.PublicUserView;
import com.example.filikov_advanced_server.dto.user_dto.PutUserDto;
import com.example.filikov_advanced_server.dto.user_dto.PutUserDtoResponse;
import com.example.filikov_advanced_server.responses.BaseSuccessResponse;
import com.example.filikov_advanced_server.responses.CustomSuccessResponse;

import java.util.List;
import java.util.UUID;

public interface UserService{
  
    public CustomSuccessResponse<List<PublicUserView>> getUsers();
    public CustomSuccessResponse<PublicUserView> getUserInfoById(UUID id);
    public CustomSuccessResponse<PublicUserView> getUserInfo(UUID id);
    public BaseSuccessResponse deleteUser(UUID id);
    public CustomSuccessResponse<PutUserDtoResponse> replaceUser(PutUserDto putUserDto, UUID id);

}
