package com.example.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class CustomSuccessResponse<T> {

    private T data;
    private int statusCode;
    private boolean success;
    private Integer code;
    private List<Integer> codes;

    public static <T> CustomSuccessResponse<T> getSuccessResponse(T data){
        CustomSuccessResponse<T> successResponse = new CustomSuccessResponse<>();
        successResponse.setData(data);
        successResponse.setStatusCode(1);
        successResponse.setSuccess(true);
        return successResponse;
    }

    public static <T> CustomSuccessResponse<T> getErrorResponse(List<Integer> codes, Integer code){
        CustomSuccessResponse<T> successResponse = new CustomSuccessResponse<>();
        successResponse.setCodes(codes);
        successResponse.setCode(code);
        successResponse.setStatusCode(code);
        successResponse.setSuccess(true);
        return successResponse;
    }
}