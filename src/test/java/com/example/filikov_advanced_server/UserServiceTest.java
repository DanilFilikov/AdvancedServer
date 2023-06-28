package com.example.filikov_advanced_server;

import com.example.filikov_advanced_server.dto.user_dto.PublicUserView;
import com.example.filikov_advanced_server.dto.user_dto.PutUserDtoResponse;
import com.example.filikov_advanced_server.repository.UserRepo;
import com.example.filikov_advanced_server.responses.BaseSuccessResponse;
import com.example.filikov_advanced_server.responses.CustomSuccessResponse;
import com.example.filikov_advanced_server.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static com.example.filikov_advanced_server.UtilMethods.defaultStatusCode;
import static com.example.filikov_advanced_server.UtilMethods.getPutUserDto;
import static com.example.filikov_advanced_server.UtilMethods.user;
import static com.example.filikov_advanced_server.UtilMethods.wantedNumberOfInvocation;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @MockBean
    private UserRepo userRepo;
    @Autowired
    private UserService userService;

    @Test
    public void getUserTest(){

        when(userRepo.findAll()).thenReturn(List.of(user));

        CustomSuccessResponse<List<PublicUserView>> response = userService.getUsers();

        verify(userRepo, times(wantedNumberOfInvocation)).findAll();
        Assertions.assertNotNull(response.getData());
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertEquals(response.getStatusCode(), defaultStatusCode);

    }

    @Test
    public void getUserInfoByIdTest(){
        when(userRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(user));

        CustomSuccessResponse<PublicUserView> response = userService.getUserInfoById(user.getId());

        verify(userRepo, times(wantedNumberOfInvocation)).findById(ArgumentMatchers.any());
        Assertions.assertNotNull(response.getData());
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertEquals(response.getStatusCode(), defaultStatusCode);
    }

    @Test
    public void getUserInfoTest(){
        when(userRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(user));

        CustomSuccessResponse<PublicUserView> response = userService.getUserInfoById(user.getId());

        verify(userRepo, times(wantedNumberOfInvocation)).findById(ArgumentMatchers.any());
        Assertions.assertNotNull(response.getData());
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertEquals(response.getStatusCode(), defaultStatusCode);
    }

    @Test
    public void deleteUserTest(){
        BaseSuccessResponse response = userService.deleteUser(user.getId());

        verify(userRepo, times(wantedNumberOfInvocation)).deleteById(ArgumentMatchers.any());
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertEquals(response.getStatusCode(), defaultStatusCode);
    }

    @Test
    public void replaceUserTest(){
        when(userRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(user));
        when(userRepo.existsByEmail(ArgumentMatchers.any())).thenReturn(false);

        CustomSuccessResponse<PutUserDtoResponse> response = userService.replaceUser(getPutUserDto(), user.getId());

        verify(userRepo, times(wantedNumberOfInvocation)).save(user);
        Assertions.assertNotNull(response.getData());
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertEquals(response.getStatusCode(), defaultStatusCode);
    }
}