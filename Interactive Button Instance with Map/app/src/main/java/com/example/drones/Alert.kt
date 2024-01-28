package com.example.drones

import android.widget.Toast
<<<<<<< Updated upstream
=======
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory
>>>>>>> Stashed changes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Timer
import java.util.TimerTask

class Alert {
    val timer = Timer()
    fun fetchAircraftData(mainActivity:MainActivity, lat: Double, lon: Double, radius: Double) {

        val timerTask = object : TimerTask() {
            override fun run() {

                val call = RetrofitClient.instance.getAircraftData(lat, lon, radius)
                call.enqueue(object : Callback<AircraftResponse> {
                    override fun onResponse(call: Call<AircraftResponse>, response: Response<AircraftResponse>) {

                        response.body()?.ac?.forEach { aircraft ->
                            val alat: String = String.format("%.3f", aircraft.lat)
                            val alon: String = String.format("%.3f", aircraft.lon)
                            Toast.makeText(mainActivity.getApplicationContext(), alat+" "+alon, Toast.LENGTH_SHORT)
                                .show()
                        }
                        //cancelFetchingData()
                    }

                    override fun onFailure(call: Call<AircraftResponse>, t: Throwable) {
                        //Toast.makeText(GoogleMap, "fail to fetch the data", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
        timer.scheduleAtFixedRate(timerTask, 0, 5000)
    }
    fun cancelFetchingData()
    {
        timer.cancel();
        timer.purge();
    }

<<<<<<< Updated upstream
=======
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
            MarkerOptions().position(one_aircraft).title("one aircraft").icon(BitmapDescriptorFactory.fromAsset("airplane_icon.png")))
    }
>>>>>>> Stashed changes
}