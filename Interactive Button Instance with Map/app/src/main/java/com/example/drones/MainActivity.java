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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    interface RequestUser{
        @GET("/api/users/{uid}")
        Call<UserData> getUser(@Path("uid") String uid);
    }
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button1);

        Call<ApiInterface.AircraftResponse>call = RetrofitClient.getInstance().getAircraftData(37.66, -112, 40);
        call.enqueue(new Callback<ApiInterface.AircraftResponse>() {
        @Override
        public void onResponse(Call<ApiInterface.AircraftResponse> call, Response<ApiInterface.AircraftResponse> response) {
            // Iterate through the aircraft list and add markers on the map
            if (response.body() != null) {
                for (ApiInterface.Aircraft aircraft : response.body().getAc()) {
                    Toast.makeText(getApplicationContext(), Double.toString(aircraft.getLat()) , 1).show();
                }
            }
        }
        @Override
        public void onFailure(Call<ApiInterface.AircraftResponse> call, Throwable t) {

        }
        });
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestUser requestUser = retrofit.create(RequestUser.class);

        requestUser.getUser("3").enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                Toast.makeText(getApplicationContext(), response.body().data.first_name , 2).show();
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {

            }

        });*/

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