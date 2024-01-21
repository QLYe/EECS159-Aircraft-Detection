package com.example.drones

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Timer
import java.util.TimerTask
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast;


/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
// [START maps_marker_on_map_ready]
class MapsMarkerActivity : AppCompatActivity(), OnMapReadyCallback {
    // [START_EXCLUDE]
    // [START maps_marker_get_map_async]
    //private var apidata = ApiData()
    val APIDATA_CODE: String? = "apidata"
    private var firstLat = 0.0
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
        if(intent.hasExtra(MainActivity.APIDATA_CODE)){
            //getting the Parcelable object into the employeeModel
            //employeeModel= intent.getParcelableExtra(MainActivity.SECOND_ACTIVITY_CODE)
            firstLat = intent.getDoubleExtra(APIDATA_CODE, -1.0)
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
        //apidata.fetchAircraftData(33.6424, -117.8417, 20.0)

        Log.d("first lat test", firstLat.toString())
        //fetchAircraftData(googleMap,33.6424, -117.8417, 20.0, this)
        /*Timer().schedule(
            object : TimerTask() {
                override fun run() {

                    fetchAircraftData(googleMap, apidata)
                }
            }, 2000
        )*/


        googleMap.setOnMarkerClickListener { marker ->
            marker.showInfoWindow()
            true
        }
    }



}

/*fun fetchAircraftData(googleMap: GoogleMap, apidata: ApiData) {
    val timer = Timer()
    val timerTask = object : TimerTask() {
        override fun run() {

            var result = apidata.getResult()
            //googleMap.clear()
            Log.d("fetch test", "haha")

                Timer().schedule(
                    object : TimerTask() {
                        override fun run() {
                            for (aircraft in result) {
                                Log.d("first for test", "aaaa")
                                Log.d("first for test", aircraft[0].toBigDecimal().toPlainString())
                            }
                            //if you need some code to run when the delay expires
                        }
                    }, 1000
                )
            Timer().schedule(
                object : TimerTask() {
                    override fun run() {
                        for (aircraft in result) {
                            Log.d("second for test", "aaaa")
                            //Log.d("second for test", "you!!!" + aircraft[0].toBigDecimal().toPlainString())
                            googleMap.addMarker(
                                MarkerOptions()
                                    .position(LatLng(aircraft[0], aircraft[1]))
                                    .title("Speed: ${aircraft[2]}")
                                //.snippet("Lat: ${aircraft[0]}, Lon: ${aircraft[1]}")
                            )

                            //Log.d("fetch test", aircraft[0].toBigDecimal().toPlainString())
                        }
                        //if you need some code to run when the delay expires
                    }
                }, 1000
            )


        }
    }
    timer.scheduleAtFixedRate(timerTask, 0, 5000)
}*/
/*fun fetchAircraftData(googleMap: GoogleMap, lat: Double, lon: Double, radius: Double, mapsMarkerActivity: MapsMarkerActivity) {
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
                        Toast.makeText(mapsMarkerActivity.getApplicationContext(), "Warining, aircraft within 20nm", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<AircraftResponse>, t: Throwable) {

                    //Toast.makeText(GoogleMap, "fail to fetch the data", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }


    timer.scheduleAtFixedRate(timerTask, 0, 5000)
}*/