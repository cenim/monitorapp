package com.softmasters.dawuro.controllers;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.softmasters.dawuro.umid.Relatives;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelativesController {

	Context context;
	Dao<Relatives, Integer> relativesDao;

	public RelativesController(Context context,
			Dao<Relatives, Integer> relativesDao) {
		this.relativesDao = relativesDao;
		this.context = context;
	}

	public void saveOrUpdateRelatives(String applicantid, String status,
                                      String typeofrelative, String uniqueuid) throws SQLException {
		List<Relatives> relatives = new ArrayList<Relatives>();
		Relatives relative = new Relatives();
		try {
			relatives = relativesDao.query(relativesDao.queryBuilder().where()
					.eq("applicantid", applicantid).prepare());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (relatives.size() > 0) {
			relative = relatives.get(0);
			updateRelatives(relative, applicantid, status, typeofrelative, uniqueuid);
		} else {
			saveRelatives(applicantid, status, typeofrelative, uniqueuid);
		}
	}

	private void saveRelatives(String applicantid, String status,
                               String typeofrelative, String uniqueuid) throws SQLException {
		Relatives relatives = new Relatives();
		relatives.setApplicantid(applicantid);
		relatives.setTypeofrelative(typeofrelative);
		relatives.setUniqueuid(uniqueuid);

		relativesDao.create(relatives);
	}

	private void updateRelatives(Relatives relatives, String applicantid,
                                 String status, String typeofrelative, String uniqueuid) throws SQLException {
		relatives.setApplicantid(applicantid);
		relatives.setTypeofrelative(typeofrelative);
		relatives.setUniqueuid(uniqueuid);

		relativesDao.update(relatives);
	}

//	public void updateRelatives(Relatives relatives, String status)
//			throws SQLException {
//		relatives.setStatus(status);
//
//		relativesDao.update(relatives);
//	}

	public void updateRelatives(Relatives relatives, String status, String uniqueuid)
			throws SQLException {
		relatives.setStatus(status);
		relatives.setUniqueuid(uniqueuid);

		relativesDao.update(relatives);
	}
}
