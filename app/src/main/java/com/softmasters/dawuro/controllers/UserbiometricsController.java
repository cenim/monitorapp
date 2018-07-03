package com.softmasters.dawuro.controllers;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.softmasters.dawuro.umid.Userbiometrics;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserbiometricsController {

	Context context;
	Dao<Userbiometrics, Integer> userBiometricsDao;

	public UserbiometricsController(Context context,
			Dao<Userbiometrics, Integer> userBiometricsDao) {
		this.context = context;
		this.userBiometricsDao = userBiometricsDao;
	}

	public void saveOrUpdateBiometrics(String applicantid, String macaddress,
                                       byte[] picture, byte[] signature, String status, String uniqueuid)
			throws SQLException {
		List<Userbiometrics> userbiometrics = new ArrayList<Userbiometrics>();
		Userbiometrics userbiometric = new Userbiometrics();
		try {
			userbiometrics = userBiometricsDao.query(userBiometricsDao
					.queryBuilder().where().eq("applicantid", applicantid)
					.prepare());
		} catch (Exception e) {
		}
		if (userbiometrics.size() > 0) {
			userbiometric = userbiometrics.get(0);
			updateBiometrics(userbiometric, applicantid, macaddress, picture,
					signature, status, uniqueuid);
		} else {
			saveBiometrics(applicantid, macaddress, picture, signature, status, uniqueuid);
		}

	}

	private void saveBiometrics(String applicantid, String macaddress,
                                byte[] picture, byte[] signature, String status, String uniqueuid)
			throws SQLException {
		Userbiometrics userbiometrics = new Userbiometrics();
		userbiometrics.setApplicantid(applicantid);
		userbiometrics.setMacaddress(macaddress);
		userbiometrics.setPicture(picture);
		userbiometrics.setSignature(signature);
		userbiometrics.setUniqueuid(uniqueuid);

		userBiometricsDao.create(userbiometrics);
	}

	private void updateBiometrics(Userbiometrics userbiometrics,
                                  String applicantid, String macaddress, byte[] picture,
                                  byte[] signature, String status, String uniqueuid) throws SQLException {
		userbiometrics.setApplicantid(applicantid);
		userbiometrics.setMacaddress(macaddress);
		userbiometrics.setPicture(picture);
		userbiometrics.setSignature(signature);
		userbiometrics.setUniqueuid(uniqueuid);

		userBiometricsDao.update(userbiometrics);
	}

//	public void updateBiometrics(Userbiometrics userbiometrics, String id,String status)
//			throws SQLException {
//		userbiometrics.setApplicantid(id);
//		userbiometrics.setStatus(status);
//
//		userBiometricsDao.update(userbiometrics);
//	}

	public void updateBiometrics(Userbiometrics userbiometrics, String status, String uniqueuid)
			throws SQLException {
		userbiometrics.setStatus(status);
		userbiometrics.setUniqueuid(uniqueuid);

		userBiometricsDao.update(userbiometrics);
	}
}
