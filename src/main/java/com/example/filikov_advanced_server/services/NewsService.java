package com.example.filikov_advanced_server.services;

import com.example.filikov_advanced_server.dto.news_dto.GetNewsOutDto;
import com.example.filikov_advanced_server.dto.news_dto.NewsDto;
import com.example.filikov_advanced_server.responses.CreateNewsSuccessResponse;
import com.example.filikov_advanced_server.responses.CustomSuccessResponse;
import com.example.filikov_advanced_server.responses.PageableResponse;
import org.springframework.security.core.Authentication;

public interface NewsService {
    public CreateNewsSuccessResponse createNews(NewsDto newsDto, Authentication authentication);
    public CustomSuccessResponse<PageableResponse<GetNewsOutDto>> getNews(int page, int perPage);
}
