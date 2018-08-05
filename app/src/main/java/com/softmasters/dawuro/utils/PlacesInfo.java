package com.softmasters.dawuro.utils;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

public class PlacesInfo {
    String name;
    String address;
    String attributions;
    String Id;
    LatLng latLng;
    String locale;
    int priceLevel;
    Uri websiteUrl;
    String phoneNumber;
    String viewport;

    public PlacesInfo() {
    }

    public String getViewport() {
        return viewport;
    }

    public void setViewport(String viewport) {
        this.viewport = viewport;
    }

    public PlacesInfo(String name, String address, String attributions, String id, LatLng latLng, String locale, int priceLevel,
                      Uri websiteUrl, String phoneNumber, String viewport) {
        this.name = name;
        this.address = address;
        this.attributions = attributions;
        Id = id;
        this.latLng = latLng;
        this.locale = locale;
        this.priceLevel = priceLevel;
        this.websiteUrl = websiteUrl;
        this.phoneNumber = phoneNumber;
        this.viewport = viewport;
    }

    public PlacesInfo(String name, String address, String attributions, String id, LatLng latLng,
                      String locale, int priceLevel, Uri websiteUrl, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.attributions = attributions;
        Id = id;
        this.latLng = latLng;
        this.locale = locale;
        this.priceLevel = priceLevel;
        this.websiteUrl = websiteUrl;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAttributions() {
        return attributions;
    }

    public void setAttributions(String attributions) {
        this.attributions = attributions;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public int getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(int priceLevel) {
        this.priceLevel = priceLevel;
    }

    public Uri getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(Uri websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "PlacesInfo{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", attributions='" + attributions + '\'' +
                ", Id='" + Id + '\'' +
                ", latLng=" + latLng +
                ", locale='" + locale + '\'' +
                ", priceLevel=" + priceLevel +
                ", websiteUrl=" + websiteUrl +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", viewport='" + viewport + '\'' +
                '}';
    }
}
