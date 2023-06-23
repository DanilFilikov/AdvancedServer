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
import com.example.filikov_advanced_server.responses.BaseSuccessResponse;
import com.example.filikov_advanced_server.responses.CreateNewsSuccessResponse;
import com.example.filikov_advanced_server.responses.CustomSuccessResponse;
import com.example.filikov_advanced_server.responses.PageableResponse;
import com.example.filikov_advanced_server.services.FileService;
import com.example.filikov_advanced_server.services.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
        if(FileServiceImpl.filePath == null){
            throw new CustomException(ValidationConstants.NEWS_IMAGE_HAS_TO_BE_PRESENT);
        }
        NewsEntity newsEntity = NewsMapper.INSTANCE.newsDtoToEntity(newsDto);
        List<TagEntity> tagEntityList = newsDto.getTags()
                .stream()
                .map(tag -> new TagEntity().setTitle(tag))
                .collect(Collectors.toList());
        newsEntity.setImage(FileServiceImpl.filePath);
        newsEntity.setTags(tagEntityList);
        newsEntity.setUser(userRepo.findById(id).get());
        newsEntity.setUsername(userRepo.findById(id).get().getName());
        tagsRepo.saveAll(tagEntityList);
        newsRepo.save(newsEntity);
        FileServiceImpl.filePath = null;
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
                .filter(newsEntity -> author == null || newsEntity.getUsername().equals(author))
                .filter(newsEntity -> tags == null || new HashSet<>(tagsRepo.findAll().stream()
                        .map(TagEntity::getTitle)
                        .toList()).containsAll(tags))
                .filter(newsEntity -> keywords == null || newsEntity.getDescription().contains(keywords))
                .map(newsEntity -> NewsMapper.INSTANCE.newsEntityToGetNewsOutDto(newsEntity)
                        .setUserId(newsEntity.getUser().getId())
                        .setTags(newsEntity.getTags().stream().map(NewsMapper.INSTANCE::tagEntityToTag).toList())).toList();

        return PageableResponse.getPageableResponse(getNewsOutDtoList).setNumberOfElements(getNewsOutDtoList.size());
    }

    @Override
    public BaseSuccessResponse putNews(Long id, NewsDto newsDto){
        if(FileServiceImpl.filePath == null){
            throw new CustomException(ValidationConstants.NEWS_IMAGE_HAS_TO_BE_PRESENT);
        }
        tagsRepo.deleteAll(newsRepo.findById(id).get().getTags());
        NewsEntity newsEntity = newsRepo.findById(id).orElseThrow(() -> new CustomException(ValidationConstants.USER_NOT_FOUND))
               .setTags(newsDto.getTags()
                       .stream()
                       .map(tag -> new TagEntity().setTitle(tag))
                       .collect(Collectors.toList()))
               .setDescription(newsDto.getDescription())
               .setTitle(newsDto.getTitle())
               .setImage(FileServiceImpl.filePath);
        FileServiceImpl.filePath = null;
        tagsRepo.saveAll(newsEntity.getTags());
        newsRepo.save(newsEntity);
        return BaseSuccessResponse.getSuccessResponse();
    }

    @Override
    public BaseSuccessResponse deleteNews(Long id){
        NewsEntity newsEntity = newsRepo.findById(id).orElseThrow(() -> new CustomException(ValidationConstants.NEWS_NOT_FOUND));
        tagsRepo.deleteAll(newsEntity.getTags());
        newsRepo.delete(newsEntity);
        return BaseSuccessResponse.getSuccessResponse();
    }
}