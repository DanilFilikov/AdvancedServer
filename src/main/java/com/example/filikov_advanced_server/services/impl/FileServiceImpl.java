package com.example.filikov_advanced_server.services.impl;

import com.example.filikov_advanced_server.error.ValidationConstants;
import com.example.filikov_advanced_server.exception.CustomException;
import com.example.filikov_advanced_server.responses.CustomSuccessResponse;
import com.example.filikov_advanced_server.services.FileService;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {

    final String SAVE_LOCATION = "/home/dunice/IdeaProjects/AdvancedServer/temp/";
    private Path path = Paths.get("temp/").toAbsolutePath();

    @Override
    public CustomSuccessResponse<String> uploadFile(MultipartFile file) {
        try {
            File pathFile = new File(SAVE_LOCATION + file.getOriginalFilename());
            file.transferTo(pathFile);
            return CustomSuccessResponse.getSuccessResponse(SAVE_LOCATION + pathFile.getName());
        } catch (IOException e){
            throw new CustomException(ValidationConstants.UNKNOWN);
        }
    }

    @Override
    public UrlResource getFile(String fileName) {
        try {
            Path absPath = path.resolve(fileName);
            return new UrlResource(absPath.toUri());
        } catch (MalformedURLException e){
            throw new CustomException(ValidationConstants.EXCEPTION_HANDLER_NOT_PROVIDED);
        }
    }

}
