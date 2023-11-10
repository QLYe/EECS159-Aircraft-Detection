package com.example.drones;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button1);

        int a = 0;
        if (a == 0)
        {
            ADSBApiFetcher s = new ADSBApiFetcher(37.7749, -122.4194, 50);
            s.setLatLon();
            Toast.makeText(getApplicationContext(), Double.toString(s.getResultLon()) , 2).show();
            a  = 1;
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, MapsMarkerActivity.class);
                Toast.makeText(getApplicationContext(), "Toast", Toast.LENGTH_SHORT).show();
                startActivity(intent1);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_preview);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng uci = new LatLng(33.6424, -117.8417);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uci, 10));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent intent = new Intent(MainActivity.this, MapsMarkerActivity.class);
                startActivity(intent);
            }
        });
    }
}