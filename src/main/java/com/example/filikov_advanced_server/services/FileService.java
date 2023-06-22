package com.example.filikov_advanced_server.services;

import com.example.filikov_advanced_server.responses.CustomSuccessResponse;
import com.example.filikov_advanced_server.responses.UrlResource;

import java.io.File;

public interface FileService {
    public CustomSuccessResponse<String> uploadFile(File file);
    public UrlResource getFile(String fileName);
}
