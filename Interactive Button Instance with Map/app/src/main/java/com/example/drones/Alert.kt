package com.example.drones

import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Timer
import java.util.TimerTask

class Alert {
    fun fetchAircraftData(mainActivity:MainActivity, lat: Double, lon: Double, radius: Double) {
        val timer = Timer()
        val timerTask = object : TimerTask() {
            override fun run() {

                val call = RetrofitClient.instance.getAircraftData(lat, lon, radius)
                call.enqueue(object : Callback<AircraftResponse> {
                    override fun onResponse(call: Call<AircraftResponse>, response: Response<AircraftResponse>) {

                        response.body()?.ac?.forEach { aircraft ->
                            val alat: String = String.format("%.3f", aircraft.lat)
                            val alon: String = String.format("%.3f", aircraft.lon)
                            Toast.makeText(mainActivity.getApplicationContext(), alat+alon, Toast.LENGTH_SHORT)
                                .show()
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