package pl.damianj.weatherapp.storage;


import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Optional;
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

    public void saveCityToFile(WeatherForecast weatherForecast) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String jsonString = gson.toJson(weatherForecast);
        editor.putString(weatherForecast.getCityName(), jsonString);
        editor.commit();
    }

    public void removeCity(String city) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(city);
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

    public void updateIfSaved(WeatherForecast weatherForecast) {
        Set<String> savedCities = sharedPreferences.getAll().keySet();
        Optional<String> isSaved = savedCities.stream()
                .filter(x -> x.equals(weatherForecast.getCityName()))
                .findAny();
        if (isSaved.isPresent()) {
            saveCityToFile(weatherForecast);
        }
    }
}
