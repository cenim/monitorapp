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
public class Relatives implements Serializable {

	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int relativeId;
	@DatabaseField
	private String typeofrelative;
	@DatabaseField
	private String applicantid;
	@DatabaseField
	private String status;
	@DatabaseField
	private String macaddress;
	// new field
	@DatabaseField
	private String uniqueuid;

	public Relatives() {
	}

	public Relatives(int relativeId) {
		this.relativeId = relativeId;
	}

	public int getRelativeId() {
		return relativeId;
	}

	public void setRelativeId(int relativeId) {
		this.relativeId = relativeId;
	}

	public String getTypeofrelative() {
		return typeofrelative;
	}

	public void setTypeofrelative(String typeofrelative) {
		this.typeofrelative = typeofrelative;
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
		return "Relatives{" +
				"relativeId=" + relativeId +
				", typeofrelative='" + typeofrelative + '\'' +
				", applicantid='" + applicantid + '\'' +
				", status='" + status + '\'' +
				", macaddress='" + macaddress + '\'' +
				", uniqueuid='" + uniqueuid + '\'' +
				'}';
	}

}
