package com.example.filikov_advanced_server.services.impl;

import com.example.filikov_advanced_server.dto.NewsDto;
import com.example.filikov_advanced_server.entity.NewsEntity;
import com.example.filikov_advanced_server.entity.TagEntity;
import com.example.filikov_advanced_server.mapper.NewsMapper;
import com.example.filikov_advanced_server.repository.NewsRepo;
import com.example.filikov_advanced_server.repository.TagsRepo;
import com.example.filikov_advanced_server.responses.CreateNewsSuccessResponse;
import com.example.filikov_advanced_server.services.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepo newsRepo;
    private final TagsRepo tagsRepo;

    @Override
    public CreateNewsSuccessResponse createNews(NewsDto newsDto) {
        NewsEntity newsEntity = NewsMapper.INSTANCE.NewsDtoToEntity(newsDto);
        List<TagEntity> tagEntityList = newsDto.getTags()
                .stream()
                .map(tag -> new TagEntity().setTitle(tag))
                .collect(Collectors.toList());
        newsEntity.setTags(tagEntityList);
        tagsRepo.saveAll(tagEntityList);
        newsRepo.save(newsEntity);
        return CreateNewsSuccessResponse.getSuccessResponse(newsEntity);
    }
}