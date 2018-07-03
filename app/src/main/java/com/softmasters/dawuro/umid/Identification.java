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
public class Identification implements Serializable {

	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int idno;
	@DatabaseField
	private String idtype;
	@DatabaseField
	private String idnumber;
	@DatabaseField
	private String applicantid;
	@DatabaseField
	private String businessid;
	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	private byte[] idimage;
	@DatabaseField(dataType = DataType.DATE_STRING)
	private Date dateofexpiry;
	@DatabaseField(dataType = DataType.DATE_STRING)
	private Date dateofissue;
	@DatabaseField
	private String idsource;
	@DatabaseField
	private String category;
	@DatabaseField
	private String altnumber;
	@DatabaseField
	private String status;
	@DatabaseField
	private String macaddress;
	@DatabaseField
	private String vstatus;
	// new field
	@DatabaseField
	private String uniqueuid;

	public Identification() {
	}

	public Identification(int idno) {
		this.idno = idno;
	}

	public int getIdno() {
		return idno;
	}

	public void setIdno(int idno) {
		this.idno = idno;
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

	public byte[] getIdimage() {
		return idimage;
	}

	public void setIdimage(byte[] idimage) {
		this.idimage = idimage;
	}

	public Date getDateofexpiry() {
		return dateofexpiry;
	}

	public void setDateofexpiry(Date dateofexpiry) {
		this.dateofexpiry = dateofexpiry;
	}

	public Date getDateofissue() {
		return dateofissue;
	}

	public void setDateofissue(Date dateofissue) {
		this.dateofissue = dateofissue;
	}

	public String getIdsource() {
		return idsource;
	}

	public void setIdsource(String idsource) {
		this.idsource = idsource;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAltnumber() {
		return altnumber;
	}

	public void setAltnumber(String altnumber) {
		this.altnumber = altnumber;
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

	public String getVstatus() {
		return vstatus;
	}

	public void setVstatus(String vstatus) {
		this.vstatus = vstatus;
	}

	public String getUniqueuid() {
		return uniqueuid;
	}

	public void setUniqueuid(String uniqueuid) {
		this.uniqueuid = uniqueuid;
	}

	@Override
	public String toString() {
		return "Identification{" +
				"idno=" + idno +
				", idtype='" + idtype + '\'' +
				", idnumber='" + idnumber + '\'' +
				", applicantid='" + applicantid + '\'' +
				", businessid='" + businessid + '\'' +
				", idimage=" + Arrays.toString(idimage) +
				", dateofexpiry=" + dateofexpiry +
				", dateofissue=" + dateofissue +
				", idsource='" + idsource + '\'' +
				", category='" + category + '\'' +
				", altnumber='" + altnumber + '\'' +
				", status='" + status + '\'' +
				", macaddress='" + macaddress + '\'' +
				", vstatus='" + vstatus + '\'' +
				", uniqueuid='" + uniqueuid + '\'' +
				'}';
	}

}
