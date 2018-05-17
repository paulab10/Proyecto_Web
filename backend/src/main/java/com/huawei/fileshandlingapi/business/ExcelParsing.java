package com.huawei.fileshandlingapi.business;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.huawei.fileshandlingapi.constants.CooperadoresConstants.*;
import static com.huawei.fileshandlingapi.constants.ExcelConstants.COOPERADOR;
import static com.huawei.fileshandlingapi.constants.ExcelConstants.ESTADO;
import static com.huawei.fileshandlingapi.constants.ExcelConstants.FECHA_INICIO;

public class ExcelParsing {

    private static final String FILE_PATH_NAME = "";

    public void all() throws IOException, InvalidFormatException {
        // Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(
                new File(ExcelParsing.class.getClassLoader().getResource(FILE_PATH_NAME).getFile()));

        // Retrieving the number of sheets in the Workbook
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

        /*
           =============================================================
           Iterating over all the sheets in the workbook (Multiple ways)
           =============================================================
        */

        // 1. You can obtain a sheetIterator and iterate over it
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        System.out.println("Retrieving Sheets using Iterator");
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            System.out.println("=> " + sheet.getSheetName());
        }

        // Getting the Sheet at index zero
        Sheet sheet = workbook.getSheetAt(0);

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

        Iterator<Row> rowIterator = sheet.rowIterator();
        Row headerRow = rowIterator.next();

        Iterator<Cell> cellIterator = headerRow.cellIterator();

        /* Index of columns */
        int statusCellIndex = 95 - 1;
        int supplierCellIndex = 64 - 1;
        int dateCellIndex = 24 -1;

        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();

            if (dataFormatter.formatCellValue(cell).equalsIgnoreCase(ESTADO)) {
                statusCellIndex = cell.getColumnIndex();
                continue;
            }

            if (dataFormatter.formatCellValue(cell).equalsIgnoreCase(COOPERADOR)) {
                supplierCellIndex = cell.getColumnIndex();
                continue;
            }

            if (dataFormatter.formatCellValue(cell).equalsIgnoreCase(FECHA_INICIO)) {
                dateCellIndex = cell.getColumnIndex();
            }
        }

        List<Row> validRows = new ArrayList<Row>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            Cell cell =  row.getCell(statusCellIndex);

            if (dataFormatter.formatCellValue(cell).
                    equalsIgnoreCase("IN PROCESS")) {
                validRows.add(row);
            }
        }

        Map<String, List<Row>> suppliersMap;
        List<Row> dicoList = new ArrayList<Row>();
        List<Row> eneconList = new ArrayList<Row>();
        List<Row> fscrList = new ArrayList<Row>();
        List<Row> applusList = new ArrayList<Row>();
        List<Row> sicteList = new ArrayList<Row>();
        List<Row> conectarList = new ArrayList<Row>();

        for (Row row: validRows) {
            Cell cell = row.getCell(supplierCellIndex);

            if (cell.getStringCellValue().equalsIgnoreCase(DICO)) {
                dicoList.add(row);
                continue;
            }

            if (cell.getStringCellValue().equalsIgnoreCase(ENECON)) {
                eneconList.add(row);
                continue;
            }

            if (cell.getStringCellValue().equalsIgnoreCase(FSCR)) {
                fscrList.add(row);
                continue;
            }

            if (cell.getStringCellValue().equalsIgnoreCase(APPLUS)) {
                applusList.add(row);
                continue;
            }

            if (cell.getStringCellValue().equalsIgnoreCase(SICTE)) {
                sicteList.add(row);
                continue;
            }

            if (cell.getStringCellValue().equalsIgnoreCase(CONECTAR)) {
                conectarList.add(row);
            }
        }

        System.out.println("DICO count: " + dicoList.size());
        System.out.println("FSCR count: " + fscrList.size());
        System.out.println("SICTE count: " + sicteList.size());
        System.out.println("APPLUS count: " + applusList.size());
        System.out.println("ENECON count: " + eneconList.size());
        System.out.println("CONECTAR count: " + conectarList.size());
    }
}