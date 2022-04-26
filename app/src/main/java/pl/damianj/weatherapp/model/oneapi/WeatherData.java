package pl.damianj.weatherapp.model.oneapi;

import java.util.List;

public class WeatherData {

    private Double lat;
    private Double lon;
    private String timezone;
    private Integer timezoneOffset;
    private Current current;
    private Weather weather;
    private Rain rain;
    private List<Daily> dailyForecast;


    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Integer getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(Integer timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public List<Daily> getDailyForecast() {
        return dailyForecast;
    }

    public void setDailyForecast(List<Daily> dailyForecast) {
        this.dailyForecast = dailyForecast;
    }
}
