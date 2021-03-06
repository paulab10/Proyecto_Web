package com.huawei.fileshandlingapi.service;

import com.huawei.fileshandlingapi.model.ProductsExcel;
import com.huawei.fileshandlingapi.model.FilesStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IHandlingFilesService {

    void initDirectory();

    void storeFile(MultipartFile file, String category, String supplier);

    void parseDetailView();

    Map<String, List<ProductsExcel>> processSupplier(String supplierDir);

    FilesStatus getFilesStatus(String dirName);

    void deleteFile(String fileName);
}
