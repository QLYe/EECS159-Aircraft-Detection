package com.example.drones;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class MyLocListener implements LocationListener{
    private MainActivity mainactivity;
    //private double lon, lat;
    MyLocListener(MainActivity mainactivity){
        super();
        this.mainactivity = mainactivity;
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        mainactivity.setLatLon(latitude, longitude);
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
