package com.example.filikov_advanced_server.controller;

import com.example.filikov_advanced_server.error.ValidationConstants;
import com.example.filikov_advanced_server.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/uploadFile")
    public ResponseEntity uploadFile(@RequestParam MultipartFile file){
        return ResponseEntity.ok(fileService.uploadFile(file));
    }

    @GetMapping("/{fileName}")
    public ResponseEntity getFile(@PathVariable String fileName){
        return ResponseEntity.ok(fileService.getFile(fileName));
    }
}