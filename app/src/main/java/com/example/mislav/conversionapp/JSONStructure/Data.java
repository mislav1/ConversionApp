package com.example.mislav.conversionapp.JSONStructure;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("buying_rate")
    @Expose
    private String buying_rate;

    @SerializedName("selling_rate")
    @Expose
    private String selling_rate;

    @SerializedName("currency_code")
    @Expose
    private String currency_code;

    public String getBuyingRate() {
        return buying_rate;
    }

    public String getSellingRate() {
        return selling_rate;
    }

    public String getCurrencyName() {
        return currency_code;
    }

}
