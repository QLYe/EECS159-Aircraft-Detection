package com.example.drones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alert.fetchAircraftData(MainActivity.this, 33.6424, -117.8417, 10);
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
            }
        });
        Button cancelbutton = findViewById(R.id.cancelbutton);
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.cancelFetchingData();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng uci = new LatLng(33.6424, -117.8417);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uci, 10));
        alert.setFragmentMap(mMap);
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
            int top_margin = (int) Math.floor(dpToPx(newTopAlignment));
            View view = f;



            ConstraintLayout.LayoutParams p = new ConstraintLayout.LayoutParams(w, h);
            //p.topToTop = R.id.parent;
            //p.bottomToBottom = R.id.parent;
            p.startToStart = R.id.parent;
            //p.endToEnd = R.id.parent;
            //p.verticalBias = (float) 0.831;
            p.horizontalBias = (float) 0;
            p.topToBottom = R.id.textView5;
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

}