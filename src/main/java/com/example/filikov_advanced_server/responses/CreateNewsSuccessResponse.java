package com.example.filikov_advanced_server.responses;

import com.example.filikov_advanced_server.entity.NewsEntity;
import lombok.Data;

@Data
public class CreateNewsSuccessResponse {

    public Long id;
    public int statusCode;
    public boolean success;

    public static CreateNewsSuccessResponse getSuccessResponse(NewsEntity newsEntity){
        CreateNewsSuccessResponse successResponse = new CreateNewsSuccessResponse();
        successResponse.setId(newsEntity.getId());
        successResponse.setStatusCode(1);
        successResponse.setSuccess(true);
        return successResponse;
    }
}