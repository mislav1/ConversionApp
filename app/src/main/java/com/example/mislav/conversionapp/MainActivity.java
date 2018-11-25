package com.example.mislav.conversionapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mislav.conversionapp.JSONStructure.Data;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Spinner firstSpinner;
    Spinner secondSpinner;
    HashMap<String, Currency> currencyInfo;
    Button submitButton;
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstSpinner = findViewById(R.id.spinnerFirstCurrency);
        secondSpinner = findViewById(R.id.spinnerSecondCurrency);
        submitButton = findViewById(R.id.btSubmit);
        resultTextView = findViewById(R.id.txResult);
        currencyInfo = new HashMap<>();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Currencies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        firstSpinner.setAdapter(adapter);
        secondSpinner.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<ArrayList<Data>> call = api.getData();

        call.enqueue(new Callback<ArrayList<Data>>() {
            @Override
            public void onResponse(Call<ArrayList<Data>> call, Response<ArrayList<Data>> response) {
                ArrayList<Data> currenciesData = response.body();

                for (Data data : currenciesData) {
                    Currency curr = new Currency(data.getBuyingRate(), data.getSellingRate(), data.getCurrencyName());
                    currencyInfo.put(data.getCurrencyName(), curr);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Data>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Problem getting json", Toast.LENGTH_SHORT).show();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameOfFromCurrency = firstSpinner.getSelectedItem().toString();
                String nameOfToCurrency = secondSpinner.getSelectedItem().toString();


                try {
                    Currency from = currencyInfo.get(nameOfFromCurrency);
                    Currency to = currencyInfo.get(nameOfToCurrency);

                    double result = calculateConversion(from, to);
                    String stringResult = String.format("%.3f", result);
                    resultTextView.setText("1 " + nameOfFromCurrency + " = " + stringResult
                            + " " + nameOfToCurrency);
                } catch (NullPointerException e){
                    Toast.makeText(getApplicationContext(), "null pointer", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private double calculateConversion(Currency from, Currency to){
        double fromBuyingRate;
        double toSellingRate;
        try {
            fromBuyingRate = Double.parseDouble(from.getBuyingRate());
            toSellingRate = Double.parseDouble(to.getSellingRate());
            return fromBuyingRate / toSellingRate;

        } catch (NumberFormatException e){
            Log.i("MainActivity", "calculateConversion: can't covert to string");
        }
        return 0;
    }
}
