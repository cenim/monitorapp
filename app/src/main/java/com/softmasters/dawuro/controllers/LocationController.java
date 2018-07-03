package com.softmasters.dawuro.controllers;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.softmasters.dawuro.umid.Location;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationController {

	Context context;
	Dao<Location, Integer> locationDao;

	public LocationController(Context context,
			Dao<Location, Integer> locationDao) {
		this.context = context;
		this.locationDao = locationDao;
	}

	public void saveOrUpdateLocation(String applicantid, String areacode,
                                     String assembly, String businessid, String city, String country,
                                     String destination, String housenumber, String latitude,
                                     String locationtype, String longitude, String macaddress,
                                     String popularname, String region, String status, String street, String uniqueuid,
                                     String zipcode) throws SQLException {
		List<Location> locations = new ArrayList<Location>();
		Location location = new Location();
		try {
			locations = locationDao.query(locationDao.queryBuilder().where()
					.eq("businessid", businessid).or()
					.eq("applicantid", applicantid).and()
					.eq("uniqueuid", uniqueuid).prepare());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (locations.size() > 0) {
			location = locations.get(0);
			updateLocation(location, applicantid, areacode, assembly,
					businessid, city, country, destination, housenumber,
					latitude, locationtype, longitude, macaddress, popularname,
					region, status, street, uniqueuid, zipcode);
		} else {
			saveLocation(applicantid, areacode, assembly, businessid, city,
					country, destination, housenumber, latitude, locationtype,
					longitude, macaddress, popularname, region, status, street, uniqueuid,
					zipcode);
		}
	}

	private void saveLocation(String applicantid, String areacode,
                              String assembly, String businessid, String city, String country,
                              String destination, String housenumber, String latitude,
                              String locationtype, String longitude, String macaddress,
                              String popularname, String region, String status, String street, String uniqueuid,
                              String zipcode) throws SQLException {
		Location location = new Location();
		location.setApplicantid(applicantid);
		location.setAreacode(areacode);
		location.setAssembly(assembly);
		location.setBusinessid(businessid);
		location.setCity(city);
		location.setCountry(country);
		location.setDestination(destination);
		location.setHousenumber(housenumber);
		location.setLatitude(latitude);
		location.setLocationtype(locationtype);
		location.setLongitude(longitude);
		location.setMacaddress(macaddress);
		location.setPopularname(popularname);
		location.setRegion(region);
		location.setStatus(status);
		location.setStreet(street);
		location.setUniqueuid(uniqueuid);
		location.setZipcode(zipcode);

		locationDao.create(location);
	}

	private void updateLocation(Location location, String applicantid,
                                String areacode, String assembly, String businessid, String city,
                                String country, String destination, String housenumber,
                                String latitude, String locationtype, String longitude,
                                String macaddress, String popularname, String region,
                                String status, String street, String uniqueuid, String zipcode) throws SQLException {
		location.setApplicantid(applicantid);
		location.setAreacode(areacode);
		location.setAssembly(assembly);
		location.setBusinessid(businessid);
		location.setCity(city);
		location.setCountry(country);
		location.setHousenumber(housenumber);
		location.setLatitude(latitude);
		location.setLocationtype(locationtype);
		location.setLongitude(longitude);
		location.setMacaddress(macaddress);
		location.setPopularname(popularname);
		location.setRegion(region);
		location.setStatus(status);
		location.setStreet(street);
		location.setUniqueuid(uniqueuid);
		location.setZipcode(zipcode);

		locationDao.update(location);
	}

	public void updateLocation(Location location, String status, String uniqueuid)
			throws SQLException {

		location.setStatus(status);
		location.setUniqueuid(uniqueuid);

		locationDao.update(location);
	}
}
