/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softmasters.dawuro.umid;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 *
 * @author ESS
 */
@DatabaseTable
public class Location implements Serializable {

	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int locationid;
	@DatabaseField
	private String popularname;
	@DatabaseField
	private String city;
	@DatabaseField
	private String street;
	@DatabaseField
	private String assembly;
	@DatabaseField
	private String region;
	@DatabaseField
	private String zipcode;
	@DatabaseField
	private String latitude;
	@DatabaseField
	private String longitude;
	@DatabaseField
	private String areacode;
	@DatabaseField
	private String applicantid;
	@DatabaseField
	private String businessid;
	@DatabaseField
	private String housenumber;
	@DatabaseField
	private String locationtype;
	@DatabaseField
	private String destination;
	@DatabaseField
	private String country;
	@DatabaseField
	private String status;
	@DatabaseField
	private String macaddress;
	// new field
	@DatabaseField
	private String uniqueuid;

	public Location() {
	}

	public Location(int locationid) {
		this.locationid = locationid;
	}

	public int getLocationid() {
		return locationid;
	}

	public void setLocationid(int locationid) {
		this.locationid = locationid;
	}

	public String getPopularname() {
		return popularname;
	}

	public void setPopularname(String popularname) {
		this.popularname = popularname;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getAssembly() {
		return assembly;
	}

	public void setAssembly(String assembly) {
		this.assembly = assembly;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public String getApplicantid() {
		return applicantid;
	}

	public void setApplicantid(String applicantid) {
		this.applicantid = applicantid;
	}

	public String getBusinessid() {
		return businessid;
	}

	public void setBusinessid(String businessid) {
		this.businessid = businessid;
	}

	public String getHousenumber() {
		return housenumber;
	}

	public void setHousenumber(String housenumber) {
		this.housenumber = housenumber;
	}

	public String getLocationtype() {
		return locationtype;
	}

	public void setLocationtype(String locationtype) {
		this.locationtype = locationtype;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMacaddress() {
		return macaddress;
	}

	public void setMacaddress(String macaddress) {
		this.macaddress = macaddress;
	}

	public String getUniqueuid() {
		return uniqueuid;
	}

	public void setUniqueuid(String uniqueuid) {
		this.uniqueuid = uniqueuid;
	}

	@Override
	public String toString() {
		return "Location{" +
				"locationid=" + locationid +
				", popularname='" + popularname + '\'' +
				", city='" + city + '\'' +
				", street='" + street + '\'' +
				", assembly='" + assembly + '\'' +
				", region='" + region + '\'' +
				", zipcode='" + zipcode + '\'' +
				", latitude='" + latitude + '\'' +
				", longitude='" + longitude + '\'' +
				", areacode='" + areacode + '\'' +
				", applicantid='" + applicantid + '\'' +
				", businessid='" + businessid + '\'' +
				", housenumber='" + housenumber + '\'' +
				", locationtype='" + locationtype + '\'' +
				", destination='" + destination + '\'' +
				", country='" + country + '\'' +
				", status='" + status + '\'' +
				", macaddress='" + macaddress + '\'' +
				", uniqueuid='" + uniqueuid + '\'' +
				'}';
	}
}
