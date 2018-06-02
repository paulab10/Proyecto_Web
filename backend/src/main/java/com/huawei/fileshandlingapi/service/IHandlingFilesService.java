package com.huawei.fileshandlingapi.service;

import com.huawei.fileshandlingapi.model.ProductsExcel;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface IHandlingFilesService {

    void initDirectory();

    void storeFile(MultipartFile file, String dirName);

    void parseDetailView();

    Map<String, List<ProductsExcel>> processSupplier(String supplierDir);

    Resource loadFiles(String filename);

    void deleteFile(String fileName);
}
