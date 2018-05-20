package com.huawei.fileshandlingapi.service;

import ch.qos.logback.core.rolling.helper.FileStoreUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class HandlingFilesService implements IHandlingFilesService {

    private final Path rootLocation = Paths.get("upload-dir");
    private final String BASE_PATH = "upload-dir/";

    @Override
    public void initDirectory() {
        try {
            if (!Files.isDirectory(rootLocation)) {
                Files.createDirectory(rootLocation);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }

    @Override
    public void storeFile(MultipartFile file, String dirName) {
        try {
            deleteFile(dirName);
            Files.copy(file.getInputStream(), Paths.get(BASE_PATH + dirName).resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
    }

    @Override
    public Resource loadFiles(String filename) {
        try {
            Path filePath = rootLocation.resolve(filename);
            File file = filePath.toFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteFile(String dirName) {
        try {
            FileUtils.cleanDirectory(new File(BASE_PATH + dirName + "/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
