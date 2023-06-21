package com.example.filikov_advanced_server.responses;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collection;

@Data
@Accessors(chain = true)
public class PageableResponse <T> {
    private Collection <T> content;
    private int numberOfElements;

    public static <T> PageableResponse<T> getPageableResponse(Collection <T> content){
        PageableResponse<T> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(content);
        return pageableResponse;
    }
}