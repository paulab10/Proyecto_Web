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
            workbook = ExcelParsing.readExcel(dirName);
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

            if (cell.getStringCellValue().equalsIgnoreCase(PO_NUMERO)) {
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



        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            ProductsExcel tmpProductsExcel = new ProductsExcel();

            tmpProductsExcel.setPoNumber(row.getCell(poNumberIndex).getStringCellValue());
            tmpProductsExcel.setSprNumber((int)row.getCell(sprNumberIndex).getNumericCellValue());
            tmpProductsExcel.setItemCode((int)row.getCell(itemCodeIndex).getNumericCellValue());
            tmpProductsExcel.setQuantitySupplier((int)row.getCell(poQtyIndex).getNumericCellValue());

            String key = tmpProductsExcel.getPoNumber() +
                    tmpProductsExcel.getSprNumber() +
                    tmpProductsExcel.getItemCode();

            resultsMap.put(key, tmpProductsExcel);
        }
    }
}
