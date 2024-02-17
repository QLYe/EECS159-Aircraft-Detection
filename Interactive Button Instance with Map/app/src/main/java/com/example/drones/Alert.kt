package com.example.drones

import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Timer
import java.util.TimerTask


class Alert {
    val timer = Timer()
    var alert_on = false
    var map_on = false
    var fragment_map : GoogleMap?=null
    var fragment_map_set = false
    var fragment_marker_on = false
    fun fetchAircraftData(mainActivity:MainActivity, lat: Double, lon: Double, radius: Double) {
            val timerTask = object : TimerTask() {
                override fun run() {

                    if (alert_on or fragment_marker_on) {
                        if (fragment_map_set && fragment_map != null) {
                            mainActivity.runOnUiThread {
                                fragment_map?.clear()
                            }
                            /**
                            fragment_map?.addMarker(
                                MarkerOptions().position(LatLng(lat, lon)).title("My Position")
                            )
                            **/
                        }
                        val call = RetrofitClient.instance.getAircraftData(lat, lon, radius)
                        call.enqueue(object : Callback<AircraftResponse> {
                            override fun onResponse(
                                call: Call<AircraftResponse>,
                                response: Response<AircraftResponse>
                            ) {

                                response.body()?.ac?.forEach { aircraft ->
                                    if (alert_on) {
                                        alertFunction(aircraft, mainActivity)
                                    }
                                    if (fragment_marker_on) {
                                        fragmentMapFunction(aircraft)
                                    }
                                }
                            }

                            override fun onFailure(call: Call<AircraftResponse>, t: Throwable) {
                                //Toast.makeText(GoogleMap, "fail to fetch the data", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                }
            }
            timer.scheduleAtFixedRate(timerTask, 0, 5000)
    }
    fun startAlertDetection()
    {
        alert_on = true
    }

    fun cancelFetchingData()
    {
        /*timer.cancel();
        timer.purge();*/
        alert_on = false
    }

    fun setFragmentMap(new_fragment_map: GoogleMap){
        fragment_map = new_fragment_map
        fragment_map_set = true
        startFragmentMarkerAdding()

    }

    fun startFragmentMarkerAdding(){
        fragment_marker_on = true
    }

    fun alertFunction(aircraft: Aircraft,mainActivity:MainActivity){
        val alat: String = String.format("%.3f", aircraft.lat)
        val alon: String = String.format("%.3f", aircraft.lon)
        Toast.makeText(
            mainActivity.getApplicationContext(),
            alat + " " + alon,
            Toast.LENGTH_SHORT
        )
            .show()
    }

    fun fragmentMapFunction(aircraft: Aircraft){
        val one_aircraft = LatLng(aircraft.lat, aircraft.lon)
        fragment_map?.addMarker(
            MarkerOptions().position(one_aircraft).title("one aircraft")
        )
    }
}