package com.example.filikov_advanced_server;

import com.example.filikov_advanced_server.dto.user_dto.AuthDto;
import com.example.filikov_advanced_server.dto.user_dto.LoginUserDto;
import com.example.filikov_advanced_server.dto.user_dto.RegisterUserDto;
import com.example.filikov_advanced_server.mapper.UserMapper;
import com.example.filikov_advanced_server.repository.UserRepo;
import com.example.filikov_advanced_server.responses.CustomSuccessResponse;
import com.example.filikov_advanced_server.security.jwt.JwtTokenProvider;
import com.example.filikov_advanced_server.services.AuthService;
import com.example.filikov_advanced_server.services.impl.FileServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static com.example.filikov_advanced_server.UtilMethods.defaultEmail;
import static com.example.filikov_advanced_server.UtilMethods.defaultPassword;
import static com.example.filikov_advanced_server.UtilMethods.filePath;
import static com.example.filikov_advanced_server.UtilMethods.user;
import static com.example.filikov_advanced_server.UtilMethods.wantedNumberOfInvocation;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {

    @Autowired
    private AuthService authService;
    @MockBean
    private UserRepo userRepo;
    @MockBean
    private JwtTokenProvider tokenProvider;
    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void registerTest(){
        RegisterUserDto registerUserDto = UtilMethods.getRegisterUserDto();
        when(userRepo.save(ArgumentMatchers.any()))
                .thenReturn(UserMapper.INSTANCE.registerDtoToEntity(registerUserDto).setId(UUID.randomUUID()));
        when(tokenProvider.createToken(ArgumentMatchers.any())).thenReturn("some.barrier.token");
        FileServiceImpl.filePath = filePath;
        CustomSuccessResponse<LoginUserDto> customSuccessResponse = authService.registerUser(registerUserDto);

        verify(userRepo, times(wantedNumberOfInvocation)).save(ArgumentMatchers.any());
        Assertions.assertNotNull(customSuccessResponse.getData().getName());
        Assertions.assertNotNull(customSuccessResponse.getData().getRole());
        Assertions.assertNotNull(customSuccessResponse.getData().getToken());
    }

    @Test
    public void loginTest(){

        AuthDto authDto = new AuthDto();
        authDto.setEmail(defaultEmail);
        authDto.setPassword(defaultPassword);

        when(userRepo.findByEmail(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(user));
        when(userRepo.existsByEmail(ArgumentMatchers.any())).thenReturn(true);
        when(userRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(user));
        when(passwordEncoder.matches(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(true);
        when(tokenProvider.createToken(ArgumentMatchers.any())).thenReturn("some.barrier.token");

        CustomSuccessResponse<LoginUserDto> customSuccessResponse = authService.loginUser(authDto);

        Assertions.assertNotNull(customSuccessResponse.getData().getToken());
        Assertions.assertNotNull(customSuccessResponse.getData().getName());
        Assertions.assertNotNull(customSuccessResponse.getData().getRole());
        Assertions.assertNotNull(customSuccessResponse.getData().getAvatar());
        Assertions.assertNotNull(customSuccessResponse.getData().getEmail());
    }
}