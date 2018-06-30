package com.huawei.fileshandlingapi.business;


import com.huawei.fileshandlingapi.model.ProductsExcel;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.huawei.fileshandlingapi.constants.ExcelConstants.*;


public class ParseSupplierDV implements Runnable {

    private volatile Map<String, ProductsExcel> resultMap;

    private volatile Map<Integer, Integer> indexesMap;
    private volatile List<Row> supplier;

    public ParseSupplierDV(Map<Integer, Integer> indexesMap , List<Row> supplier) {
        this.indexesMap = indexesMap;
        this.supplier = supplier;
    }

    public Map<String, ProductsExcel> getResultMap() {
        return resultMap;
    }

    public void run() {
        organizeSupplierDV();
    }

    private void organizeSupplierDV() {
        resultMap = new HashMap<>();

        String keyValue = "";
        String poNumber = "";
        String status = "";
        String description = "";
        int itemCode = 0;
        int sprNumber = 0;
        double quantity = 0;
        double billedQty = 0;


        int ex = 0;
        boolean hasNumberEx = false;
        boolean hasNullEx = false;
        boolean isCheckingMap = false;

        ProductsExcel tmpProductsExcel;

        for (Row row: supplier) {
            tmpProductsExcel = new ProductsExcel();
            try {
                if (row.getCell(indexesMap.get(ITEM_CODE_INDEX)).getCellTypeEnum().equals(CellType.STRING)) {
                    itemCode = Integer.parseInt(row.getCell(indexesMap.get(ITEM_CODE_INDEX)).getStringCellValue());
                } else {
                    itemCode = (int)row.getCell(indexesMap.get(ITEM_CODE_INDEX)).getNumericCellValue();
                }
                poNumber = row.getCell(indexesMap.get(PO_NUMBER_INDEX)).getStringCellValue();

                if(poNumber.isEmpty()) {
                    continue;
                }

                if (row.getCell(indexesMap.get(SPR_INDEX)).getCellTypeEnum().equals(CellType.STRING)) {
                    sprNumber = Integer.parseInt(row.getCell(indexesMap.get(SPR_INDEX)).getStringCellValue());
                } else {
                    sprNumber = (int)row.getCell(indexesMap.get(SPR_INDEX)).getNumericCellValue();
                }

                status = row.getCell(indexesMap.get(STATUS_INDEX)).getStringCellValue();
                if (row.getCell(indexesMap.get(DESCRIPTION_INDEX)) != null) {
                    description = row.getCell(indexesMap.get(DESCRIPTION_INDEX)).getStringCellValue();
                }

                quantity = row.getCell(indexesMap.get(QUANTITY_INDEX)).getNumericCellValue();
                billedQty = row.getCell(indexesMap.get(PO_BILLED_INDEX)).getNumericCellValue();

                tmpProductsExcel.setItemCode(itemCode);
                tmpProductsExcel.setPoNumber(poNumber);
                tmpProductsExcel.setSprNumber(sprNumber);
                tmpProductsExcel.setDescription(description);
                tmpProductsExcel.setQuantityDV(quantity);
                tmpProductsExcel.setBilledQty(billedQty);
                /* Values to list */
                tmpProductsExcel.addQuantityDV(quantity);
                tmpProductsExcel.addBilledQty(billedQty);
                tmpProductsExcel.setInProcess(false);

                if (status.equalsIgnoreCase("IN PROCESS")) {
                    tmpProductsExcel.setInProcess(true);
                }

                keyValue = poNumber + sprNumber + itemCode;

                if (quantity == 0 && billedQty == 0) {
                    continue;
                }

                isCheckingMap = true;

                if ( resultMap.get(keyValue) != null) {
                    tmpProductsExcel = resultMap.get(keyValue);
                    //prevProduct.addQuantityDV(quantity);
                    //prevProduct.addBilledQty(billedQty);
                    tmpProductsExcel.addQuantityDV(quantity);
                    tmpProductsExcel.addBilledQty(billedQty);
                }

            } catch (NumberFormatException e) {
                hasNumberEx = true;
            }
            catch (Exception e) {
                if (!isCheckingMap) {
                    hasNullEx = true;
                }
            } finally {
                if (!hasNumberEx && !hasNullEx) {
                    resultMap.put(keyValue, tmpProductsExcel);
                } else {
                    hasNumberEx = false;
                    hasNullEx = false;
                }
                isCheckingMap = false;
            }
        }
    }
}
