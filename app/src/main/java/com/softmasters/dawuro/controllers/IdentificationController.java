package com.softmasters.dawuro.controllers;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.softmasters.dawuro.umid.Identification;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IdentificationController {

	Context context;
	Dao<Identification, Integer> identificationDao;

	public IdentificationController(Context context,
			Dao<Identification, Integer> identificationDao) {
		this.context = context;
		this.identificationDao = identificationDao;
	}

	public void saveOrUpdateIdentification(String altnumber,
                                           String applicantid, String businessid, String category,
                                           Date dateofexpiry, Date dateofissue, byte[] idimage,
                                           String idnumber, String idsource, String idtype, String macaddress,
                                           String status, String uniqueuid, String vstatus) throws SQLException {
		List<Identification> identifications = new ArrayList<Identification>();
		Identification identification = new Identification();
		try {
			identifications = identificationDao.query(identificationDao
					.queryBuilder().where().eq("applicantid", applicantid).or()
					.eq("businessid", businessid).and().eq("idtype", idtype)
					.prepare());
		} catch (Exception e) {
		}
		if (identifications.size() > 0) {
			identification = identifications.get(0);
			updateIdentification(identification, altnumber, applicantid,
					businessid, category, dateofexpiry, dateofissue, idimage,
					idnumber, idsource, idtype, macaddress, status,uniqueuid, vstatus);
		} else {
			saveIdentification(altnumber, applicantid, businessid, category,
					dateofexpiry, dateofissue, idimage, idnumber, idsource,
					idtype, macaddress, status, uniqueuid, vstatus);
		}

	}

	void saveIdentification(String altnumber, String applicantid,
                            String businessid, String category, Date dateofexpiry,
                            Date dateofissue, byte[] idimage, String idnumber, String idsource,
                            String idtype, String macaddress, String status, String uniqueuid, String vstatus)
			throws SQLException {
		Identification identification = new Identification();
		identification.setAltnumber(altnumber);
		identification.setApplicantid(applicantid);
		identification.setBusinessid(businessid);
		identification.setCategory(category);// id type. e.g. passport
		identification.setDateofexpiry(dateofexpiry);
		identification.setDateofissue(dateofissue);
		identification.setIdimage(idimage);
		identification.setIdnumber(idnumber);
		identification.setIdtype(idtype);
		identification.setIdsource(idsource);// office that authorizes/d it
		identification.setMacaddress(macaddress);
		identification.setStatus(status);
		identification.setUniqueuid(uniqueuid);
		identification.setVstatus(vstatus);


		identificationDao.create(identification);

	}

	void updateIdentification(Identification identification, String altnumber,
                              String applicantid, String businessid, String category,
                              Date dateofexpiry, Date dateofissue, byte[] idimage,
                              String idnumber, String idsource, String idtype, String macaddress,
                              String status, String uniqueuid, String vstatus) throws SQLException {
		identification.setApplicantid(applicantid);
		identification.setBusinessid(businessid);
		identification.setCategory(category);
		identification.setDateofexpiry(dateofexpiry);
		identification.setDateofissue(dateofissue);
		identification.setIdimage(idimage);
		identification.setIdnumber(idnumber);
		identification.setIdtype(idtype);
		identification.setIdsource(idsource);
		identification.setStatus(status);
		identification.setUniqueuid(uniqueuid);
		identification.setVstatus(vstatus);

		identificationDao.update(identification);
	}

//	public void updateIdentification(Identification identification, String id,
//			String status) throws SQLException {
//		if(!TextUtils.isEmpty(identification.getApplicantid())){
//			identification.setApplicantid(id);
//		}else if(!TextUtils.isEmpty(identification.getBusinessid())){
//			identification.setBusinessid(id);
//		}
//		identification.setStatus(status);
//
//		identificationDao.update(identification);
//	}

	public void updateIdentification(Identification identification,
                                     String status, String uniqueuid) throws SQLException {

		identification.setStatus(status);
		identification.setUniqueuid(uniqueuid);

		identificationDao.update(identification);
	}

}
