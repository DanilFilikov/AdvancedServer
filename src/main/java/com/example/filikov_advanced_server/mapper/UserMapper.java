package com.example.filikov_advanced_server.mapper;

import com.example.filikov_advanced_server.dto.LoginUserDto;
import com.example.filikov_advanced_server.dto.RegisterUserDto;
import com.example.filikov_advanced_server.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "avatar", source = "avatar")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    UserEntity registerDtoToEntity(RegisterUserDto dto);

    @Mapping(target = "token", ignore = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "avatar", source = "avatar")
    LoginUserDto entityToLoginUserDto(UserEntity userEntity);

}