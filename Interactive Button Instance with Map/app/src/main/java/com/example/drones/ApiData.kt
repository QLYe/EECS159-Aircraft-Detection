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
import android.util.Log

class ApiData (){
    private val result = Vector<List<Double>>()
    fun fetchAircraftData(lat: Double, lon: Double, radius: Double) {
        val timer = Timer()
        val timerTask = object : TimerTask() {
            override fun run() {
                //may have sync problem
                /*for (a in result){
                    Log.d("api test", a[0].toBigDecimal().toPlainString())
                }*/
                result.clear()
                val call = RetrofitClient.instance.getAircraftData(lat, lon, radius)
                call.enqueue(object : Callback<AircraftResponse> {
                    override fun onResponse(call: Call<AircraftResponse>, response: Response<AircraftResponse>) {

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

    fun getResult(): Vector<List<Double>> {
        Log.d("get result test test", "inside get result")
        for (a in result){
            Log.i("get result test test", a[0].toBigDecimal().toPlainString())
        }
        return result
    }
    fun getFirstLat(): Double{
        var b = -1.0
        for (a in result){
            Log.d("first lat in api test", b.toString())
            b =  a[0]
        }
        return b
    }


}