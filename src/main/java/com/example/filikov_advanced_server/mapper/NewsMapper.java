package com.example.filikov_advanced_server.mapper;

import com.example.filikov_advanced_server.dto.NewsDto;
import com.example.filikov_advanced_server.entity.NewsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NewsMapper {

    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", source = "description")
    @Mapping(target = "image", source = "image")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "tags", ignore = true)
    NewsEntity NewsDtoToEntity(NewsDto newsDto);
}
