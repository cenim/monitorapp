/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softmasters.dawuro.umid;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * @author ESS
 */
@DatabaseTable
public class Gallery implements Serializable {

	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int galleryId;
	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	private byte[] picture;
	@DatabaseField
	private String businessid;
	@DatabaseField
	private String applicantid;
	@DatabaseField
	private String status;
	@DatabaseField
	private String macaddress;
	@DatabaseField
	private String uniqueuid;
	// new field
	@DatabaseField
	private String latitude;
	@DatabaseField(dataType = DataType.DATE_STRING)
	private Date timestamp;
	@DatabaseField
	private String longitude;

	public String getUniqueuid() {
		return uniqueuid;
	}

	public void setUniqueuid(String uniqueuid) {
		this.uniqueuid = uniqueuid;
	}

	public Gallery() {
	}

	public Gallery(int galleryId) {
		this.galleryId = galleryId;
	}

	public int getGalleryId() {
		return galleryId;
	}

	public void setGalleryId(int galleryId) {
		this.galleryId = galleryId;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public String getBusinessid() {
		return businessid;
	}

	public void setBusinessid(String businessid) {
		this.businessid = businessid;
	}

	public String getApplicantid() {
		return applicantid;
	}

	public void setApplicantid(String applicantid) {
		this.applicantid = applicantid;
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

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
