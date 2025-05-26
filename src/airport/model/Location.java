package airport.model;

import airport.core.Prototype;

public class Location implements Prototype<Location> {
    
    private final String code;
    private final String airportId;
    private String airportName;
    private String airportCity;
    private String airportCountry;
    private double airportLatitude;
    private double airportLongitude;

    public Location(String airportId, String airportName, String airportCity, String airportCountry, double airportLatitude, double airportLongitude, String code) {
        this.airportId = airportId;
        this.airportName = airportName;
        this.airportCity = airportCity;
        this.airportCountry = airportCountry;
        this.airportLatitude = airportLatitude;
        this.airportLongitude = airportLongitude;
        this.code = code;
    }

    @Override
    public Location copy() {
        return new Location(airportId, airportName, airportCity, airportCountry, airportLatitude, airportLongitude, code);
    }

    public String getAirportId() {
        return airportId;
    }

    public String getAirportName() {
        return airportName;
    }

    public String getAirportCity() {
        return airportCity;
    }

    public String getAirportCountry() {
        return airportCountry;
    }

    public double getAirportLatitude() {
        return airportLatitude;
    }

    public double getAirportLongitude() {
        return airportLongitude;
    }

    public void setName(String name) {
        this.airportName = name;
    }

    public void setCity(String city) {
        this.airportCity = city;
    }

    public void setLatitude(double latitude) {
        this.airportLatitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.airportLongitude = longitude;
    }

    public String getCode() {
        return code;
    }

}
