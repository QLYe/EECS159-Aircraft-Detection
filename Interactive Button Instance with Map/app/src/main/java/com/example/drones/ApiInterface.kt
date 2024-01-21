// ApiInterface.kt
package com.example.drones

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("v2/point/{lat}/{lon}/{radius}")
    fun getAircraftData(
        @Path("lat") latitude: Double,
        @Path("lon") longitude: Double,
        @Path("radius") radius: Double
    ): Call<AircraftResponse>
}

data class AircraftResponse(
    val ac: List<Aircraft>
)

data class Aircraft(
    val lat: Double,
    val lon: Double,
    val speed: Double
)