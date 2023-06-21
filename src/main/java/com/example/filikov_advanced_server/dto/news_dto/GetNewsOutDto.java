package com.example.filikov_advanced_server.dto.news_dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class GetNewsOutDto {

    String description;
    Long id;
    String image;
    List<Tag> tags;
    String title;
    UUID userId;
    String username;

}