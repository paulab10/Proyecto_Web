package com.huawei.fileshandlingapi.model;

public class ProductsExcel {
    private String poNumber;

    private int sprNumber;

    private int itemCode;

    private double quantityDV;

    private double quantitySupplier;

    private String description;

    private double qtyAccepted;

    private double billedQty;

    public double getBilledQty() {
        return billedQty;
    }

    public void setBilledQty(double billedQty) {
        this.billedQty = billedQty;
    }

    public double getQuantitySupplier() {
        return quantitySupplier;
    }

    public void setQuantitySupplier(double quantitySupplier) {
        this.quantitySupplier = quantitySupplier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getQtyAccepted() {
        return qtyAccepted;
    }

    public void setQtyAccepted(double qtyAccepted) {
        this.qtyAccepted = qtyAccepted;
    }

    public void addQuantity(double quantity) {
        this.quantityDV += quantity;
    }

    public double getQuantityDV() {
        return quantityDV;
    }

    public void setQuantityDV(double quantityDV) {
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
