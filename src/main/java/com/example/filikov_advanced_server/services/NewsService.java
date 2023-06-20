package com.example.filikov_advanced_server.services;

import com.example.filikov_advanced_server.dto.NewsDto;
import com.example.filikov_advanced_server.responses.CreateNewsSuccessResponse;
import com.example.filikov_advanced_server.responses.CustomSuccessResponse;

public interface NewsService {
    public CreateNewsSuccessResponse createNews(NewsDto newsDto);
}
