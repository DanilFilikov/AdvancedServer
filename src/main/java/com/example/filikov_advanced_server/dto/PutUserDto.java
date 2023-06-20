package com.example.filikov_advanced_server.dto;

import com.example.filikov_advanced_server.error.ValidationConstants;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PutUserDto {
    @NotBlank(message = ValidationConstants.USER_AVATAR_NOT_NULL)
    String avatar;

    @Email(message = ValidationConstants.USER_EMAIL_NOT_VALID)
    @NotBlank(message = ValidationConstants.USER_EMAIL_NOT_NULL)
    @Size(min = 3, max = 100, message = ValidationConstants.EMAIL_SIZE_NOT_VALID)
    String email;

    @Size(min = 3, max = 25, message = ValidationConstants.USERNAME_SIZE_NOT_VALID)
    @NotBlank(message = ValidationConstants.USERNAME_HAS_TO_BE_PRESENT)
    String name;

    @NotBlank(message = ValidationConstants.USER_ROLE_NOT_NULL)
    String role;
}