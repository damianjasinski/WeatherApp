package pl.damianj.weatherapp.storage;


import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Set;

import pl.damianj.weatherapp.model.oneapi.WeatherForecast;

public class StorageService {
    private static StorageService storageService = null;
    private SharedPreferences sharedPreferences;

    private StorageService(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public static void createInstance(SharedPreferences sharedPreferences) {
        if (storageService == null) {
            storageService = new StorageService(sharedPreferences);
        }
    }

    public static StorageService getInstance() {
        if (storageService != null) {
            return storageService;
        }
        return null;
    }

    public void saveToFile(WeatherForecast weatherForecast) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String jsonString = gson.toJson(weatherForecast);
        editor.putString(weatherForecast.getCityName(), jsonString);
        editor.commit();
    }

    public Set<String> getAllKeys() {
        return sharedPreferences.getAll().keySet();
    }

    public WeatherForecast loadWeatherData(String city) {
        String weatherForecast = sharedPreferences.getString(city, "Not found");
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.fromJson(weatherForecast, WeatherForecast.class);
    }

    public void saveLastSelected(WeatherForecast weatherForecast) {
        if (weatherForecast != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

            String jsonString = gson.toJson(weatherForecast);
            editor.putString("LastSelected", jsonString);
            editor.commit();
        }
    }

    public WeatherForecast loadLastSelected() {
        String weatherForecast = sharedPreferences.getString("LastSelected", "NotFound");
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        if (!"NotFound".equals(weatherForecast)) {
            return gson.fromJson(weatherForecast, WeatherForecast.class);
        }
        return null;
    }

}
