package com.huawei.fileshandlingapi.business;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.huawei.fileshandlingapi.constants.CooperadoresConstants.*;
import static com.huawei.fileshandlingapi.constants.ExcelConstants.*;

public class ExcelParsing {

    private static final String BASE_PATH = "upload-dir/";

    public static void readExcel(String dirName) throws IOException, InvalidFormatException {
        // Creating a Workbook from an Excel file (.xls or .xlsx)
        File folder = new File(BASE_PATH + dirName + "/");
        File[] files = folder.listFiles();

        Workbook workbook;

        try {
            //workbook = WorkbookFactory.create(files[0]);
            workbook = WorkbookFactory.create(new File(ExcelParsing.class.getClassLoader().
                    getResource("files/detail_view_2018.xls").getFile()));

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
            int itemCodeIndex = 4 - 1;
            int poQtyIndex = 0;

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                if (dataFormatter.formatCellValue(cell).equalsIgnoreCase(IBUY_STATUS)) {
                    statusCellIndex = cell.getColumnIndex();
                    continue;
                }

                if (dataFormatter.formatCellValue(cell).equalsIgnoreCase(COOPERADOR)) {
                    supplierCellIndex = cell.getColumnIndex();
                    continue;
                }

                if (cell.getStringCellValue().equalsIgnoreCase(ITEM_CODE)) {
                    itemCodeIndex = cell.getColumnIndex();
                    continue;
                }

                if (cell.getStringCellValue().equalsIgnoreCase(PO_IBUY_CANT)) {
                    poQtyIndex = cell.getColumnIndex();
                    continue;
                }

                if (dataFormatter.formatCellValue(cell).equalsIgnoreCase(FECHA_INICIO)) {
                    dateCellIndex = cell.getColumnIndex();
                }
            }

            List<Row> validRows = determineValidRows(rowIterator, statusCellIndex);

            Map<String, List<Row>> suppliersMap = determineSuppliers(validRows, supplierCellIndex);

            List<Row> conectarSupplier = suppliersMap.get(CONECTAR_KEY);

            Map<Integer, Integer> qtySpr = new HashMap<Integer, Integer>();

            int keyValue = 0;
            int quantity = 0;

            for (Row row: conectarSupplier) {
                try {
                    keyValue = (int)row.getCell(itemCodeIndex).getNumericCellValue();
                    quantity = (int) row.getCell(poQtyIndex).getNumericCellValue();

                    if (quantity == 0) {
                        continue;
                    }

                    if ( qtySpr.get(keyValue) != null) {
                        quantity += qtySpr.get(keyValue);
                    }


                } catch (Exception e) {
                    System.out.println("Empty code");
                } finally {
                    qtySpr.put(keyValue, quantity);
                    continue;
                }

            }

            System.out.println(qtySpr.size());

        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("[ERROR] ExcelParsing: Null Pointer error. empty directory");
        }

    }

    public static List<Row> determineValidRows(Iterator<Row> rowIterator, int statusCellIndex) {
        List<Row> validRows = new ArrayList<Row>();

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            Cell cell =  row.getCell(statusCellIndex);

            if (dataFormatter.formatCellValue(cell).
                    equalsIgnoreCase("IN PROCESS")) {
                validRows.add(row);
            }
        }
        return validRows;
    }

    public static Map<String, List<Row>> determineSuppliers(List<Row> validRows, int supplierCellIndex) {
        Map<String, List<Row>> suppliersMap = new HashMap<String, List<Row>>();
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

        suppliersMap.put(DICO_KEY, dicoList);
        suppliersMap.put(FSCR_KEY, fscrList);
        suppliersMap.put(SICTE_KEY, sicteList);
        suppliersMap.put(APPLUS_KEY, applusList);
        suppliersMap.put(ENECON_KEY, eneconList);
        suppliersMap.put(CONECTAR_KEY, conectarList);

        System.out.println("DICO count: " + dicoList.size());
        System.out.println("FSCR count: " + fscrList.size());
        System.out.println("SICTE count: " + sicteList.size());
        System.out.println("APPLUS count: " + applusList.size());
        System.out.println("ENECON count: " + eneconList.size());
        System.out.println("CONECTAR count: " + conectarList.size());

        return suppliersMap;
    }

    //public

    public static void main(String[] args) throws IOException, InvalidFormatException {

        readExcel("");
    }
}
