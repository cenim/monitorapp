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
public class Userfingerprint implements Serializable {

	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int fingerprintId;
	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	private byte[] fingerprint;
	@DatabaseField
	private String fingertype;
	@DatabaseField
	private String applicantid;
	@DatabaseField
	private String status;
	@DatabaseField
	private String macaddress;
	// new field
	@DatabaseField
	private String uniqueuid;

	public Userfingerprint() {
	}

	public Userfingerprint(int fingerprintId) {
		this.fingerprintId = fingerprintId;
	}

	public int getFingerprintId() {
		return fingerprintId;
	}

	public void setFingerprintId(int fingerprintId) {
		this.fingerprintId = fingerprintId;
	}

	public byte[] getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(byte[] fingerprint) {
		this.fingerprint = fingerprint;
	}

	public String getFingertype() {
		return fingertype;
	}

	public void setFingertype(String fingertype) {
		this.fingertype = fingertype;
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
		return "Userfingerprint{" +
				"fingerprintId=" + fingerprintId +
				", fingerprint=" + Arrays.toString(fingerprint) +
				", fingertype='" + fingertype + '\'' +
				", applicantid='" + applicantid + '\'' +
				", status='" + status + '\'' +
				", macaddress='" + macaddress + '\'' +
				", uniqueuid='" + uniqueuid + '\'' +
				'}';
	}

}
