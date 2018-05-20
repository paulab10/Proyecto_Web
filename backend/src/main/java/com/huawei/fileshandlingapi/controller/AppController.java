package com.huawei.fileshandlingapi.controller;

import com.huawei.fileshandlingapi.service.IHandlingFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.m
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AppController {

    @Autowired
    IHandlingFilesService handlingFilesService;

    private Map<String, String> files = new HashMap<>();

    @PostMapping(value = "/upload-file/{name}")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile multipartFile, @PathVariable String name) {
        System.out.println("[INFO]: Uploading file");
        String message = "";
        try {
            handlingFilesService.storeFile(multipartFile, name);
            files.put(name, multipartFile.getOriginalFilename());

            message = "You successfully uploaded " + multipartFile.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            e.printStackTrace();
            message = "FAIL to upload " + multipartFile.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }
}
