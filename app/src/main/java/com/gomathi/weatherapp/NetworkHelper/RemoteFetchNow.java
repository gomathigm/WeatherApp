package com.gomathi.weatherapp.NetworkHelper;

import android.content.Context;


import com.gomathi.weatherapp.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class RemoteFetchNow {


//http://api.openweathermap.org/data/2.5/weather?q=charlotte,usa&APPID=bc060110b2034bab07bbfef2de1b9ba7

    private static final String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";


    public static JSONObject getJSON(Context context, String city) {
        try {

            URL url = new URL(String.format(OPEN_WEATHER_MAP_API, city));
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.addRequestProperty("x-api-key",
                    context.getString(R.string.open_weather_maps_app_id));

            // connection.addRequestProperty("x-api-key","bc060110b2034bab07bbfef2de1b9ba7");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp = "";
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            //System.out.println("RajayJsonToString:" + json.toString());

            // This value will be 404 if the request was not
            // successful
            if (data.getInt("cod") != 200) {
                return null;
            }

            return data;
        } catch (Exception e) {
            return null;
        }
    }
}