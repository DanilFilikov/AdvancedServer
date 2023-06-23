package com.example.filikov_advanced_server.mapper;

import com.example.filikov_advanced_server.dto.news_dto.GetNewsOutDto;
import com.example.filikov_advanced_server.dto.news_dto.NewsDto;
import com.example.filikov_advanced_server.dto.news_dto.Tag;
import com.example.filikov_advanced_server.entity.NewsEntity;
import com.example.filikov_advanced_server.entity.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NewsMapper {

    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", source = "description")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "tags", ignore = true)
    NewsEntity newsDtoToEntity(NewsDto newsDto);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "title", source = "title")
    GetNewsOutDto newsEntityToGetNewsOutDto(NewsEntity newsEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    Tag tagEntityToTag(TagEntity tagEntity);
}
