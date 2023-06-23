package com.example.filikov_advanced_server.services;

import com.example.filikov_advanced_server.responses.CustomSuccessResponse;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public CustomSuccessResponse<String> uploadFile(MultipartFile file);
    public UrlResource getFile(String fileName);
}
