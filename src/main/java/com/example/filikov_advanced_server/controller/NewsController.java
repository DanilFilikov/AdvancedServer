package com.example.filikov_advanced_server.controller;

import com.example.filikov_advanced_server.dto.news_dto.NewsDto;
import com.example.filikov_advanced_server.services.NewsService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @PostMapping
    public ResponseEntity createNews(@Valid @RequestBody NewsDto newsDto, Authentication authentication){
    return ResponseEntity.ok(newsService.createNews(newsDto, authentication));
    }

    @GetMapping
    public ResponseEntity getNews(@RequestParam int page, @RequestParam int perPage){
    return ResponseEntity.ok(newsService.getNews(page, perPage));
    }
}