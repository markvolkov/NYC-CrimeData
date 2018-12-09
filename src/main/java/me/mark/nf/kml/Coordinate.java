package me.mark.nf.kml;

public class Coordinate {

    private String crime;
    private float longitude;
    private float latitude;

    public Coordinate(String crime, float longitude, float latitude) {
        this.crime = crime;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getCrime() {
        return crime;
    }

    public void setCrime(String crime) {
        this.crime = crime;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return crime + " " + latitude + " " + longitude;
    }
}
