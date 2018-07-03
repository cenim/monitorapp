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
public class Basicinformation implements Serializable {

	private static final long serialVersionUID = 1L;
	@DatabaseField
	private String applicantid;// generate using uuid
	@DatabaseField
	private String firstname;
	@DatabaseField
	private String surname;
	@DatabaseField
	private String middlename;
	@DatabaseField
	private String title;
	@DatabaseField
	private String nationality;
	@DatabaseField
	private String placeofbirth;
	@DatabaseField
	private String age;
	@DatabaseField
	private String gender;
	@DatabaseField
	private String tribe;
	@DatabaseField
	private String previousname;
	@DatabaseField
	private String profession;
	@DatabaseField
	private String maritalstatus;
	@DatabaseField
	private String applicantsource;
	@DatabaseField
	private String applicanttype;
	@DatabaseField
	private Date dateofbirth;
	@DatabaseField(generatedId = true)
	private int basicid;
	@DatabaseField
	private String idtype;
	@DatabaseField
	private String idnumber;
	@DatabaseField
	private String language;
	@DatabaseField(dataType = DataType.DATE_STRING)
	private Date applieddate;
	@DatabaseField
	private String destination;
	@DatabaseField
	private String fullname;
	@DatabaseField
	private String employer;
	@DatabaseField
	private String status;
	@DatabaseField
	private String macaddress;
	@DatabaseField
	private String accountstatus;
	@DatabaseField
	private String email;
	// new field
	@DatabaseField
	private String uniqueuid;

	public Date getApplieddate() {
		return applieddate;
	}

	public void setApplieddate(Date applieddate) {
		this.applieddate = applieddate;
	}

	public Basicinformation() {
	}

	public Basicinformation(int basicid) {
		this.basicid = basicid;
	}

	public String getApplicantid() {
		return applicantid;
	}

	public void setApplicantid(String applicantid) {
		this.applicantid = applicantid;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getPlaceofbirth() {
		return placeofbirth;
	}

	public void setPlaceofbirth(String placeofbirth) {
		this.placeofbirth = placeofbirth;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTribe() {
		return tribe;
	}

	public void setTribe(String tribe) {
		this.tribe = tribe;
	}

	public String getPreviousname() {
		return previousname;
	}

	public void setPreviousname(String previousname) {
		this.previousname = previousname;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getMaritalstatus() {
		return maritalstatus;
	}

	public void setMaritalstatus(String maritalstatus) {
		this.maritalstatus = maritalstatus;
	}

	public String getApplicantsource() {
		return applicantsource;
	}

	public void setApplicantsource(String applicantsource) {
		this.applicantsource = applicantsource;
	}

	public String getApplicanttype() {
		return applicanttype;
	}

	public void setApplicanttype(String applicanttype) {
		this.applicanttype = applicanttype;
	}

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public int getBasicid() {
		return basicid;
	}

	public void setBasicid(int basicid) {
		this.basicid = basicid;
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
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

	public String getAccountstatus() {
		return accountstatus;
	}

	public void setAccountstatus(String accountstatus) {
		this.accountstatus = accountstatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUniqueuid() {
		return uniqueuid;
	}

	public void setUniqueuid(String uniqueuid) {
		this.uniqueuid = uniqueuid;
	}

	@Override
	public String toString() {
		return "Basicinformation{" +
				"applicantid='" + applicantid + '\'' +
				", firstname='" + firstname + '\'' +
				", surname='" + surname + '\'' +
				", middlename='" + middlename + '\'' +
				", title='" + title + '\'' +
				", nationality='" + nationality + '\'' +
				", placeofbirth='" + placeofbirth + '\'' +
				", age='" + age + '\'' +
				", gender='" + gender + '\'' +
				", tribe='" + tribe + '\'' +
				", previousname='" + previousname + '\'' +
				", profession='" + profession + '\'' +
				", maritalstatus='" + maritalstatus + '\'' +
				", applicantsource='" + applicantsource + '\'' +
				", applicanttype='" + applicanttype + '\'' +
				", dateofbirth=" + dateofbirth +
				", basicid=" + basicid +
				", idtype='" + idtype + '\'' +
				", idnumber='" + idnumber + '\'' +
				", language='" + language + '\'' +
				", applieddate=" + applieddate +
				", destination='" + destination + '\'' +
				", fullname='" + fullname + '\'' +
				", employer='" + employer + '\'' +
				", status='" + status + '\'' +
				", macaddress='" + macaddress + '\'' +
				", accountstatus='" + accountstatus + '\'' +
				", email='" + email + '\'' +
				", uniqueuid='" + uniqueuid + '\'' +
				'}';
	}

}
