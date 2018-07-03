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
import java.util.Date;

@DatabaseTable
public class Businessinformation implements Serializable {

	private static final long serialVersionUID = 1L;
	@DatabaseField
	private String businessuid;
	@DatabaseField
	private String businessname;
	@DatabaseField
	private String tinnumber;
	@DatabaseField
	private String businessregno;
	@DatabaseField
	private String natureofbusiness;
	@DatabaseField
	private String source;
	@DatabaseField
	private String website;
	@DatabaseField
	private Date applieddate;
	@DatabaseField(generatedId = true)
	private int bid;
	@DatabaseField
	private String idtype;
	@DatabaseField
	private String idnumber;
	@DatabaseField(dataType = DataType.DATE_STRING)
	private Date dateofincorporation;
	@DatabaseField
	private String destination;
	@DatabaseField
	private String status;
	@DatabaseField
	private String macaddress;
	@DatabaseField
	private String branch;
	@DatabaseField
	private String businessstatus;
	// new field
	@DatabaseField
	private String uniqueuid;

	public Businessinformation() {
	}

	public Businessinformation(int bid) {
		this.bid = bid;
	}

	public Businessinformation(int bid, String businessuid) {
		this.bid = bid;
		this.businessuid = businessuid;
	}

	public String getBusinessuid() {
		return businessuid;
	}

	public void setBusinessuid(String businessuid) {
		this.businessuid = businessuid;
	}

	public String getBusinesname() {
		return businessname;
	}

	public void setBusinessname(String businessname) {
		this.businessname = businessname;
	}

	public String getTinnumber() {
		return tinnumber;
	}

	public void setTinnumber(String tinnumber) {
		this.tinnumber = tinnumber;
	}

	public String getBusinessregno() {
		return businessregno;
	}

	public void setBusinessregno(String businessregno) {
		this.businessregno = businessregno;
	}

	public String getNatureofbusiness() {
		return natureofbusiness;
	}

	public void setNatureofbusiness(String natureofbusiness) {
		this.natureofbusiness = natureofbusiness;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Date getApplieddate() {
		return applieddate;
	}

	public void setApplieddate(Date applieddate) {
		this.applieddate = applieddate;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public String getIdtype() {
		return idtype;
	}

	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public Date getDateofincorporation() {
		return dateofincorporation;
	}

	public void setDateofincorporation(Date dateofincorporation) {
		this.dateofincorporation = dateofincorporation;
	}

	public String getBusinessname() {
		return businessname;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
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

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getBusinessstatus() {
		return businessstatus;
	}

	public void setBusinessstatus(String businessstatus) {
		this.businessstatus = businessstatus;
	}

	public String getUniqueuid() {
		return uniqueuid;
	}

	public void setUniqueuid(String uniqueuid) {
		this.uniqueuid = uniqueuid;
	}

	@Override
	public String toString() {
		return "Businessinformation{" +
				"businessuid='" + businessuid + '\'' +
				", businessname='" + businessname + '\'' +
				", tinnumber='" + tinnumber + '\'' +
				", businessregno='" + businessregno + '\'' +
				", natureofbusiness='" + natureofbusiness + '\'' +
				", source='" + source + '\'' +
				", website='" + website + '\'' +
				", applieddate=" + applieddate +
				", bid=" + bid +
				", idtype='" + idtype + '\'' +
				", idnumber='" + idnumber + '\'' +
				", dateofincorporation=" + dateofincorporation +
				", destination='" + destination + '\'' +
				", status='" + status + '\'' +
				", macaddress='" + macaddress + '\'' +
				", branch='" + branch + '\'' +
				", businessstatus='" + businessstatus + '\'' +
				", uniqueuid='" + uniqueuid + '\'' +
				'}';
	}

}
