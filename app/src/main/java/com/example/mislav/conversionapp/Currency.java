package com.example.mislav.conversionapp;

public class Currency {

    private String buyingRate;
    private String sellingRate;
    private String currencyCode;

    public Currency(String buyingRate, String sellingRate, String currencyCode) {
        this.buyingRate = buyingRate;
        this.sellingRate = sellingRate;
        this.currencyCode = currencyCode;
    }

    public String getBuyingRate() {
        return buyingRate;
    }

    public String getSellingRate() {
        return sellingRate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
}
