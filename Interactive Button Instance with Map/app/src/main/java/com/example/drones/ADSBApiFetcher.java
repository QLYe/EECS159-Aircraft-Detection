package com.example.drones;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URI;
import java.net.UnknownServiceException;
import java.util.Scanner;

public class ADSBApiFetcher {

    // Replace these with your desired values
    private static final double LATITUDE = 33.6424;
    private static final double LONGITUDE = -117.8417;
    private static final int RADIUS = 10; // in nm

    String fetchAircraftData(double lat, double lon, int radius) {

        try {
            URI uri = new URI("https", null, "api.adsb.one", -1, "/v2/point/"
                    + lat + "/"
                    + lon + "/"
                    + radius, null, null);

            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            System.out.println(responseCode);
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
                return inline.toString();
                // Process the JSON data as needed
            }
        } catch (Exception e) {
            //e.printStackTrace();
            if(e.getMessage() == null){
                if(e instanceof SocketTimeoutException) {return "STE";}
                else if (e instanceof ProtocolException) {return "PE";}
                else if (e instanceof MalformedURLException) {return "ME";}
                else if (e instanceof URISyntaxException) {return "USE";}
                else if (e instanceof IOException) {return "IO";}
                return e.toString();
            }
            String b = e.getMessage();
                return b.substring(0,3);
        }
    }
}