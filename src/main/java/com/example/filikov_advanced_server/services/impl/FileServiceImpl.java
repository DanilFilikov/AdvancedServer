package com.example.filikov_advanced_server.services.impl;

import com.example.filikov_advanced_server.responses.CustomSuccessResponse;
import com.example.filikov_advanced_server.responses.UrlResource;
import com.example.filikov_advanced_server.services.FileService;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.File;

public class FileServiceImpl implements FileService {

    @Override
    public CustomSuccessResponse<String> uploadFile(File file) {
        return CustomSuccessResponse.getSuccessResponse(file.getName());
    }

    @Override
    public UrlResource getFile(String fileName) {
        return new UrlResource();
    }

}
