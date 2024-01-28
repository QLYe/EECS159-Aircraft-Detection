package com.example.drones

import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Timer
import java.util.TimerTask

class Alert {
    val timer = Timer()
    var alert_on = false
    var map_on = false
    fun fetchAircraftData(mainActivity:MainActivity, lat: Double, lon: Double, radius: Double) {
            val timerTask = object : TimerTask() {
                override fun run() {
                    if (alert_on) {
                        val call = RetrofitClient.instance.getAircraftData(lat, lon, radius)
                        call.enqueue(object : Callback<AircraftResponse> {
                            override fun onResponse(
                                call: Call<AircraftResponse>,
                                response: Response<AircraftResponse>
                            ) {

                                response.body()?.ac?.forEach { aircraft ->
                                    val alat: String = String.format("%.3f", aircraft.lat)
                                    val alon: String = String.format("%.3f", aircraft.lon)
                                    Toast.makeText(
                                        mainActivity.getApplicationContext(),
                                        alat + " " + alon,
                                        Toast.LENGTH_SHORT
                                    )
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

}