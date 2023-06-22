package com.example.filikov_advanced_server.controller;

import com.example.filikov_advanced_server.dto.news_dto.NewsDto;
import com.example.filikov_advanced_server.services.NewsService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @PostMapping
    public ResponseEntity createNews(@Valid @RequestBody NewsDto newsDto, Authentication authentication){
        String id = authentication.getName();
    return ResponseEntity.ok(newsService.createNews(newsDto, UUID.fromString(id)));
    }

    @GetMapping
    public ResponseEntity getNews(@RequestParam int page, @RequestParam int perPage){
    return ResponseEntity.ok(newsService.getNews(page, perPage));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getUserNews(@RequestParam int page, @RequestParam int perPage, @PathVariable UUID userId){
        return ResponseEntity.ok(newsService.getUserNews(page, perPage, userId));
    }

    @GetMapping("/find")
    public ResponseEntity findNews(@RequestParam int page, @RequestParam int perPage, @RequestParam(required = false) String author, @RequestParam(required = false) String keyword, @RequestParam(required = false) List<String> tags){
        return ResponseEntity.ok(newsService.findNews(page, perPage, author, keyword, tags));
    }
}