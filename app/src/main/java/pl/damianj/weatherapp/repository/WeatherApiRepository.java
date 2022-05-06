package pl.damianj.weatherapp.repository;

import android.util.Log;

import java.time.LocalDateTime;
import java.util.List;


import pl.damianj.weatherapp.model.oneapi.WeatherForecast;
import pl.damianj.weatherapp.service.WeatherDataService;
import pl.damianj.weatherapp.storage.StorageService;
import pl.damianj.weatherapp.viewmodel.WeatherDataViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import pl.damianj.weatherapp.model.oneapi.Coord;

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

    public void getWeatherData(Coord coords, WeatherDataViewModel viewModel) {
        weatherDataService.getWeatherData(coords.getLat(), coords.getLon()).enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                Log.i("RETROFIT-WEATHERDATA", response.body().toString());
                WeatherForecast weatherData = response.body();
                weatherData.setCityName(coords.getCityName());
                viewModel.setWeatherData(weatherData);
                StorageService.getInstance().updateIfSaved(viewModel.getWeatherData().getValue());
            }

            @Override
            public void onFailure(Call<WeatherForecast> call, Throwable t) {
                Log.e("RETROFIT", t.getMessage());
            }
        });
    }

    public void getCityCoords(String cityName, WeatherDataViewModel viewModel)  {
        weatherDataService.getCityGeo(cityName).enqueue(new Callback<List<Coord>>() {
            @Override
            public void onResponse(Call<List<Coord>> call, Response<List<Coord>> response) {
                if (response.body().size() == 0) {
                   viewModel.setError("City not found");
                }
                else {
                    Log.i("RETROFIT-GEOCITY", response.body().toString());
                    Coord coord = new Coord(response.body().get(0));
                    coord.setCityName(cityName);
                    viewModel.setCoord(coord);
                    viewModel.setCityName(cityName);
                }
            }
            @Override
            public void onFailure(Call<List<Coord>> call, Throwable t) {
                Log.e("RETROFIT", t.getMessage());
            }
        });
    }

    public void refreshWeatherForecast(WeatherDataViewModel viewModel) {
        weatherDataService.getWeatherData(viewModel.getWeatherData().getValue().getLat(), viewModel.getWeatherData().getValue().getLon()).enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                Coord coord = new Coord(viewModel.getWeatherData().getValue().getLat(), viewModel.getWeatherData().getValue().getLon(), viewModel.getWeatherData().getValue().getCityName());
                getWeatherData(coord, viewModel);
            }

            @Override
            public void onFailure(Call<WeatherForecast> call, Throwable t) {
                Log.e("RETROFIT", t.getMessage());
            }
        });
    }

}
