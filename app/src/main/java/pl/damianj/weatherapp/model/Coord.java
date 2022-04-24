
package pl.damianj.weatherapp.model;

public class Coord {

    private Double lon;
    private Double lat;

    public Coord(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Coord(Coord coord) {
        this.lat = coord.getLat();
        this.lon = coord.getLon();
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "Coord{" +
                "lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}
