package com.softmasters.dawuro.controllers;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.softmasters.dawuro.umid.Businessinformation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BusinessInformationController {
	Context context;
	Dao<Businessinformation, Integer> businessinformationDao;

	public BusinessInformationController(Context context,
			Dao<Businessinformation, Integer> businessinformationDao) {
		this.context = context;
		this.businessinformationDao = businessinformationDao;
	}

	public void saveOrUpdateBusinessinformation(Date applieddate,
                                                String branch, String businesname, String businessuid,
                                                String businessregno, String businessstatus,
                                                Date dateofincorporation, String destination, String idnumber,
                                                String idtype, String macaddress, String natureofbusiness,
                                                String source, String status, String tinnumber, String uniqueuid, String website)
			throws SQLException {
		List<Businessinformation> businessinformations = new ArrayList<Businessinformation>();
		Businessinformation businessinformation = new Businessinformation();
		try {
			businessinformations = businessinformationDao
					.query(businessinformationDao.queryBuilder().where()
							.eq("businessuid", businessuid).prepare());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (businessinformations.size() > 0) {
			businessinformation = businessinformations.get(0);
			updateBusinessinformation(businessinformation, applieddate, branch,
					businesname, businessuid, businessregno, businessstatus,
					dateofincorporation, destination, idnumber, idtype,
					macaddress, natureofbusiness, source, status, tinnumber, uniqueuid,
					website);
		} else {
			saveBusinessinformation(applieddate, branch, businesname,
					businessuid, businessregno, businessstatus,
					dateofincorporation, destination, idnumber, idtype,
					macaddress, natureofbusiness, source, status, tinnumber, uniqueuid,
					website);
		}

	}

	private void saveBusinessinformation(Date applieddate, String branch,
                                         String businesname, String businessuid, String businessregno,
                                         String businessstatus, Date dateofincorporation,
                                         String destination, String idnumber, String idtype,
                                         String macaddress, String natureofbusiness, String status,
                                         String source, String tinnumber, String uniqueuid, String website)
			throws SQLException {
		Businessinformation businessinformation = new Businessinformation();
		businessinformation.setApplieddate(applieddate);
		businessinformation.setBranch(branch);
		businessinformation.setBusinessname(businesname);
		businessinformation.setBusinessuid(businessuid);
		businessinformation.setBusinessregno(businessregno);
		businessinformation.setBusinessstatus(businessstatus);
		businessinformation.setDateofincorporation(dateofincorporation);
		businessinformation.setDestination(destination);
		businessinformation.setIdnumber(idnumber);
		businessinformation.setIdtype(idtype);
		businessinformation.setMacaddress(macaddress);
		businessinformation.setNatureofbusiness(natureofbusiness);
		businessinformation.setSource(source);
		businessinformation.setStatus(status);
		businessinformation.setTinnumber(tinnumber);
		businessinformation.setUniqueuid(uniqueuid);
		businessinformation.setWebsite(website);


		businessinformationDao.create(businessinformation);
	}

	private void updateBusinessinformation(
            Businessinformation businessinformation, Date applieddate,
            String branch, String businesname, String businessuid,
            String businessregno, String businessstatus,
            Date dateofincorporation, String destination, String idnumber,
            String idtype, String macaddress, String natureofbusiness,
            String status, String source, String tinnumber, String uniqueuid, String website)
			throws SQLException {
		businessinformation.setApplieddate(applieddate);
		businessinformation.setBranch(branch);
		businessinformation.setBusinessname(businesname);
		businessinformation.setBusinessuid(businessuid);
		businessinformation.setBusinessregno(businessregno);
		businessinformation.setBusinessstatus(businessstatus);
		businessinformation.setDateofincorporation(dateofincorporation);
		businessinformation.setDestination(destination);
		businessinformation.setIdnumber(idnumber);
		businessinformation.setIdtype(idtype);
		businessinformation.setMacaddress(macaddress);
		businessinformation.setNatureofbusiness(natureofbusiness);
		businessinformation.setSource(source);
		businessinformation.setStatus(status);
		businessinformation.setTinnumber(tinnumber);
		businessinformation.setUniqueuid(uniqueuid);
		businessinformation.setWebsite(website);

		businessinformationDao.update(businessinformation);
	}

//	public void updateBusinessinformation(
//			Businessinformation businessinformation,String businessuid, String status)
//			throws SQLException {
//		businessinformation.setBusinessuid(businessuid);
//		businessinformation.setStatus(status);
//
//		businessinformationDao.update(businessinformation);
//	}

	public void updateBusinessinformation(
            Businessinformation businessinformation, String status, String uniqueuid)
			throws SQLException {
		businessinformation.setStatus(status);
		businessinformation.setUniqueuid(uniqueuid);

		businessinformationDao.update(businessinformation);
	}
}
