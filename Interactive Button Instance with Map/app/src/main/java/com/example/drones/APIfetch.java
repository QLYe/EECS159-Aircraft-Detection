package com.example.drones;

import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.util.Scanner;

public class APIfetch {

    // Replace these with your desired values
    private static final double LATITUDE = 33.6424;
    private static final double LONGITUDE = -117.8417;
    private static final int RADIUS = 50; // in nm

    String fetch(double lat, double lon, int radius) {
        String a = "a";
        try {

            //String urlString = "https://api.adsb.one/v2/point/" + lat + "/" + lon + "/" + radius;
            URI uri = new URI("https", null, "api.adsb.one", -1, "/v2/point/"
                    + lat + "/"
                    + lon + "/"
                    + radius, null, null);

            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            a = "b";
            connection.connect();

            int responseCode = connection.getResponseCode();
            a = Integer.toString(responseCode);
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder inline = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }

                scanner.close();

                //JSONObject data = new JSONObject(inline.toString());
                System.out.println(inline.toString());
                // Process the JSON data as needed
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }
}
