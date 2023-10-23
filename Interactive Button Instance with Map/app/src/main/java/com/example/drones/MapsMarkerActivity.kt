package com.example.drones

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
// [START maps_marker_on_map_ready]
public class MapsMarkerActivity : AppCompatActivity(), OnMapReadyCallback {

    // [START_EXCLUDE]
    // [START maps_marker_get_map_async]
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps)

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish() // This will close the current activity and return to the previous one
        }
    }
    // [END maps_marker_get_map_async]
    // [END_EXCLUDE]

    // [START maps_marker_on_map_ready_add_marker]
    override fun onMapReady(googleMap: GoogleMap) {
        val uci = LatLng(33.6424, -117.8417)
        googleMap.addMarker(
            MarkerOptions()
                .position(uci)
                .title("Marker at UCI")
        )
        val sna = LatLng(33.6741, -117.869)
        googleMap.addMarker(
            MarkerOptions()
                .position(sna)
                .title("Marker at SNA")
        )
        val daiso = LatLng(33.706270, -117.783910)
        googleMap.addMarker(
            MarkerOptions()
                .position(daiso)
                .title("Marker at Daiso")
        )
        // [START_EXCLUDE silent]
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(uci))
        // [END_EXCLUDE]
    }
    // [END maps_marker_on_map_ready_add_marker]
}