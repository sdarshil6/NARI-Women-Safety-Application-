package com.company.currentlocation;

public class LocationInfo {

    private String latitude;
    private String longitude;
    private String countryName;
    private String locality;
    private String addressLine;

    public LocationInfo(){}

    public LocationInfo(String latitude, String longitude, String countryName, String locality, String addressLine) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.countryName = countryName;
        this.locality = locality;
        this.addressLine = addressLine;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }
}
