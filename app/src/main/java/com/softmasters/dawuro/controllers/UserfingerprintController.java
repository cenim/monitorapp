package com.softmasters.dawuro.controllers;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.softmasters.dawuro.umid.Userfingerprint;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserfingerprintController {

	Context context;
	Dao<Userfingerprint, Integer> userfingerprintDao;

	public UserfingerprintController(Context context,
			Dao<Userfingerprint, Integer> userfingerprintDao) {
		this.context = context;
		this.userfingerprintDao = userfingerprintDao;
	}

	public void saveOrUpdateFingerprint(String applicantid, byte[] fingerprint,
                                        String fingertype, String macaddress, String uniqueuid) throws SQLException {
		List<Userfingerprint> userfingerprints = new ArrayList<Userfingerprint>();
		Userfingerprint userfingerprint = new Userfingerprint();
		try {
			userfingerprints = userfingerprintDao.query(userfingerprintDao
					.queryBuilder().where().eq("applicantid", applicantid)
					.prepare());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (userfingerprints.size() > 0) {
			userfingerprint = userfingerprints.get(0);
			updateFingerprint(userfingerprint, applicantid, fingerprint,
					fingertype, macaddress, uniqueuid);
		} else {
			saveFingerprint(applicantid, fingerprint, fingertype, macaddress, uniqueuid);
		}
	}

	private void saveFingerprint(String applicantid, byte[] fingerprint,
                                 String fingertype, String macaddress, String uniqueuid) throws SQLException {
		Userfingerprint userfingerprint = new Userfingerprint();
		userfingerprint.setApplicantid(applicantid);
		userfingerprint.setFingerprint(fingerprint);
		userfingerprint.setFingertype(fingertype);
		userfingerprint.setMacaddress(macaddress);
		userfingerprint.setUniqueuid(uniqueuid);

		userfingerprintDao.create(userfingerprint);
	}

	private void updateFingerprint(Userfingerprint userfingerprint,
                                   String applicantid, byte[] fingerprint, String fingertype,
                                   String macaddress, String uniqueuid) throws SQLException {
		userfingerprint.setApplicantid(applicantid);
		userfingerprint.setFingerprint(fingerprint);
		userfingerprint.setFingertype(fingertype);
		userfingerprint.setMacaddress(macaddress);
		userfingerprint.setUniqueuid(uniqueuid);

		userfingerprintDao.update(userfingerprint);
	}

//	public void updateFingerprint(Userfingerprint userfingerprint, String status)
//			throws SQLException {
//		userfingerprint.setStatus(status);
//
//		userfingerprintDao.update(userfingerprint);
//	}

	public void updateFingerprint(Userfingerprint userfingerprint, String status, String uniqueuid)
			throws SQLException {
		userfingerprint.setStatus(status);
		userfingerprint.setUniqueuid(uniqueuid);

		userfingerprintDao.update(userfingerprint);
	}
}
