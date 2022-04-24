package pl.damianj.weatherapp.service;

import java.util.List;

import pl.damianj.weatherapp.BuildConfig;
import pl.damianj.weatherapp.model.Coord;
import pl.damianj.weatherapp.model.WeatherData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherDataService {
//    @GET("/data/2.5/weather?appid=" + BuildConfig.API_KEY)
//    Call<WeatherData> getWeatherData(@Query("q") String cityName);

    @GET("data/2.5/weather?&appid=" + BuildConfig.API_KEY)
    Call<WeatherData> getWeatherData(@Query("lat") Double lat, @Query("lon") Double lon);

    @GET("/geo/1.0/direct?&appid=" + BuildConfig.API_KEY)
    Call<List<Coord>> getCityGeo(@Query("q") String cityName);



}
