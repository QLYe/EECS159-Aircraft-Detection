package com.example.drones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.os.Bundle;
import android.location.LocationManager;
import android.location.LocationListener;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import java.util.Vector;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ApiData apidata = new ApiData();
    public static final String ALERT = "alert";
    private Alert alert = new Alert();
    private double lat = 0, lon = 0;

    private boolean detecting = false;
    private boolean alarm_on = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocListener(MainActivity.this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // Permission has not been granted yet, request it
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            this.finishAffinity();
            return;
        }


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //double longitude = location.getLongitude();
        //double latitude = location.getLatitude();
        //Log.d("longitude debug", Double.toString(longitude));
        //Log.d("latitude debug", Double.toString(latitude));
        /*Toast.makeText(
                this.getApplicationContext(),
                longitude + " " + latitude,
                Toast.LENGTH_LONG
        );*/
        //33.6424, -117.8417
        alert.fetchAircraftData(MainActivity.this, 50, 15);

        //refresh apidata
        //apidata.fetchAircraftData(33.6424, -117.8417, 20);

        /*Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, MapsMarkerActivity.class);
                Toast.makeText(getApplicationContext(), "Toast", Toast.LENGTH_SHORT).show();
                //intent1.putExtra(ALERT, alert);
                startActivity(intent1);
            }
        });*/

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_preview);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }


        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.startAlertDetection();
                TextView mytxt=(TextView ) findViewById(R.id.textView2);
                mytxt.setText("Current Status: Detecting");
                detecting = true;
                findTextViewByID(R.id.NearestAircraft).setText("Nearest Aircraft: Fetching");
            }
        });
        Button cancelbutton = findViewById(R.id.cancelbutton);
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.cancelFetchingData();
                TextView mytxt=(TextView ) findViewById(R.id.textView2);
                mytxt.setText("Current Status: Idle");
                detecting = false;
                TextView nearestAircraftTxt=(TextView ) findViewById(R.id.NearestAircraft);
                nearestAircraftTxt.setText("Nearest Aircraft: N/A");
                findTextViewByID(R.id.NearestAircraft).setText("Nearest Aircraft: N/A");
                findTextViewByID(R.id.SafetyLevel).setText("Safety Level: N/A");
            }
        });
        Button alarmButton = findViewById(R.id.button1);
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.setAlarm();
                alarm_on = !alarm_on;
                TextView alarmText=(TextView ) findViewById(R.id.Alert);
                if (alarm_on)
                {
                    alarmButton.setText("Turn off alarm");
                    alarmText.setText("Alarm: On");
                }
                else
                {
                    alarmButton.setText("Turn on alarm");
                    alarmText.setText("Alarm: Off");
                }

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng uci = new LatLng(33.6424, -117.8417);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uci, 10));
        alert.setFragmentMap(mMap);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                View fragmentMap = findViewById(R.id.map_preview);
                View returnButtonView = findViewById(R.id.returnButton);

                makeFragmentFullScreen(fragmentMap);
                returnButtonView.setVisibility(returnButtonView.VISIBLE);
                Button returnButton = findViewById(R.id.returnButton);
                returnButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        resizeView(fragmentMap, 411, 256, 404);//256
                        findViewById(R.id.returnButton).setVisibility(returnButtonView.INVISIBLE);
                    }
                });
            }
        });
    }

    private void resizeView(View f, int newWidth, int newHeight, int newTopAlignment) {
        if (f != null) {
            int w = (int) Math.floor(dpToPx(newWidth));
            int h = (int) Math.floor(dpToPx(newHeight));
            //int top_margin = (int) Math.floor(dpToPx(newTopAlignment));
            View view = f;



            ConstraintLayout.LayoutParams p = new ConstraintLayout.LayoutParams(w, h);
            //p.topToTop = R.id.parent;
            //p.bottomToBottom = R.id.parent;
            p.startToStart = R.id.parent;
            //p.endToEnd = R.id.parent;
            //p.verticalBias = (float) 0.831;
            p.topMargin = (int) Math.floor(dpToPx(3f));
            p.horizontalBias = (float) 0;
            p.topToBottom = R.id.Alert;
            view.setLayoutParams(p);
        }
    }

    private float dpToPx(float dip)
    {
        Resources r = getResources();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
        );
        return px;
    }

    private void makeFragmentFullScreen(View fragmentView) {
        if (fragmentView != null) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            fragmentView.setLayoutParams(params);
        }
    }

    public void setLatLon(double lat, double lon)
    {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat()
    {
        return lat;
    }

    public double getLon()
    {
        return lon;
    }
    public TextView findTextViewByID(int id)
    {
        return (TextView ) findViewById(id);
    }

}