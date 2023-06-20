package com.example.filikov_advanced_server.services.impl;

import com.example.filikov_advanced_server.dto.PublicUserView;
import com.example.filikov_advanced_server.dto.PutUserDto;
import com.example.filikov_advanced_server.dto.PutUserDtoResponse;
import com.example.filikov_advanced_server.entity.UserEntity;
import com.example.filikov_advanced_server.error.ValidationConstants;
import com.example.filikov_advanced_server.exception.CustomException;
import com.example.filikov_advanced_server.mapper.UserMapper;
import com.example.filikov_advanced_server.responses.BaseSuccessResponse;
import com.example.filikov_advanced_server.responses.CustomSuccessResponse;
import com.example.filikov_advanced_server.repository.UserRepo;
import com.example.filikov_advanced_server.services.UserService;
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
    public CustomSuccessResponse<PublicUserView> getUserInfo(UUID id){
        PublicUserView response = UserMapper.INSTANCE.entityToPublicUserView(userRepo.findById(id).get());
        return CustomSuccessResponse.getSuccessResponse(response);
    }

    @Override
    public BaseSuccessResponse deleteUser(UUID id){
        userRepo.deleteById(id);
        return BaseSuccessResponse.getSuccessResponse();
    }

    @Override
    public CustomSuccessResponse<PutUserDtoResponse> replaceUser(PutUserDto putUserDto, UUID id){
        UserEntity userEntity = userRepo.findById(id).orElseThrow(() -> new CustomException(ValidationConstants.USER_NOT_FOUND));
        userEntity.setAvatar(putUserDto.getAvatar());
        userEntity.setRole(putUserDto.getRole());
        userEntity.setName(putUserDto.getName());
        if(userRepo.existsByEmail(putUserDto.getEmail())){
            throw new CustomException(ValidationConstants.USER_ALREADY_EXISTS);
        }
        userEntity.setEmail(putUserDto.getEmail());
        userRepo.save(userEntity);
        return CustomSuccessResponse.getSuccessResponse(UserMapper.INSTANCE.entityToPutUserDtoResponse(userEntity));
    }
}