package com.example.drones;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;

import androidx.annotation.NonNull;

public class MyLocListener implements LocationListener{
    //private double lon, lat;
    MyLocListener(){
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        //longitude = location.getLongitude();
        //latitude = location.getLatitude();
    }
    /**
    public double getLon(){
        return lon;
    }

    public double getLat() {
        return lat;
    }
    **/
}
