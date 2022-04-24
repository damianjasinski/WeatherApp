package pl.damianj.weatherapp.repository;

import android.util.Log;

import java.util.List;

import pl.damianj.weatherapp.model.Coord;
import pl.damianj.weatherapp.model.WeatherData;
import pl.damianj.weatherapp.service.WeatherDataService;
import pl.damianj.weatherapp.viewmodel.WeatherDataViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherApiRepository {
    private static WeatherApiRepository weatherApiRepository = null;
    private static WeatherDataService weatherDataService;

    private WeatherApiRepository() {
        weatherDataService = new retrofit2.Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherDataService.class);
    }

    public static WeatherApiRepository getInstance() {
        if (weatherApiRepository == null)
            weatherApiRepository = new WeatherApiRepository();

        return weatherApiRepository;
    }

    public void getWeatherData(Coord coords, WeatherDataViewModel weatherDataVM) {
        weatherDataService.getWeatherData(coords.getLat(), coords.getLon()).enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                Log.i("RETROFIT-WEATHERDATA", response.body().toString());
                WeatherData weatherData = response.body();
                weatherDataVM.setWeatherData(weatherData);
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Log.e("RETROFIT-WEATHERDATA", "Request was unsuccesfull");
            }
        });
    }

    public void getGeoCity(String cityName, WeatherDataViewModel weatherDataVM) {
        weatherDataService.getCityGeo(cityName).enqueue(new Callback<List<Coord>>() {
            @Override
            public void onResponse(Call<List<Coord>> call, Response<List<Coord>> response) {
                Coord coord = new Coord(response.body().get(0));
                Log.i("RETROFIT-GEOCITY", coord.getLat().toString() + coord.getLon().toString());
                weatherDataVM.setCoord(coord);
                weatherDataVM.setCityName(cityName);
            }

            @Override
            public void onFailure(Call<List<Coord>> call, Throwable t) {
                Log.e("RETROFIT-GEOCITY", t.getMessage());
            }
        });
    }
}
