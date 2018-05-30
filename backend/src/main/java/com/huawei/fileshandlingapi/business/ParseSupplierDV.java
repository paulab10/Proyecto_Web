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
        resultMap = new HashMap<String, ProductsExcel>();

        String keyValue = "";
        String poNumber = "";
        int itemCode = 0;
        int sprNumber = 0;
        int quantity = 0;
        int billedQty = 0;

        int ex = 0;

        ProductsExcel tmpProductsExcel;

        for (Row row: supplier) {
            tmpProductsExcel = new ProductsExcel();
            try {
                itemCode = (int)row.getCell(indexesMap.get(ITEM_CODE_INDEX)).getNumericCellValue();
                poNumber = row.getCell(indexesMap.get(PO_NUMBER_INDEX)).getStringCellValue();

                if (row.getCell(indexesMap.get(SPR_INDEX)).getCellTypeEnum().equals(CellType.STRING)) {
                    sprNumber = Integer.parseInt(row.getCell(indexesMap.get(SPR_INDEX)).getStringCellValue());
                } else {
                    sprNumber = (int) row.getCell(indexesMap.get(SPR_INDEX)).getNumericCellValue();
                }

                quantity = (int) row.getCell(indexesMap.get(QUANTITY_INDEX)).getNumericCellValue();
                billedQty = (int) row.getCell(indexesMap.get(PO_BILLED_INDEX)).getNumericCellValue();

                tmpProductsExcel.setItemCode(itemCode);
                tmpProductsExcel.setPoNumber(poNumber);
                tmpProductsExcel.setSprNumber(sprNumber);
                tmpProductsExcel.setQuantityDV(quantity);
                tmpProductsExcel.setBilledQty(billedQty);

                keyValue = poNumber + sprNumber + itemCode;

                if (quantity == 0) {
                    continue;
                }

                if ( resultMap.get(keyValue) != null) {
                    tmpProductsExcel.addQuantity(resultMap.get(keyValue).getQuantityDV());
                }


            } catch (Exception e) {
                ex++;
            } finally {
                resultMap.put(keyValue, tmpProductsExcel);
                continue;
            }
        }
    }
}
