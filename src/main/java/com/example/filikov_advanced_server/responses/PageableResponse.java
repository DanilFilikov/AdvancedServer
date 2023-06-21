package com.example.filikov_advanced_server.responses;

import lombok.Data;

import java.util.List;

@Data
public class PageableResponse <T> {
    public T content;
    public int numberOfElements;

    public static <T> PageableResponse<T> getPageableResponse(List<T> content){
        PageableResponse<T> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent((T) content);
        pageableResponse.setNumberOfElements(content.size());
        return pageableResponse;
    }
}