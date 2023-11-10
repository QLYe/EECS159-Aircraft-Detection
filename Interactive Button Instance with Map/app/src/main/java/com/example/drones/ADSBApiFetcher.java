package com.example.drones;

import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;

public class ADSBApiFetcher {
    private double lat;
    private double lon;
    private double rad;
    private double resultLat;
    private double resultLon;
    private String result;
    Call<ApiInterface.AircraftResponse> call;
    ADSBApiFetcher(double lat, double lon, double rad)
    {
        this.lat = lat;
        this.lon = lon;
        this.rad = rad;
    }

    void setLatLon() {
        call = RetrofitClient.getInstance().getAircraftData(lat, lon, rad);
        call.enqueue(new Callback<ApiInterface.AircraftResponse>() {
            @Override
            public void onResponse(Call<ApiInterface.AircraftResponse> call, Response<ApiInterface.AircraftResponse> response) {
                // Iterate through the aircraft list and add markers on the map
                if (response.body() != null) {
                    for (ApiInterface.Aircraft aircraft : response.body().getAc()) {
                        resultLat = aircraft.getLat();
                        resultLon = aircraft.getLon();
                    }
                }
            }
            @Override
            public void onFailure(Call<ApiInterface.AircraftResponse> call, Throwable t) {

            }
        });
    }
    double getResultLat()
    {
        return resultLat;
    }
    double getResultLon()
    {
        return resultLon;
    }





}