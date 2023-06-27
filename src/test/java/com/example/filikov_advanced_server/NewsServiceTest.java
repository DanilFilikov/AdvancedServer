package com.example.filikov_advanced_server;

import com.example.filikov_advanced_server.dto.news_dto.GetNewsOutDto;
import com.example.filikov_advanced_server.dto.news_dto.NewsDto;
import com.example.filikov_advanced_server.entity.TagEntity;
import com.example.filikov_advanced_server.repository.NewsRepo;
import com.example.filikov_advanced_server.repository.TagsRepo;
import com.example.filikov_advanced_server.repository.UserRepo;
import com.example.filikov_advanced_server.responses.BaseSuccessResponse;
import com.example.filikov_advanced_server.responses.CreateNewsSuccessResponse;
import com.example.filikov_advanced_server.responses.CustomSuccessResponse;
import com.example.filikov_advanced_server.responses.PageableResponse;
import com.example.filikov_advanced_server.services.FileService;
import com.example.filikov_advanced_server.services.NewsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.filikov_advanced_server.Methods.file;
import static com.example.filikov_advanced_server.Methods.getNewsDto;
import static com.example.filikov_advanced_server.Methods.newsEntity;
import static com.example.filikov_advanced_server.Methods.tag;
import static com.example.filikov_advanced_server.Methods.user;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NewsServiceTest {

    @Autowired
    NewsService newsService;
    @Autowired
    FileService fileService;
    @MockBean
    UserRepo userRepo;
    @MockBean
    TagsRepo tagsRepo;
    @MockBean
    NewsRepo newsRepo;

    @Test
    public void createNewsTest(){
        NewsDto newsDto = Methods.getNewsDto();
        when(userRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(user.setId(UUID.randomUUID())));
        fileService.uploadFile(file);
        when(newsRepo.save(ArgumentMatchers.any())).thenReturn(newsEntity);

        CreateNewsSuccessResponse response = newsService.createNews(newsDto, UUID.randomUUID());

        verify(newsRepo, times(1)).save(ArgumentMatchers.any());
        verify(tagsRepo, times(1)).saveAll(ArgumentMatchers.any());
        Assertions.assertEquals(response.getStatusCode(), 1);
        Assertions.assertEquals(response.getId(), newsEntity.getId());
    }

    @Test
    public void getNewsTest(){

        when(newsRepo.findAll(PageRequest.of(0, 100))).thenReturn(Page.empty().map(e -> newsEntity));

        CustomSuccessResponse<PageableResponse<GetNewsOutDto>> response = newsService.getNews(0 , 100);

        Assertions.assertNotNull(response.getData());
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertEquals(response.getStatusCode(), 1);
    }

    @Test
    public void getUserNewsTest(){
        when(newsRepo.findAll(PageRequest.of(0, 100))).thenReturn(Page.empty().map(e -> newsEntity));
        when(userRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(user));

        CustomSuccessResponse<PageableResponse<GetNewsOutDto>> response = newsService.getUserNews(0, 100, user.getId());

        Assertions.assertNotNull(response.getData());
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertEquals(response.getStatusCode(), 1);
    }

    @Test
    public void findNewsTest(){
        when(newsRepo.findAll(PageRequest.of(0, 100))).thenReturn(Page.empty().map(e -> newsEntity));

        PageableResponse<GetNewsOutDto> response = newsService.findNews(0, 100, "author",
                "test",
                Stream.of(tag).map(TagEntity::getTitle).collect(Collectors.toList()));

        Assertions.assertNotNull(response.getContent());
    }

    @Test
    public void putNewsTest(){
        when(newsRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(newsEntity));

        fileService.uploadFile(file);
        BaseSuccessResponse response = newsService.putNews(newsEntity.getId(), getNewsDto());

        verify(newsRepo, times(1)).save(ArgumentMatchers.any());
        verify(tagsRepo, times(1)).saveAll(ArgumentMatchers.any());
        verify(tagsRepo, times(1)).deleteAll(ArgumentMatchers.any());
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertEquals(response.getStatusCode(), 1);
    }

    @Test
    public void deleteNews(){
        when(newsRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(newsEntity));

        BaseSuccessResponse response = newsService.deleteNews(newsEntity.getId());

        verify(tagsRepo, times(1)).deleteAll(ArgumentMatchers.any());
        verify(newsRepo, times(1)).delete(ArgumentMatchers.any());
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertEquals(response.getStatusCode(), 1);
    }
}