package com.example.filikov_advanced_server.services;

import com.example.filikov_advanced_server.dto.news_dto.GetNewsOutDto;
import com.example.filikov_advanced_server.dto.news_dto.NewsDto;
import com.example.filikov_advanced_server.responses.CreateNewsSuccessResponse;
import com.example.filikov_advanced_server.responses.CustomSuccessResponse;
import com.example.filikov_advanced_server.responses.PageableResponse;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface NewsService {
    public CreateNewsSuccessResponse createNews(NewsDto newsDto, UUID id);
    public CustomSuccessResponse<PageableResponse<GetNewsOutDto>> getNews(int page, int perPage);
    public CustomSuccessResponse<PageableResponse<GetNewsOutDto>> getUserNews(int page, int perPage, UUID id);
}
