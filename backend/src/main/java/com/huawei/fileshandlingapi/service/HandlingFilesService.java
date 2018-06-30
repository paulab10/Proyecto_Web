package com.huawei.fileshandlingapi.service;

import com.huawei.fileshandlingapi.business.ExcelParsing;
import com.huawei.fileshandlingapi.model.ProductsExcel;
import com.huawei.fileshandlingapi.model.FilesStatus;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.StyledEditorKit;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.huawei.fileshandlingapi.constants.CooperadoresConstants.DETAIL_VIEW;


@Service
public class HandlingFilesService implements IHandlingFilesService {

    private final Path rootLocation = Paths.get("upload-dir");
    private final String BASE_PATH = "upload-dir/";

    private Map<String, List<Row>> supplierMap;

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
    public void storeFile(MultipartFile file, String category, String supplier) {
        try {
            deleteFile(category + "/" + supplier);
            Files.copy(file.getInputStream(), Paths.get(BASE_PATH + category + "/" + supplier).resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
    }

    @Override
    public void parseDetailView() {
        supplierMap = ExcelParsing.parseDetailView();
    }

    @Override
    public Map<String, List<ProductsExcel>> processSupplier(String supplierDir) {
        try {
            return ExcelParsing.processSupplier(supplierMap.get(supplierDir), supplierDir);
        } catch (IOException e) {
            return null;
        } catch (InvalidFormatException e) {
            System.out.println("Invalid Format");
            return null;
        }
    }

    @Override
    public FilesStatus getFilesStatus(String dirName) {
        FilesStatus status;

        Map<String, Boolean> filesStatusMap = new HashMap<>();

        File folder = new File(BASE_PATH + dirName + "/");
        String path = folder.getPath();
        String[] subDirectories = folder.list((dir, name) -> new File(dir, name).isDirectory());

        for(String name: subDirectories) {
            File file = new File(BASE_PATH + "/suppliers/" + name);

            filesStatusMap.put(name, file.listFiles().length > 0);
        }

        if (dirName.equalsIgnoreCase("suppliers")) {
            Boolean hasDV = ((new File(BASE_PATH + "/detailview")).listFiles().length) > 0;

            filesStatusMap.put(DETAIL_VIEW, hasDV);
            status = new FilesStatus(filesStatusMap, hasDV);
        } else {
            status = new FilesStatus(filesStatusMap, false);
        }

        return status;
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
