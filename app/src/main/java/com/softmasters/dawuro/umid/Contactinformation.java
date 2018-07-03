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
public class Contactinformation implements Serializable {

	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int contactid;
	@DatabaseField
	private String telephone;
	@DatabaseField
	private String mobile;
	@DatabaseField
	private String alternatemobile;
	@DatabaseField
	private String postofficebox;
	@DatabaseField
	private String fax;
	@DatabaseField
	private String applicantid;
	@DatabaseField
	private String businessid;
	@DatabaseField
	private String contacttype;
	@DatabaseField
	private String email;
	@DatabaseField
	private String status;
	@DatabaseField
	private String macaddress;
	@DatabaseField
	private String uniqueuid;
	//new field
	@DatabaseField
	private String personofinterest;
	@DatabaseField
	private String poitelephone;
	@DatabaseField
	private String contactname;

	public Contactinformation() {
	}

	public Contactinformation(int contactid) {
		this.contactid = contactid;
	}

	public int getContactid() {
		return contactid;
	}

	public void setContactid(int contactid) {
		this.contactid = contactid;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAlternatemobile() {
		return alternatemobile;
	}

	public void setAlternatemobile(String alternatemobile) {
		this.alternatemobile = alternatemobile;
	}

	public String getPostofficebox() {
		return postofficebox;
	}

	public void setPostofficebox(String postofficebox) {
		this.postofficebox = postofficebox;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
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

	public String getContacttype() {
		return contacttype;
	}

	public void setContacttype(String contacttype) {
		this.contacttype = contacttype;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPersonofinterest() {
		return personofinterest;
	}

	public void setPersonofinterest(String personofinterest) {
		this.personofinterest = personofinterest;
	}

	public String getPoitelephone() {
		return poitelephone;
	}

	public void setPoitelephone(String poitelephone) {
		this.poitelephone = poitelephone;
	}

	public String getContactname() {
		return contactname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	@Override
	public String toString() {
		return "Contactinformation{" +
				"contactid=" + contactid +
				", telephone='" + telephone + '\'' +
				", mobile='" + mobile + '\'' +
				", alternatemobile='" + alternatemobile + '\'' +
				", postofficebox='" + postofficebox + '\'' +
				", fax='" + fax + '\'' +
				", applicantid='" + applicantid + '\'' +
				", businessid='" + businessid + '\'' +
				", contacttype='" + contacttype + '\'' +
				", email='" + email + '\'' +
				", status='" + status + '\'' +
				", macaddress='" + macaddress + '\'' +
				", uniqueuid='" + uniqueuid + '\'' +
				", personofinterest='" + personofinterest + '\'' +
				", poitelephone='" + poitelephone + '\'' +
				'}';
	}
}
