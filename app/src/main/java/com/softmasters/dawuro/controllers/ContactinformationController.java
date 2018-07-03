package com.softmasters.dawuro.controllers;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.softmasters.dawuro.umid.Contactinformation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactinformationController {

	Context context;
	Dao<Contactinformation, Integer> contactinformationDao;

	public ContactinformationController(Context context,
			Dao<Contactinformation, Integer> contactinformationDao) {
		this.context = context;
		this.contactinformationDao = contactinformationDao;
	}

	public void saveOrUpdateIndividualContactinformation(
			String alternatemobile,
			String applicantid, String businessid, String contactname,
			String contacttype, String email, String fax, String macaddress,
			String mobile,String personofinterest, String postofficebox, String poitelephone, String status,
			String telephone, String uniqueuid)
			throws SQLException {
		List<Contactinformation> contactinformations = new ArrayList<Contactinformation>();
		Contactinformation contactinformation = new Contactinformation();
		try {
			contactinformations = contactinformationDao
					.query(contactinformationDao.queryBuilder().where()
							.eq("applicantid", applicantid).prepare());
			Log.i("", "Applicant id : " + applicantid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (contactinformations.size() > 0) {
			contactinformation = contactinformations.get(0);
			updateContactinformation(contactinformation,alternatemobile, applicantid, businessid, contactname,
					contacttype, email, fax, macaddress, mobile, personofinterest, postofficebox,
					poitelephone, status, telephone, uniqueuid);
		} else {
			saveContactinformation(alternatemobile, applicantid, businessid, contactname,
					contacttype, email, fax, macaddress, mobile, personofinterest, postofficebox,
					poitelephone, status, telephone, uniqueuid);
		}
	}

	public void saveOrUpdateContactinformation(
			String alternatemobile,
			String applicantid, String businessid, String contactname,
			String contacttype, String email, String fax, String macaddress,
			String mobile,String personofinterest, String postofficebox, String poitelephone, String status,
			String telephone, String uniqueuid)
			throws SQLException {
		if(applicantid == null){
			applicantid = "";
		}
		if(businessid == null){
			businessid = "";
		}
		
		List<Contactinformation> contactinformations = new ArrayList<Contactinformation>();
		Contactinformation contactinformation = new Contactinformation();
		try {
			contactinformations = contactinformationDao
					.query(contactinformationDao.queryBuilder().where()
							.eq("applicantid", applicantid).and().eq("businessid", businessid).prepare());
		} catch (Exception e) {
		}

		if (contactinformations.size() > 0) {
			contactinformation = contactinformations.get(0);
			updateContactinformation(contactinformation,alternatemobile, applicantid, businessid, contactname,
					contacttype, email, fax, macaddress, mobile, personofinterest, postofficebox,
					poitelephone, status, telephone, uniqueuid);
		} else {
			saveContactinformation(alternatemobile, applicantid, businessid, contactname,
					contacttype, email, fax, macaddress, mobile, personofinterest, postofficebox,
					poitelephone, status, telephone, uniqueuid);
		}
	}
	
	public void saveOrUpdateBusinessContactinformation(String alternatemobile,
                                                       String applicantid, String businessid, String contactname,
													   String contacttype, String email, String fax, String macaddress,
													   String mobile,String personofinterest,                                                     String postofficebox, String poitelephone, String status, String telephone, String uniqueuid)
			throws SQLException {
		List<Contactinformation> contactinformations = new ArrayList<Contactinformation>();
		Contactinformation contactinformation = new Contactinformation();
		try {
			contactinformations = contactinformationDao
					.query(contactinformationDao.queryBuilder().where()
							.eq("businessid", businessid).prepare());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (contactinformations.size() > 0) {
			contactinformation = contactinformations.get(0);
			updateContactinformation(contactinformation, alternatemobile,
					applicantid, businessid, contactname, contacttype, email, fax,
					macaddress, mobile, personofinterest, postofficebox, poitelephone, status, telephone, uniqueuid);
		} else {
			saveContactinformation(alternatemobile, applicantid, businessid, contactname,
					contacttype, email, fax, macaddress, mobile, personofinterest, postofficebox, poitelephone,
					status, telephone, uniqueuid);
		}
	}

	private void saveContactinformation(String alternatemobile,
                                        String applicantid, String businessid, String contactname, String contacttype,
                                        String email, String fax, String macaddress, String mobile, String personofinterest,
                                        String postofficebox, String poitelephone, String status, String telephone,
										String uniqueuid)
			throws SQLException {
		Contactinformation contactinformation = new Contactinformation();
		contactinformation.setAlternatemobile(alternatemobile);
		contactinformation.setApplicantid(applicantid);
		contactinformation.setBusinessid(businessid);
		contactinformation.setContactname(contactname);
		contactinformation.setContacttype(contacttype);
		contactinformation.setEmail(email);
		contactinformation.setFax(fax);
		contactinformation.setMacaddress(macaddress);
		contactinformation.setMobile(mobile);
		contactinformation.setPersonofinterest(personofinterest);
		contactinformation.setPostofficebox(postofficebox);
		contactinformation.setPoitelephone(poitelephone);
		contactinformation.setStatus(status);
		contactinformation.setTelephone(telephone);
		contactinformation.setUniqueuid(uniqueuid);

		contactinformationDao.create(contactinformation);
		Log.i("Contactinformation", "Contacts  created ");
	}

	private void updateContactinformation(
            Contactinformation contactinformation,String alternatemobile,
			String applicantid, String businessid, String contactname, String contacttype,
			String email, String fax, String macaddress, String mobile, String personofinterest,
			String postofficebox, String poitelephone, String status, String telephone,
			String uniqueuid)
			throws SQLException {
		contactinformation.setAlternatemobile(alternatemobile);
		contactinformation.setApplicantid(applicantid);
		contactinformation.setBusinessid(businessid);
		contactinformation.setContacttype(contacttype);
		contactinformation.setEmail(email);
		contactinformation.setFax(fax);
		contactinformation.setMacaddress(macaddress);
		contactinformation.setMobile(mobile);
		contactinformation.setPersonofinterest(personofinterest);
		contactinformation.setPostofficebox(postofficebox);
		contactinformation.setPoitelephone(poitelephone);
		contactinformation.setStatus(status);
		contactinformation.setTelephone(telephone);
		contactinformation.setUniqueuid(uniqueuid);

		contactinformationDao.update(contactinformation);
		Log.i("Contactinformation", "Contacts  updated ");

	}

	public void updateContactinformation(Contactinformation contactinformation,
                                         String status, String uniqueuid) throws SQLException {

		contactinformation.setStatus(status);
		contactinformation.setUniqueuid(uniqueuid);

		contactinformationDao.update(contactinformation);

		Log.d("","Contact info updated ");
	}

	public void updateContactinformation(Contactinformation contactinformation, String telephone) throws  SQLException{

		contactinformation.setTelephone(telephone);

		contactinformationDao.update(contactinformation);
	}
}
