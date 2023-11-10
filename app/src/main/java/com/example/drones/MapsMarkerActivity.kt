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
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call
import java.util.Timer
import java.util.TimerTask
/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
// [START maps_marker_on_map_ready]
class MapsMarkerActivity : AppCompatActivity(), OnMapReadyCallback {

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
        googleMap.addMarker(MarkerOptions().position(uci).title("Marker at UCI"))

        val sna = LatLng(33.6741, -117.869)
        googleMap.addMarker(MarkerOptions().position(sna).title("Marker at SNA"))

        val daiso = LatLng(33.706270, -117.783910)
        googleMap.addMarker(MarkerOptions().position(daiso).title("Marker at Daiso"))

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(uci))

        fetchAircraftData(googleMap, 33.6424, -117.8417, 100.0)

        googleMap.setOnMarkerClickListener { marker ->
            marker.showInfoWindow()
            true
        }
    }


    private fun fetchAircraftData(googleMap: GoogleMap, lat: Double, lon: Double, radius: Double) {
        val timer = Timer()
        val timerTask = object : TimerTask() {
            override fun run() {

                val call = RetrofitClient.instance.getAircraftData(lat, lon, radius)
                call.enqueue(object : Callback<AircraftResponse> {
                    override fun onResponse(call: Call<AircraftResponse>, response: Response<AircraftResponse>) {

                        googleMap.clear()

                        response.body()?.ac?.forEach { aircraft ->
                            googleMap.addMarker(
                                    MarkerOptions()
                                            .position(LatLng(aircraft.lat, aircraft.lon))
                                            .title("Speed: ${aircraft.speed}")
                                            .snippet("Lat: ${aircraft.lat}, Lon: ${aircraft.lon}")
                            )
                        }
                    }

                    override fun onFailure(call: Call<AircraftResponse>, t: Throwable) {

                        Toast.makeText(this@MapsMarkerActivity, "fail to fetch the data", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }


        timer.scheduleAtFixedRate(timerTask, 0, 2000)
    }
}
