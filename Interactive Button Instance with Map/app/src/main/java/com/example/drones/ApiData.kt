package com.example.drones

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Timer
import java.util.TimerTask
import java.util.Vector

class ApiData (val lat: Double, val lon: Double, val rad: Double){
    private val result = Vector<List<Double>>()
    fun fetchAircraftData(googleMap: GoogleMap, lat: Double, lon: Double, radius: Double) {
        val timer = Timer()
        val timerTask = object : TimerTask() {
            override fun run() {
                //may have sync problem
                result.clear()
                val call = RetrofitClient.instance.getAircraftData(lat, lon, radius)
                call.enqueue(object : Callback<AircraftResponse> {
                    override fun onResponse(call: Call<AircraftResponse>, response: Response<AircraftResponse>) {
                        googleMap.clear()

                        response.body()?.ac?.forEach { aircraft ->
                            var templist = listOf(aircraft.lat, aircraft.lon, aircraft.speed)
                            result.addElement(templist)

                        }
                    }

                    override fun onFailure(call: Call<AircraftResponse>, t: Throwable) {

                        //Toast.makeText(GoogleMap, "fail to fetch the data", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }


        timer.scheduleAtFixedRate(timerTask, 0, 5000)
    }
}