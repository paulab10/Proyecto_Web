package com.huawei.fileshandlingapi.model;

public class ProductsExcel {
    private String poNumber;

    private int sprNumber;

    private int itemCode;

    private int quantityDV;

    private int quantitySupplier;

    private String description;

    private int qtyAccepted;

    private int billedQty;

    public int getBilledQty() {
        return billedQty;
    }

    public void setBilledQty(int billedQty) {
        this.billedQty = billedQty;
    }

    public int getQuantitySupplier() {
        return quantitySupplier;
    }

    public void setQuantitySupplier(int quantitySupplier) {
        this.quantitySupplier = quantitySupplier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQtyAccepted() {
        return qtyAccepted;
    }

    public void setQtyAccepted(int qtyAccepted) {
        this.qtyAccepted = qtyAccepted;
    }

    public void addQuantity(int quantity) {
        this.quantityDV += quantity;
    }

    public int getQuantityDV() {
        return quantityDV;
    }

    public void setQuantityDV(int quantityDV) {
        this.quantityDV = quantityDV;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public int getSprNumber() {
        return sprNumber;
    }

    public void setSprNumber(int sprNumber) {
        this.sprNumber = sprNumber;
    }

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }
}
