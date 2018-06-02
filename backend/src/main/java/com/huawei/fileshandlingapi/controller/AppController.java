package com.huawei.fileshandlingapi.controller;

import com.huawei.fileshandlingapi.model.ProductsExcel;
import com.huawei.fileshandlingapi.service.IHandlingFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.m

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.huawei.fileshandlingapi.constants.CooperadoresConstants.AVAILABLE_KEY;
import static com.huawei.fileshandlingapi.constants.CooperadoresConstants.TO_CREATE_KEY;

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

    @GetMapping(value = "/get/{action}/{supplier}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductsExcel>> getModificationInfo(@PathVariable String supplier, @PathVariable String action) {
        List<ProductsExcel> resultsList = handlingFilesService.processSupplier(supplier).get(action);

        if (resultsList != null) {
            System.out.println("Supplier processed correctly");

            return ResponseEntity.status(HttpStatus.OK).body(resultsList);
        } else {
            System.out.println("Error processing supplier: " + supplier);

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }

    @PutMapping(value = "/update-dv")
    public ResponseEntity<String> updateDetailView() {
        handlingFilesService.parseDetailView();

        return  ResponseEntity.status(HttpStatus.OK).body((String)"Detail View Processed");
    }
}
