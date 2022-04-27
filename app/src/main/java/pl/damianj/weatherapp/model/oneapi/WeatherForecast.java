package pl.damianj.weatherapp.model.oneapi;

import java.util.List;

public class WeatherForecast {

    private Double lat;
    private Double lon;
    private String timezone;
    private Integer timezoneOffset;
    private Current current;
    private Rain rain;
    private List<Daily> daily;


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


    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public List<Daily> getDaily() {
        return daily;
    }

    public void setDaily(List<Daily> dailyForecast) {
        this.daily = daily;
    }
}
