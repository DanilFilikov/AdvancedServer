package com.example.filikov_advanced_server;

import static com.example.filikov_advanced_server.Methods.file;
import com.example.filikov_advanced_server.responses.CustomSuccessResponse;
import com.example.filikov_advanced_server.services.FileService;
import com.example.filikov_advanced_server.services.impl.FileServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.core.io.UrlResource;

public class FileServiceTest {

    @Test
    public void uploadFileTest(){
        FileService fileService = new FileServiceImpl();

        CustomSuccessResponse<String> response = fileService.uploadFile(file);

        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals(response.getStatusCode(), 1);
        Assertions.assertTrue(response.getSuccess());
    }

    @Test
    public void getFileTest(){
        FileService fileService = new FileServiceImpl();

        UrlResource urlResource = fileService.getFile(file.getName());

        Assertions.assertNotNull(urlResource);
    }

}