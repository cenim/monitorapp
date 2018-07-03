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

/**
 *
 * @author ESS
 */
@DatabaseTable
public class Userbiometrics implements Serializable {

	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int bioid;
	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	private byte[] picture;
	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	private byte[] signature;
	@DatabaseField
	private String applicantid;
	@DatabaseField
	private String status;
	@DatabaseField
	private String macaddress;
	// new field
	@DatabaseField
	private String uniqueuid;

	public Userbiometrics() {
	}

	public Userbiometrics(int bioid) {
		this.bioid = bioid;
	}

	public int getBioid() {
		return bioid;
	}

	public void setBioid(int bioid) {
		this.bioid = bioid;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
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

	public String getUniqueuid() {
		return uniqueuid;
	}

	public void setUniqueuid(String uniqueuid) {
		this.uniqueuid = uniqueuid;
	}

	@Override
	public String toString() {
		return "Userbiometrics{" +
				"bioid=" + bioid +
				", picture=" + Arrays.toString(picture) +
				", signature=" + Arrays.toString(signature) +
				", applicantid='" + applicantid + '\'' +
				", status='" + status + '\'' +
				", macaddress='" + macaddress + '\'' +
				", uniqueuid='" + uniqueuid + '\'' +
				'}';
	}
}
