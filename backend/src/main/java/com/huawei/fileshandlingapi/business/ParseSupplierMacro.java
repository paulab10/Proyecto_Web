package com.huawei.fileshandlingapi.business;

import com.huawei.fileshandlingapi.model.ProductsExcel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.huawei.fileshandlingapi.constants.ExcelConstants.*;


public class ParseSupplierMacro implements Runnable {

    private volatile Map<String, ProductsExcel> resultsMap;

    private volatile String dirName;

    public Map<String, ProductsExcel> getResultsMap() {
        return resultsMap;
    }

    public ParseSupplierMacro(String dirName) {
        this.dirName = dirName;
    }

    public void run() {
        parseSupplierFile();
    }

    private void parseSupplierFile() {
        resultsMap = new HashMap<>();

        Workbook workbook;

        try {
            workbook = ExcelParsing.readExcel("suppliers/" + dirName);
        }catch (Exception e) {
            return;
        }

        // =============================================================
        //   Iterating over all the sheets in the workbook
        // =============================================================
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        Sheet sheetMacro = null;
        System.out.println("Retrieving Sheets using Iterator");
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            String sheetName = sheet.getSheetName().toLowerCase();
            // TODO: change conditional
            if (sheetName.contains("macro")) {
                Row row = sheet.rowIterator().next();
                if (row.getPhysicalNumberOfCells() < 40) {
                    continue;
                }
                sheetMacro = sheet;
                break;
            }
        }

        if (sheetMacro == null) {
            return;
        }
        Iterator<Row> rowIterator = sheetMacro.rowIterator();
        Row headerRow = rowIterator.next();

        Iterator<Cell> cellIterator = headerRow.cellIterator();

        /* Index of columns */
        int poNumberIndex = 0;
        int sprNumberIndex = 0;
        int itemCodeIndex = 0;
        int poQtyIndex = 0;

        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();

            if (cell.getStringCellValue().equalsIgnoreCase(ITEM_CODE)) {
                itemCodeIndex = cell.getColumnIndex();
                continue;
            }

            if (cell.getStringCellValue().equalsIgnoreCase(PO_NUM_SHORT)) {
                poNumberIndex = cell.getColumnIndex();
                continue;
            }

            if (cell.getStringCellValue().equalsIgnoreCase(SPR)) {
                sprNumberIndex = cell.getColumnIndex();
                continue;
            }

            if (cell.getStringCellValue().equalsIgnoreCase(QUANTITY_MACRO)) {
                poQtyIndex = cell.getColumnIndex();
            }
        }

        boolean isCheckingMap = false;
        String key = "";

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            ProductsExcel tmpProductsExcel = new ProductsExcel();

            try {
                tmpProductsExcel.setPoNumber(row.getCell(poNumberIndex).getStringCellValue());
                tmpProductsExcel.setSprNumber((int)row.getCell(sprNumberIndex).getNumericCellValue());
                tmpProductsExcel.setItemCode((int)row.getCell(itemCodeIndex).getNumericCellValue());
                tmpProductsExcel.setQuantitySupplier(row.getCell(poQtyIndex).getNumericCellValue());

                key = tmpProductsExcel.getPoNumber() +
                        tmpProductsExcel.getSprNumber() +
                        tmpProductsExcel.getItemCode();

                isCheckingMap = true;

                if ( resultsMap.get(key) != null) {
                    tmpProductsExcel.addQuantitySupplier(resultsMap.get(key).getQuantitySupplier());
                }
            } catch (Exception e) {
                if (isCheckingMap) {
                    resultsMap.put(key, tmpProductsExcel);
                    isCheckingMap =  false;
                }
                continue;
            }

            resultsMap.put(key, tmpProductsExcel);
        }
    }
}
