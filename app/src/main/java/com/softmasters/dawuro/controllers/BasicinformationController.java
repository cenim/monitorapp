package com.softmasters.dawuro.controllers;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.softmasters.dawuro.umid.Basicinformation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BasicinformationController {

	Dao<Basicinformation, Integer> basicinformationDao;
	Context context;

	public BasicinformationController(Context context,
			Dao<Basicinformation, Integer> basicinformationDao) {
		this.context = context;
		this.basicinformationDao = basicinformationDao;
	}

	public void saveOrUpdateBasicinformation(String accountstatus, String age,
                                             String applicantid, String applicantsource, String applicanttype,
                                             Date applieddate, Date dateofbirth, String destination, String email,
                                             String employer, String firstname, String fullname, String gender,
                                             String idnumber, String idtype, String language, String macaddress,
                                             String maritalstatus, String middlename, String nationality,
                                             String placeofbirth, String previousname, String profession,
                                             String status, String surname, String title, String tribe, String uniqueuid)
			throws SQLException {
		List<Basicinformation> basicinformations = new ArrayList<Basicinformation>();
		Basicinformation basicinformation = new Basicinformation();

		try {
			basicinformations = basicinformationDao.query(basicinformationDao
					.queryBuilder().where().eq("applicantid", applicantid)
					.prepare());

			Log.d("Basicinformation size", "Size : " + basicinformations.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (basicinformations.size() > 0) {
			basicinformation = basicinformations.get(0);
			updateBasicinformation(basicinformation, accountstatus, age,
					applicantid, applicantsource, applicanttype, applieddate,
					dateofbirth, destination, email, employer, firstname, fullname,
					gender, idnumber, idtype, language, macaddress,
					maritalstatus, middlename, nationality, placeofbirth,
					previousname, profession, status, surname, title, tribe, uniqueuid);
		} else {
			saveBasicinformation(accountstatus, age, applicantid,
					applicantsource, applicanttype, applieddate, dateofbirth,
					destination, email, employer, firstname, fullname, gender,
					idnumber, idtype, language, macaddress, maritalstatus,
					middlename, nationality, placeofbirth, previousname,
					profession, status, surname, title, tribe, uniqueuid);
		}

	}

	void saveBasicinformation(String accountstatus, String age,
                              String applicantid, String applicantsource, String applicanttype,
                              Date applieddate, Date dateofbirth, String destination, String email,
                              String employer, String firstname, String fullname, String gender,
                              String idnumber, String idtype, String language, String macaddress,
                              String maritalstatus, String middlename, String nationality,
                              String placeofbirth, String previousname, String profession,
                              String status, String surname, String title, String tribe, String uniqueuid)
			throws SQLException {
		Basicinformation basicinformation = new Basicinformation();

		basicinformation.setAccountstatus(accountstatus);
		basicinformation.setAge(age);
		basicinformation.setApplicantid(applicantid);
		basicinformation.setApplicantsource(applicantsource);
		basicinformation.setApplieddate(applieddate);
		basicinformation.setApplicanttype(applicanttype);
		basicinformation.setDateofbirth(dateofbirth);
		basicinformation.setDestination(destination);
		basicinformation.setEmail(email);
		basicinformation.setEmployer(employer);
		basicinformation.setFirstname(firstname);
		basicinformation.setFullname(fullname);
		basicinformation.setGender(gender);
		basicinformation.setIdnumber(idnumber);
		basicinformation.setIdtype(idtype);
		basicinformation.setLanguage(language);
		basicinformation.setMacaddress(macaddress);
		basicinformation.setMaritalstatus(maritalstatus);
		basicinformation.setMiddlename(middlename);
		basicinformation.setNationality(nationality);
		basicinformation.setPlaceofbirth(placeofbirth);
		basicinformation.setPreviousname(previousname);
		basicinformation.setProfession(profession);
		basicinformation.setStatus(status);
		basicinformation.setSurname(surname);
		basicinformation.setTitle(title);
		basicinformation.setTribe(tribe);
		basicinformation.setUniqueuid(uniqueuid);

		basicinformationDao.create(basicinformation);
	}

	void updateBasicinformation(Basicinformation basicinformation,
                                String accountstatus, String age, String applicantid,
                                String applicantsource, String applicanttype, Date applieddate,
                                Date dateofbirth, String destination, String email, String employer,
                                String firstname, String fullname, String gender, String idnumber,
                                String idtype, String language, String macaddress,
                                String maritalstatus, String middlename, String nationality,
                                String placeofbirth, String previousname, String profession,
                                String status, String surname, String title, String tribe, String uniqueuid)
			throws SQLException {

		basicinformation.setAccountstatus(accountstatus);
		basicinformation.setAge(age);
		basicinformation.setApplicantid(applicantid);
		basicinformation.setApplicantsource(applicantsource);
		basicinformation.setApplicanttype(applicanttype);
		basicinformation.setApplieddate(applieddate);
		basicinformation.setDateofbirth(dateofbirth);
		basicinformation.setDestination(destination);
		basicinformation.setEmail(email);
		basicinformation.setEmployer(employer);
		basicinformation.setFirstname(firstname);
		basicinformation.setGender(gender);
		basicinformation.setIdnumber(idnumber);
		basicinformation.setIdtype(idtype);
		basicinformation.setMacaddress(macaddress);
		basicinformation.setMaritalstatus(maritalstatus);
		basicinformation.setMiddlename(middlename);
		basicinformation.setNationality(nationality);
		basicinformation.setPlaceofbirth(placeofbirth);
		basicinformation.setPreviousname(previousname);
		basicinformation.setProfession(profession);
		basicinformation.setStatus(status);
		basicinformation.setSurname(surname);
		basicinformation.setTitle(title);
		basicinformation.setTribe(tribe);
		basicinformation.setUniqueuid(uniqueuid);

		basicinformationDao.update(basicinformation);

	}

//	public void updateBasicinformation(Basicinformation basicinformation, String applicantid,
//			String status) throws SQLException {
//		basicinformation.setApplicantid(applicantid);
//		basicinformation.setStatus(status);
//
//		basicinformationDao.update(basicinformation);
//	}

	public  void updateBasicinformation(Basicinformation basicinformation,
                                        String status, String uniqueuid) throws SQLException {
		basicinformation.setStatus(status);
		basicinformation.setUniqueuid(uniqueuid);

		basicinformationDao.update(basicinformation);
	}

}
