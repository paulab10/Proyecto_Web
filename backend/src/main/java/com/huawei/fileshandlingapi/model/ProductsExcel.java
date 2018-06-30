package com.huawei.fileshandlingapi.model;

import java.util.ArrayList;
import java.util.List;

public class ProductsExcel {
    private String poNumber;

    private int sprNumber;

    private int itemCode;

    private List<Double> quantityDVList;

    private List<Double> billedQtyList;

    private double quantityDV;

    private double quantitySupplier;

    private String description;

    private double qtyAccepted;

    private double billedQty;

    private boolean isInProcess;

    public ProductsExcel() {
        quantityDVList = new ArrayList<>();
        billedQtyList = new ArrayList<>();
    }

    public boolean isInProcess() {
        return isInProcess;
    }

    public void setInProcess(boolean inProcess) {
        isInProcess = inProcess;
    }

    public List<Double> getQuantityDVList() {
        return quantityDVList;
    }

    public void setQuantityDVList(List<Double> quantityDVList) {
        this.quantityDVList = quantityDVList;
    }

    public List<Double> getBilledQtyList() {

        return billedQtyList;
    }

    public double getAllBilledQty() {
        double result = 0;

        for(Double val: billedQtyList) {
            result += val;
        }

        return result;
    }

    public double getAllQuantityDV() {
        double result = 0;

        for(Double val: quantityDVList) {
            result += val;
        }

        return result;
    }

    public void setBilledQtyList(List<Double> billedQtyList) {
        this.billedQtyList = billedQtyList;
    }

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

    public void addQuantityDV(double quantity) {
        this.quantityDVList.add(quantity);
    }

    public void addBilledQty(double quantity) { this.billedQtyList.add(quantity); }

    public void addQuantitySupplier(double quantity) {
        quantitySupplier += quantity;
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
