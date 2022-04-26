package pl.damianj.weatherapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import pl.damianj.weatherapp.model.Coord;
import pl.damianj.weatherapp.model.WeatherData;
import pl.damianj.weatherapp.model.oneapi.WeatherForecast;
import pl.damianj.weatherapp.service.WeatherDataService;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherDataViewModel extends ViewModel {
    private MutableLiveData<String> cityName = new MutableLiveData<>();
    private MutableLiveData<WeatherForecast> weatherData = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private Coord coord;


    private final WeatherDataService service = new retrofit2.Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherDataService.class);

    public LiveData<String> getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName.setValue(cityName);
    }

    public void setWeatherData(WeatherForecast weatherData) {
        this.weatherData.setValue(weatherData);
    }

    public void setError(String error) {
        this.error.setValue(error);
    }

    public LiveData<String> getError() {
        return this.error;
    }


    public LiveData<WeatherForecast> getWeatherData() {
        return this.weatherData;
    }

    public Coord getCoord() {
        return this.coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

}
