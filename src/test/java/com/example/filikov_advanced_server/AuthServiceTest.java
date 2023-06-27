package com.example.filikov_advanced_server;

import com.example.filikov_advanced_server.dto.user_dto.AuthDto;
import com.example.filikov_advanced_server.dto.user_dto.LoginUserDto;
import com.example.filikov_advanced_server.dto.user_dto.RegisterUserDto;
import com.example.filikov_advanced_server.mapper.UserMapper;
import com.example.filikov_advanced_server.repository.UserRepo;
import com.example.filikov_advanced_server.responses.CustomSuccessResponse;
import com.example.filikov_advanced_server.security.jwt.JwtTokenProvider;
import com.example.filikov_advanced_server.services.AuthService;
import com.example.filikov_advanced_server.services.FileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static com.example.filikov_advanced_server.Methods.file;
import static com.example.filikov_advanced_server.Methods.user;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthServiceTest {

    @Autowired
    AuthService authService;
    @Autowired
    FileService fileService;
    @MockBean
    UserRepo userRepo;
    @MockBean
    JwtTokenProvider tokenProvider;
    @MockBean
    BCryptPasswordEncoder pas;

    @Test
    public void registerTest(){
        RegisterUserDto registerUserDto = Methods.getRegisterUserDto();

        when(userRepo.save(ArgumentMatchers.any()))
                .thenReturn(UserMapper.INSTANCE.registerDtoToEntity(registerUserDto).setId(UUID.randomUUID()));
        fileService.uploadFile(file);
        when(tokenProvider.createToken(ArgumentMatchers.any())).thenReturn("some.barrier.token");
        CustomSuccessResponse<LoginUserDto> customSuccessResponse = authService.registerUser(registerUserDto);

        verify(userRepo, times(1)).save(ArgumentMatchers.any());
        Assertions.assertNotNull(customSuccessResponse.getData().getName());
        Assertions.assertNotNull(customSuccessResponse.getData().getRole());
        Assertions.assertNotNull(customSuccessResponse.getData().getToken());
    }

    @Test
    public void loginTest(){
        registerTest();
        RegisterUserDto registerUserDto = Methods.getRegisterUserDto();
        AuthDto authDto = new AuthDto();
        authDto.setEmail("56fe46a@gmail.ru");
        authDto.setPassword("123123");

        when(userRepo.findByEmail(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(user));
        when(userRepo.existsByEmail(ArgumentMatchers.any())).thenReturn(true);
        when(userRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(user));
        when(pas.matches(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(true);
        CustomSuccessResponse<LoginUserDto> customSuccessResponse = authService.loginUser(authDto);

        Assertions.assertNotNull(customSuccessResponse.getData().getToken());
        Assertions.assertNotNull(customSuccessResponse.getData().getName());
        Assertions.assertNotNull(customSuccessResponse.getData().getRole());
        Assertions.assertNotNull(customSuccessResponse.getData().getAvatar());
        Assertions.assertNotNull(customSuccessResponse.getData().getEmail());
    }
}