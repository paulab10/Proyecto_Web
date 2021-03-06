package com.huawei.fileshandlingapi.business;

import com.huawei.fileshandlingapi.model.ProductsExcel;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static com.huawei.fileshandlingapi.constants.CooperadoresConstants.*;
import static com.huawei.fileshandlingapi.constants.ExcelConstants.*;

public class ExcelParsing {

    private static final String BASE_PATH = "upload-dir/";

    private static Map<Integer, Integer> indexesMap;

    public static Map<String, List<Row>> parseDetailView() {
        Workbook workbook;
        try {
            workbook = readExcel(DETAIL_VIEW);
        } catch (Exception e) {
            return null;
        }

        // =============================================================
        //   Iterating over all the sheets in the workbook (Multiple ways)
        // =============================================================

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
        indexesMap = new HashMap<>();

        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();

            if (dataFormatter.formatCellValue(cell).equalsIgnoreCase(IBUY_STATUS)) {
                indexesMap.put(STATUS_INDEX, cell.getColumnIndex());
                continue;
            }

            if (cell.getStringCellValue().equalsIgnoreCase(DESCRIPTION)) {
                indexesMap.put(DESCRIPTION_INDEX, cell.getColumnIndex());
                continue;
            }

            if (dataFormatter.formatCellValue(cell).equalsIgnoreCase(COOPERADOR)) {
                indexesMap.put(SUPPLIER_INDEX, cell.getColumnIndex());
                continue;
            }

            if (cell.getStringCellValue().equalsIgnoreCase(ITEM_CODE)) {
                indexesMap.put(ITEM_CODE_INDEX, cell.getColumnIndex());
                continue;
            }

            if (cell.getStringCellValue().equalsIgnoreCase(PO_NUM_SHORT)) {
                indexesMap.put(PO_NUMBER_INDEX, cell.getColumnIndex());
                continue;
            }

            if (cell.getStringCellValue().equalsIgnoreCase(SPR)) {
                indexesMap.put(SPR_INDEX, cell.getColumnIndex());
                continue;
            }

            if (cell.getStringCellValue().equalsIgnoreCase(QUANTITY)) {
                indexesMap.put(QUANTITY_INDEX, cell.getColumnIndex());
                continue;
            }

            if (cell.getStringCellValue().equalsIgnoreCase(PO_BILLED)) {
                indexesMap.put(PO_BILLED_INDEX, cell.getColumnIndex());
            }

            /*
            if (dataFormatter.formatCellValue(cell).equalsIgnoreCase(FECHA_INICIO)) {
                indexesMap.put(DATE_INDEX, cell.getColumnIndex());
            }*/
        }

        //List<Row> validRows = determineValidRows(rowIterator, indexesMap.get(STATUS_INDEX));

        return determineSuppliers(rowIterator, indexesMap.get(SUPPLIER_INDEX));
    }

    public static Workbook readExcel(String dirName) throws IOException, InvalidFormatException {
        // Creating a Workbook from an Excel file (.xls or .xlsx)
        File folder = new File(BASE_PATH + dirName + "/");
        //File folder = new File(ExcelParsing.class.getClassLoader().getResource(BASE_PATH + dirName).getFile());
        File[] files = folder.listFiles();
        FileInputStream fileInputStream = null;
        Workbook workbook = null;

        try {
            fileInputStream = new FileInputStream(files[0]);
            workbook = WorkbookFactory.create(fileInputStream);
            //workbook = WorkbookFactory.create(new File(ExcelParsing.class.getClassLoader().
                    //getResource("files/"+dirName).getFile()));

            // Retrieving the number of sheets in the Workbook
            System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("[ERROR] ExcelParsing: Null Pointer error. empty directory");
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }

        return workbook;
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

    public static Map<String, List<Row>> determineSuppliers(Iterator<Row> rows, int supplierCellIndex) {
        Map<String, List<Row>> suppliersMap = new HashMap<String, List<Row>>();
        List<Row> dicoList = new ArrayList<Row>();
        List<Row> eneconList = new ArrayList<Row>();
        List<Row> fscrList = new ArrayList<Row>();
        List<Row> applusList = new ArrayList<Row>();
        List<Row> sicteList = new ArrayList<Row>();
        List<Row> conectarList = new ArrayList<Row>();

        while (rows.hasNext())
        {
            Row row = rows.next();
            Cell cell = row.getCell(supplierCellIndex);

            if (cell == null) {
                continue;
            }

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

    public static Map<String, List<ProductsExcel>> processSupplier(List<Row> supplier, String supplierDir) throws IOException, InvalidFormatException {

        Map<String, List<ProductsExcel>> resultsMap = new HashMap<>();

        ParseSupplierDV parseSupplierDV = new ParseSupplierDV(indexesMap, supplier);
        ParseSupplierMacro parseSupplierMacro = new ParseSupplierMacro(supplierDir);

        Thread t1 = new Thread(parseSupplierDV);
        Thread t2 = new Thread(parseSupplierMacro);

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        Map<String, ProductsExcel> supplierDV = parseSupplierDV.getResultMap();
        Map<String, ProductsExcel> supplierMacro = parseSupplierMacro.getResultsMap();

        List<ProductsExcel> pendingToCreateList = new ArrayList<>();
        List<ProductsExcel> availableList = new ArrayList<>();
        List<ProductsExcel> adjustList = new ArrayList<>();
        List<ProductsExcel> pendingToCheckList = new ArrayList<>();

        for (ProductsExcel rowSupplier: supplierMacro.values()) {
            String keyValue = rowSupplier.getPoNumber() +
                    rowSupplier.getSprNumber()+ rowSupplier.getItemCode();

            try {
                ProductsExcel tmp = supplierDV.get(keyValue);
                if (tmp != null) {

                    double availableQuantitiy = tmp.getAllQuantityDV() - tmp.getAllBilledQty();
                    double billedQuantity = tmp.getAllBilledQty();
                    double supplierQuantity = rowSupplier.getQuantitySupplier();

                    /* Discard elements that are already billed */
                    if (billedQuantity < supplierQuantity ) {
                        /* Only IN PROCESS can be part of available and adjust */
                        if (tmp.isInProcess()) {
                            /* If enough quantity, determine available */
                            if (supplierQuantity <= availableQuantitiy) {
                                /* Loop to determine available */
                                double tmpSupplierQty = supplierQuantity;
                                List<Double> allQuantities = tmp.getQuantityDVList();
                                List<Double> allBilledQty = tmp.getBilledQtyList();

                                List<Double> currentQuantities = new ArrayList<>();
                                for (int i = 0; i < allQuantities.size(); i++) {
                                    currentQuantities.add(allQuantities.get(i) - allBilledQty.get(i));
                                }

                                currentQuantities.sort(null);

                                for(Double currentQty: currentQuantities) {
                                    ProductsExcel loopProduct = getNewProduct(tmp);
                                    loopProduct.setQuantityDV(currentQty);

                                    if (tmpSupplierQty >= currentQty) {
                                        loopProduct.setQuantitySupplier(currentQty);
                                    } else {
                                        loopProduct.setQuantitySupplier(tmpSupplierQty);
                                    }

                                    tmpSupplierQty -= currentQty;
                                    if (tmpSupplierQty < 0) {
                                        tmpSupplierQty = 0;
                                    }
                                    availableList.add(loopProduct);
                                }
                            }
                            /* Not enough quantity, set to adjust */
                            else {
                                tmp.setQuantityDV(availableQuantitiy);
                                tmp.setQuantitySupplier(supplierQuantity);
                                adjustList.add(tmp);
                            }
                            /* Not in process, could be part of create list */
                        } else {
                            pendingToCreateList.add(rowSupplier);
                        }
                    }

                    /* Determine products to check */
                    if (tmp.getAllBilledQty() != 0) { // Only add to list if no zero in Billed cell
                        List<Double> allBilledQty = tmp.getBilledQtyList();

                        tmp.setQuantitySupplier(rowSupplier.getQuantitySupplier());
                        tmp.setBilledQty(tmp.getAllBilledQty());
                        pendingToCheckList.add(tmp);
                    }

                } else {

                    pendingToCreateList.add(rowSupplier);
                }
            } catch (Exception e) {
                pendingToCreateList.add(rowSupplier);
                continue;
            }
        }

        resultsMap.put(AVAILABLE_KEY, availableList);
        resultsMap.put(TO_ADJUST_KEY, adjustList);
        resultsMap.put(TO_CREATE_KEY, pendingToCreateList);
        resultsMap.put(TO_CHECK_KEY, pendingToCheckList);

        return resultsMap;
    }

    private static ProductsExcel getNewProduct(ProductsExcel tmp) {
        ProductsExcel result = new ProductsExcel();

        result.setItemCode(tmp.getItemCode());
        result.setPoNumber(tmp.getPoNumber());
        result.setSprNumber(tmp.getSprNumber());
        result.setDescription(tmp.getDescription());
        return result;
    }


}