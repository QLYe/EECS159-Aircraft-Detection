package com.example.drones

import android.widget.Toast
import android.media.MediaPlayer;
import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Timer
import java.util.TimerTask


class Alert {
    private var mediaPlayer: MediaPlayer? = null
    val timer = Timer()
    var alert_on = false
    var map_on = false
    var fragment_map : GoogleMap?=null
    var fragment_map_set = false
    var fragment_marker_on = false
    var musicPlayable = true
    fun fetchAircraftData(mainActivity:MainActivity, radius: Double) {
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
                    val call = RetrofitClient.instance.getAircraftData(mainActivity.getLat(), mainActivity.getLon(), radius)
                    call.enqueue(object : Callback<AircraftResponse> {
                        override fun onResponse(
                            call: Call<AircraftResponse>,
                            response: Response<AircraftResponse>
                        ) {
                            musicPlayable = true
                            response.body()?.ac?.forEach { aircraft ->
                                if (alert_on) {
                                    alertFunction(aircraft, mainActivity, 5)
                                }
                                if (fragment_marker_on) {
                                    fragmentMapFunction(aircraft)
                                    fragment_map?.addMarker(
                                        MarkerOptions().position(LatLng(mainActivity.getLat(), mainActivity.getLon())).title("your position")
                                    )
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

    fun alertFunction(aircraft: Aircraft,mainActivity:MainActivity, alertRange : Int){
        val alat: String = String.format("%.3f", aircraft.lat)
        val alon: String = String.format("%.3f", aircraft.lon)
        var d = distance(mainActivity.getLat(), mainActivity.getLon(), aircraft.lat, aircraft.lon)
        if (d < alertRange)
        {
            Toast.makeText(
                mainActivity.getApplicationContext(),
                alat + " " + alon,
                Toast.LENGTH_SHORT
            )
                .show()
            if (musicPlayable)
            {
                playAlertSound(mainActivity)
                musicPlayable = false
            }
        }
    }

    fun fragmentMapFunction(aircraft: Aircraft){
        val one_aircraft = LatLng(aircraft.lat, aircraft.lon)
        fragment_map?.addMarker(
            MarkerOptions().position(one_aircraft).title("one aircraft").icon(BitmapDescriptorFactory.fromAsset("airplane_icon.png"))
        )
    }

    fun playAlertSound(context: Context) {

        mediaPlayer?.release()

        val mediaPlayer: MediaPlayer = MediaPlayer.create(context, R.raw.alert)
        mediaPlayer.setOnCompletionListener {
            it.release()
        }
        mediaPlayer.start()
    }

    private fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist = dist * 60 * 1.1515
        return dist / 1.1508
    }

    private fun deg2rad(deg: Double): Double {
        return (deg * Math.PI / 180.0)
    }

    private fun rad2deg(rad: Double): Double {
        return (rad * 180.0 / Math.PI)
    }
}