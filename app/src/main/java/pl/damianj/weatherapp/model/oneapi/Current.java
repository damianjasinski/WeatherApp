package pl.damianj.weatherapp.model.oneapi;
import com.google.gson.annotations.SerializedName;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Current {

    private LocalTime request_time;
    private Integer dt;
    private Integer sunrise;
    private Integer sunset;
    private Double temp;
    private Double feels_like;
    private Integer pressure;
    private Integer humidity;
    private Double dewPoint;
    private Double uvi;
    private Integer clouds;
    private Integer visibility;
    @SerializedName("wind_speed")
    private Double windSpeed;
    private Integer wind_deg;
    private List<Weather> weather = null;
    private Rain rain;

    public LocalTime getRequestTime() {
        return request_time;
    }

    public void setRequestTime(LocalTime time) {
        request_time = time;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getFeelsLike() {
        return feels_like;
    }

    public void setFeelsLike(Double feels_like) {
        this.feels_like = Current.this.feels_like;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(Double dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Double getUvi() {
        return uvi;
    }

    public void setUvi(Double uvi) {
        this.uvi = uvi;
    }

    public Integer getClouds() {
        return clouds;
    }

    public void setClouds(Integer clouds) {
        this.clouds = clouds;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = Current.this.windSpeed;
    }

    public Integer getWindDeg() {
        return wind_deg;
    }

    public void setWindDeg(Integer wind_deg) {
        this.wind_deg = wind_deg;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }


}