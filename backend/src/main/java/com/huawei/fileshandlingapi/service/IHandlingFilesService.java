package com.huawei.fileshandlingapi.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface IHandlingFilesService {

    void initDirectory();

    void storeFile(MultipartFile file, String dirName);

    Resource loadFiles(String filename);

    void deleteFile(String fileName);
}
