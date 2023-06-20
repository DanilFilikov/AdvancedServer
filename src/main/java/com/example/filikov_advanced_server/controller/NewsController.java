package com.example.filikov_advanced_server.controller;

import com.example.filikov_advanced_server.dto.NewsDto;
import com.example.filikov_advanced_server.services.NewsService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @PostMapping
    public ResponseEntity createNews(Authentication authentication, @Valid @RequestBody NewsDto newsDto){
    return ResponseEntity.ok(newsService.createNews(newsDto));
    }
}