package pl.damianj.weatherapp.util;

import android.util.Log;

import java.util.HashMap;
import java.util.stream.Collectors;

import pl.damianj.weatherapp.model.oneapi.WeatherForecast;
import pl.damianj.weatherapp.viewmodel.WeatherDataViewModel;

public class UnitConverter {

    private static UnitConverter unitConverter = null;

    private UnitConverter() {

    }

    public static UnitConverter getUnitCoverter() {
        if (unitConverter == null ) {
            unitConverter = new UnitConverter();
        }
        return unitConverter;
    }

    public void convertMetricToImperial(WeatherDataViewModel viewModel) {
        WeatherForecast weatherForecast = viewModel.getWeatherData().getValue();
        weatherForecast.getCurrent().setWindSpeed(weatherForecast.getCurrent().getWindSpeed() * 2.237f);
        weatherForecast.getCurrent().setTemp(((weatherForecast.getCurrent().getTemp() * (9f/5f)) + 32));

        weatherForecast.getDaily()
                .forEach(x -> x.setWindSpeed((weatherForecast.getDaily().get(weatherForecast.getDaily().indexOf(x)).getWindSpeed() * 2.237f)));
        weatherForecast.getDaily()
                .forEach(x -> x.getTemp().setDay(((weatherForecast.getDaily().get(weatherForecast.getDaily().indexOf(x)).getTemp().getDay() * (9f/5f)) + 32)));
        viewModel.setUnitSystem( new HashMap<String, String>() {{
            put("wind","miles/h");
            put("temp", "F");
        }});
        viewModel.setWeatherData(weatherForecast);
    }

    public void convertImperialToMetric(WeatherDataViewModel viewModel) {
        WeatherForecast weatherForecast = viewModel.getWeatherData().getValue();
        weatherForecast.getCurrent().setWindSpeed(weatherForecast.getCurrent().getWindSpeed() / 2.237f);
        weatherForecast.getCurrent().setTemp(((weatherForecast.getCurrent().getTemp() - 32) * (5f/9f)));
        weatherForecast.getDaily()
                .forEach(x -> x.setWindSpeed((weatherForecast.getDaily().get(weatherForecast.getDaily().indexOf(x)).getWindSpeed() / 2.237f)));
        weatherForecast.getDaily()
                .forEach(x -> x.getTemp().setDay(((weatherForecast.getDaily().get(weatherForecast.getDaily().indexOf(x)).getTemp().getDay() - 32) * (5f/9f))));
        viewModel.setUnitSystem( new HashMap<String, String>() {{
            put("wind","m/s");
            put("temp", "C");
        }});
        viewModel.setWeatherData(weatherForecast);
    }
}
