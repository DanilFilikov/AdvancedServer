package com.example.demo.dto;

import com.example.demo.entity.UserEntity;
import com.example.demo.error.ValidationConstants;
import lombok.Data;

import javax.validation.constraints.Email;
import java.util.UUID;

@Data
public class LoginUserDto {

    private String avatar;

    @Email(message = ValidationConstants.USER_EMAIL_NOT_VALID)
    private String email;

    private UUID id;
    private String name;
    private String role;
    private String token;

    public static LoginUserDto mapToDto(UserEntity user, String token){
        LoginUserDto userDto = new LoginUserDto();
        userDto.setId(user.getId());
        userDto.setAvatar(user.getAvatar());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setRole(user.getRole());
        userDto.setToken(token);
        return userDto;
    }
}