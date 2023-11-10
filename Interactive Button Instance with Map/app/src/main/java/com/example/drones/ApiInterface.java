package com.example.drones;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import java.util.List;

public interface ApiInterface {
    @GET("v2/point/{lat}/{lon}/{radius}")
    Call<AircraftResponse> getAircraftData(
            @Path("lat") double latitude,
            @Path("lon") double longitude,
            @Path("radius") double radius
    );
}

    class AircraftResponse {
        private List<Aircraft> ac;

        // Getters and Setters
        public List<Aircraft> getAc() {
            return ac;
        }

        public void setAc(List<Aircraft> ac) {
            this.ac = ac;
        }
    }

    class Aircraft {
        private double lat;
        private double lon;
        private double speed;

        // Getters and Setters
        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }
    }
