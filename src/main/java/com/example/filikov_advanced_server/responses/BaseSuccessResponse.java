package com.example.filikov_advanced_server.responses;

import lombok.Data;

@Data
public class BaseSuccessResponse {
    private int statusCode;
    private boolean success;

    public static BaseSuccessResponse getSuccessResponse() {
        BaseSuccessResponse baseSuccessResponse = new BaseSuccessResponse();
        baseSuccessResponse.setSuccess(true);
        baseSuccessResponse.setStatusCode(1);
        return baseSuccessResponse;
    }
}