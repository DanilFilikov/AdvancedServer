package com.example.filikov_advanced_server.service.impl;

import com.example.filikov_advanced_server.dto.PublicUserView;
import com.example.filikov_advanced_server.error.ValidationConstants;
import com.example.filikov_advanced_server.exception.CustomException;
import com.example.filikov_advanced_server.mapper.UserMapper;
import com.example.filikov_advanced_server.responses.CustomSuccessResponse;
import com.example.filikov_advanced_server.repository.UserRepo;
import com.example.filikov_advanced_server.service.UserService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public CustomSuccessResponse<List<PublicUserView>> getUsers(){
        List<PublicUserView> users = userRepo.findAll().stream()
                .map(UserMapper.INSTANCE::entityToPublicUserView)
                .toList();
        return CustomSuccessResponse.getSuccessResponse(users);
    }

    @Override
    public CustomSuccessResponse<PublicUserView> getUserInfoById(UUID id) {
        PublicUserView response = UserMapper.INSTANCE.entityToPublicUserView(userRepo.findById(id)
                .orElseThrow(() -> new CustomException(ValidationConstants.USER_NOT_FOUND)));
        return CustomSuccessResponse.getSuccessResponse(response);
    }

    @Override
    public CustomSuccessResponse<PublicUserView> getUserInfo(String email){
        PublicUserView response = UserMapper.INSTANCE.entityToPublicUserView(userRepo.findByEmail(email).get());
        return CustomSuccessResponse.getSuccessResponse(response);
    }
}