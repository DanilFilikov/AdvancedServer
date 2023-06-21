package com.example.filikov_advanced_server.services.impl;

import com.example.filikov_advanced_server.dto.news_dto.GetNewsOutDto;
import com.example.filikov_advanced_server.dto.news_dto.NewsDto;
import com.example.filikov_advanced_server.entity.NewsEntity;
import com.example.filikov_advanced_server.entity.TagEntity;
import com.example.filikov_advanced_server.error.ValidationConstants;
import com.example.filikov_advanced_server.exception.CustomException;
import com.example.filikov_advanced_server.mapper.NewsMapper;
import com.example.filikov_advanced_server.repository.NewsRepo;
import com.example.filikov_advanced_server.repository.TagsRepo;
import com.example.filikov_advanced_server.repository.UserRepo;
import com.example.filikov_advanced_server.responses.CreateNewsSuccessResponse;
import com.example.filikov_advanced_server.responses.CustomSuccessResponse;
import com.example.filikov_advanced_server.responses.PageableResponse;
import com.example.filikov_advanced_server.services.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepo newsRepo;
    private final TagsRepo tagsRepo;
    private final UserRepo userRepo;


    @Override
    public CreateNewsSuccessResponse createNews(NewsDto newsDto, UUID id) {
        NewsEntity newsEntity = NewsMapper.INSTANCE.newsDtoToEntity(newsDto);
        List<TagEntity> tagEntityList = newsDto.getTags()
                .stream()
                .map(tag -> new TagEntity().setTitle(tag))
                .collect(Collectors.toList());
        newsEntity.setTags(tagEntityList);
        newsEntity.setUser(userRepo.findById(id).get());
        newsEntity.setUsername(userRepo.findById(id).get().getName());
        tagsRepo.saveAll(tagEntityList);
        newsRepo.save(newsEntity);
        return CreateNewsSuccessResponse.getSuccessResponse(newsEntity);
    }

    @Override
    public CustomSuccessResponse<PageableResponse<GetNewsOutDto>> getNews(int page, int perPage){
        List<GetNewsOutDto> list = newsRepo
                .findAll(PageRequest.of(page, perPage))
                .getContent()
                .stream().map(newsEntity -> NewsMapper.INSTANCE.newsEntityToGetNewsOutDto(newsEntity)
                        .setUserId(newsEntity.getUser().getId())
                        .setTags(newsEntity.getTags()
                                .stream()
                                .map(NewsMapper.INSTANCE::tagEntityToTag)
                                .toList()))
                .toList();
        return CustomSuccessResponse.getSuccessResponse(
                PageableResponse.getPageableResponse(list)
                .setNumberOfElements(list.size()));
    }

    @Override
    public CustomSuccessResponse<PageableResponse<GetNewsOutDto>> getUserNews(int page, int perPage, UUID id){
        List<GetNewsOutDto> getNewsOutDtoList = newsRepo
                .findAll(PageRequest.of(page, perPage))
                .getContent()
                .stream()
                .filter(newsEntity -> newsEntity.getUser().equals(userRepo.findById(id)
                        .orElseThrow(() -> new CustomException(ValidationConstants.USER_NOT_FOUND))))
                .map(newsEntity -> NewsMapper.INSTANCE.newsEntityToGetNewsOutDto(newsEntity)
                        .setUserId(newsEntity.getUser().getId())
                        .setTags(newsEntity.getTags()
                                .stream().map(NewsMapper.INSTANCE::tagEntityToTag).toList())).toList();
        return CustomSuccessResponse.getSuccessResponse(
                PageableResponse.getPageableResponse(getNewsOutDtoList)
                        .setNumberOfElements(getNewsOutDtoList.size()));
    }

    @Override
    public PageableResponse<GetNewsOutDto> findNews(int page, int perPage, String author, String keywords, List<String> tags){
        List<GetNewsOutDto> getNewsOutDtoList = newsRepo
                .findAll(PageRequest.of(page, perPage))
                .getContent()
                .stream()
                .filter(newsEntity -> newsEntity.getUsername().equals(author)
                        & new HashSet<>(tagsRepo.findAll().stream().map(TagEntity::getTitle).toList()).containsAll(tags)
                        & newsEntity.getDescription().contains(keywords))
                .map(newsEntity -> NewsMapper.INSTANCE.newsEntityToGetNewsOutDto(newsEntity)
                        .setUserId(newsEntity.getUser().getId())
                        .setTags(newsEntity.getTags().stream().map(NewsMapper.INSTANCE::tagEntityToTag).toList())).toList();

        return PageableResponse.getPageableResponse(getNewsOutDtoList).setNumberOfElements(getNewsOutDtoList.size());
    }
}