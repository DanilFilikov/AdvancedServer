package com.example.filikov_advanced_server.dto.news_dto;

import com.example.filikov_advanced_server.entity.TagEntity;
import com.example.filikov_advanced_server.error.ValidationConstants;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Validated
@Data
@Accessors(chain = true)
public class NewsDto {
    @NotBlank(message = ValidationConstants.NEWS_DESCRIPTION_HAS_TO_BE_PRESENT)
    @Size(min = 3, max = 160, message = ValidationConstants.NEWS_DESCRIPTION_SIZE_NOT_VALID)
    String description;

    @Size(min = 3, max = 130, message = ValidationConstants.NEWS_IMAGE_HAS_TO_BE_PRESENT)
    String image;

    List<@NotEmpty(message = ValidationConstants.TAGS_NOT_VALID) String> tags;

    @Size(min = 3, max = 160, message = ValidationConstants.NEWS_TITLE_SIZE)
    String title;

}