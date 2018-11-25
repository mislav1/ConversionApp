package com.example.mislav.conversionapp;

import com.example.mislav.conversionapp.JSONStructure.Data;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiInterface {

    String BASE_URL = "http://hnbex.eu/";

    @Headers("Content-Type: application/json")
    @GET("api/v1/rates/daily/")
    Call<ArrayList<Data>> getData();
}
