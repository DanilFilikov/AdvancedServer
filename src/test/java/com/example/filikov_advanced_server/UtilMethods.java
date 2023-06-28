package com.example.filikov_advanced_server;

import com.example.filikov_advanced_server.dto.news_dto.GetNewsOutDto;
import com.example.filikov_advanced_server.dto.news_dto.NewsDto;
import com.example.filikov_advanced_server.dto.user_dto.PutUserDto;
import com.example.filikov_advanced_server.dto.user_dto.RegisterUserDto;
import com.example.filikov_advanced_server.entity.NewsEntity;
import com.example.filikov_advanced_server.entity.TagEntity;
import com.example.filikov_advanced_server.entity.UserEntity;
import com.example.filikov_advanced_server.mapper.NewsMapper;
import com.example.filikov_advanced_server.services.impl.FileServiceImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UtilMethods {

    public static MultipartFile file = new MockMultipartFile("test.txt",
            "test.txt",
            "text/plain",
            "test".getBytes());

    public static UserEntity user = new UserEntity().setAvatar("/home/dunice/IdeaProjects/AdvancedServer/temp/test.txt")
            .setRole("user")
            .setName("qweqwe")
            .setEmail("56fe46a@gmail.ru")
            .setId(UUID.randomUUID())
            .setPassword(getRegisterUserDto().getPassword());

    public static TagEntity tag = new TagEntity()
            .setId(1L)
            .setTitle("testich");

    public static NewsEntity newsEntity = NewsMapper.INSTANCE.newsDtoToEntity(getNewsDto())
            .setUser(user)
            .setUsername(user.getName())
            .setTags(Stream.of(tag).map(e -> new TagEntity().setTitle(getNewsDto().getTitle())).collect(Collectors.toList()));

    public static String filePath = "/home/dunice/IdeaProjects/AdvancedServer/temp/Anime.jpg";
    public static String defaultEmail = "56fe46a@gmail.ru";
    public static String defaultPassword = "123123";
    public static int defaultStatusCode = 1;
    public static int page = 0;
    public static int perPage = 100;
    public static int wantedNumberOfInvocation = 1;

    public static RegisterUserDto getRegisterUserDto(){
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setPassword("123123");
        registerUserDto.setName("qweqwe");
        registerUserDto.setRole("user");
        registerUserDto.setEmail("56fe46a@gmail.ru");
        registerUserDto.setAvatar("/home/dunice/IdeaProjects/AdvancedServer/temp/");
        return registerUserDto;
   }

   public static PutUserDto getPutUserDto(){
       PutUserDto putUserDto = new PutUserDto();
       putUserDto.setAvatar("/home/dunice/IdeaProjects/AdvancedServer/temp/");
       putUserDto.setEmail("56f123a@gmail.ru");
       putUserDto.setName("qwe");
       putUserDto.setRole("user");
        return putUserDto;
   }

   public static NewsDto getNewsDto(){
        NewsDto newsDto = new NewsDto();
        newsDto.setDescription("Test");
        newsDto.setImage("/home/dunice/IdeaProjects/AdvancedServer/temp/test.txt");
        newsDto.setTitle("WOW");
        newsDto.setTags(List.of(tag.getTitle()));
        return newsDto;
   }

   public static GetNewsOutDto getNewsOutDto(){
        GetNewsOutDto getNewsOutDto = new GetNewsOutDto();
        getNewsOutDto.setDescription("Test");
        getNewsOutDto.setTags(List.of(NewsMapper.INSTANCE.tagEntityToTag(tag)));
        getNewsOutDto.setUsername("qwe");
        getNewsOutDto.setImage("/home/dunice/IdeaProjects/AdvancedServer/temp/test.txt");
        getNewsOutDto.setTitle("WOW");
        getNewsOutDto.setUserId(user.getId());
        return getNewsOutDto;
   }

}
