package com.pepe.app.safesurfing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapProviderType;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        WeatherConfig config = new WeatherConfig();
        config.unitSystem = WeatherConfig.UNIT_SYSTEM.M;
        config.ApiKey= "d488064b89a68d822fd0f952df6b7c83";
       // config.lang = "en"; // If you want to use english
        //config.maxResult = 5; // Max number of cities retrieved
        //config.numDays = 6; // Max num of days in the forecast
        // Sample WeatherLib client init
        try {
            WeatherClient client = (new WeatherClient.ClientBuilder()).attach(this)
                    .httpClient(com.survivingwithandroid.weather.lib.client.volley.WeatherClientDefault.class)
                    .provider(new OpenweathermapProviderType())
                    .config(config)
                    .build();

            client.getCurrentCondition(new WeatherRequest(Weather.getInstance().getLongitud(),Weather.getInstance().getLatitud()), new WeatherClient.WeatherEventListener() {
                @Override
                public void onWeatherRetrieved(CurrentWeather currentWeather) {
                    float currentTemp = currentWeather.weather.temperature.getTemp();
                    float currentWind = currentWeather.weather.wind.getSpeed();
                    Float currentWindDir = currentWeather.weather.wind.getDeg();
                    int windDirInt=0;
                    if(currentWindDir!=null&&!currentWindDir.isNaN()) {
                        windDirInt = Integer.valueOf(String.valueOf(currentWindDir).substring(0,String.valueOf(currentWindDir).indexOf(".")))%360;
                    }
                    int numDirection = windDirInt/45;
                    String windDirStr ="N";
                    switch(numDirection){
                        case 0:
                            windDirStr="E";
                            break;
                        case 1:
                            windDirStr="NE";
                            break;
                        case 2:
                            windDirStr="N";
                            break;
                        case 3:
                            windDirStr="NO";
                            break;
                        case 4:
                            windDirStr="O";
                            break;
                        case 5:
                            windDirStr="SO";
                            break;
                        case 6:
                            windDirStr="S";
                            break;
                        case 7:
                            windDirStr="SE";
                            break;
                    }
                    Weather.getInstance().setWind(currentWind);
                    Weather.getInstance().setWindDirection(windDirStr);
                    Weather.getInstance().setCiudad(currentWeather.weather.location.getCity());

                    Log.d("PEPEWL", "City ["+currentWeather.weather.location.getCity()+"] Current temp ["+currentTemp+"]"+" Current tWind ["+currentWind+"]"+" Current tWind ["+windDirStr+"]");
                }

                @Override
                public void onWeatherError(WeatherLibException e) {
                    Log.d("PEPEWL", "Weather Error - parsing data");
                    e.printStackTrace();
                }

                @Override
                public void onConnectionError(Throwable throwable) {
                    Log.d("PEPEWL", "Connection error");
                    throwable.printStackTrace();
                }
            });
        }
        catch (Throwable t) {
            t.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        super.onBackPressed();
    }
}
