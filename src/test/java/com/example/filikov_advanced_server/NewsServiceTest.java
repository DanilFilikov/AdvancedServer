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
import com.example.filikov_advanced_server.services.NewsService;
import com.example.filikov_advanced_server.services.impl.FileServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.filikov_advanced_server.UtilMethods.defaultStatusCode;
import static com.example.filikov_advanced_server.UtilMethods.filePath;
import static com.example.filikov_advanced_server.UtilMethods.getNewsDto;
import static com.example.filikov_advanced_server.UtilMethods.newsEntity;
import static com.example.filikov_advanced_server.UtilMethods.page;
import static com.example.filikov_advanced_server.UtilMethods.perPage;
import static com.example.filikov_advanced_server.UtilMethods.tag;
import static com.example.filikov_advanced_server.UtilMethods.user;
import static com.example.filikov_advanced_server.UtilMethods.wantedNumberOfInvocation;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class NewsServiceTest {

    @Autowired
    private NewsService newsService;
    @MockBean
    private UserRepo userRepo;
    @MockBean
    private TagsRepo tagsRepo;
    @MockBean
    private NewsRepo newsRepo;

    @Test
    public void createNewsTest(){
        NewsDto newsDto = UtilMethods.getNewsDto();
        when(userRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(user.setId(UUID.randomUUID())));
        when(newsRepo.save(ArgumentMatchers.any())).thenReturn(newsEntity);
        FileServiceImpl.filePath = filePath;
        CreateNewsSuccessResponse response = newsService.createNews(newsDto, UUID.randomUUID());

        verify(newsRepo, times(wantedNumberOfInvocation)).save(ArgumentMatchers.any());
        verify(tagsRepo, times(wantedNumberOfInvocation)).saveAll(ArgumentMatchers.any());
        Assertions.assertEquals(response.getStatusCode(), defaultStatusCode);
        Assertions.assertEquals(response.getId(), newsEntity.getId());
    }

    @Test
    public void getNewsTest(){

        when(newsRepo.findAll(PageRequest.of(page, perPage))).thenReturn(Page.empty().map(e -> newsEntity));

        CustomSuccessResponse<PageableResponse<GetNewsOutDto>> response = newsService.getNews(page , perPage);

        Assertions.assertNotNull(response.getData());
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertEquals(response.getStatusCode(), defaultStatusCode);
    }

    @Test
    public void getUserNewsTest(){
        when(newsRepo.findAll(PageRequest.of(page, perPage))).thenReturn(Page.empty().map(e -> newsEntity));
        when(userRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(user));

        CustomSuccessResponse<PageableResponse<GetNewsOutDto>> response = newsService.getUserNews(page, perPage, user.getId());

        Assertions.assertNotNull(response.getData());
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertEquals(response.getStatusCode(), defaultStatusCode);
    }

    @Test
    public void findNewsTest(){
        when(newsRepo.findAll(PageRequest.of(page, perPage))).thenReturn(Page.empty().map(e -> newsEntity));

        PageableResponse<GetNewsOutDto> response = newsService.findNews(page, perPage, "author",
                "test",
                Stream.of(tag).map(TagEntity::getTitle).collect(Collectors.toList()));

        Assertions.assertNotNull(response.getContent());
    }

    @Test
    public void putNewsTest(){
        when(newsRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(newsEntity));

        FileServiceImpl.filePath = filePath;
        BaseSuccessResponse response = newsService.putNews(newsEntity.getId(), getNewsDto());

        verify(newsRepo, times(wantedNumberOfInvocation)).save(ArgumentMatchers.any());
        verify(tagsRepo, times(wantedNumberOfInvocation)).saveAll(ArgumentMatchers.any());
        verify(tagsRepo, times(wantedNumberOfInvocation)).deleteAll(ArgumentMatchers.any());
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertEquals(response.getStatusCode(), defaultStatusCode);
    }

    @Test
    public void deleteNewsTest(){
        when(newsRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(newsEntity));

        BaseSuccessResponse response = newsService.deleteNews(newsEntity.getId());

        verify(tagsRepo, times(wantedNumberOfInvocation)).deleteAll(ArgumentMatchers.any());
        verify(newsRepo, times(wantedNumberOfInvocation)).delete(ArgumentMatchers.any());
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertEquals(response.getStatusCode(), defaultStatusCode);
    }
}