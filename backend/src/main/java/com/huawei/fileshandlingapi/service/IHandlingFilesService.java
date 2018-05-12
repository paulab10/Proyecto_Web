package com.huawei.fileshandlingapi.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IHandlingFilesService {

    void initDirectory();

    void storeFile(MultipartFile file);

    Resource loadFiles(String filename);
}
