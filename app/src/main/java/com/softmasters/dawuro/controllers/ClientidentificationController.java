package com.softmasters.dawuro.controllers;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.softmasters.dawuro.umid.Clientidentification;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientidentificationController {

	Context context;
	Dao<Clientidentification, Integer> clientidentificationDao;

	public ClientidentificationController(Context context,
			Dao<Clientidentification, Integer> clientidentificationDao) {
		this.clientidentificationDao = clientidentificationDao;
		this.context = context;
	}

	public void saveOrUpdateClientientification(String clientId,
                                                String macaddress, Date modifieddate, String regid,
                                                Date registerdate, String status, String uniqueuid) throws SQLException {
		List<Clientidentification> clientidentifications = new ArrayList<Clientidentification>();
		Clientidentification clientidentification = new Clientidentification();
		try {
			clientidentifications = clientidentificationDao
					.query(clientidentificationDao.queryBuilder().where()
							.eq("clientId", clientId).prepare());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (clientidentifications.size() > 0) {
			clientidentification = clientidentifications.get(0);
			updateClientidentification(clientidentification, clientId,
					macaddress, modifieddate, regid, registerdate, status, uniqueuid);
		} else {
			saveClientidentification(clientId, macaddress, modifieddate, regid,
					registerdate, status, uniqueuid);
		}
	}

	private void saveClientidentification(String clientId, String macaddress,
                                          Date modifieddate, String regid, Date registerdate, String status, String uniqueuid)
			throws SQLException {
		Clientidentification clientidentification = new Clientidentification();
		clientidentification.setClientId(clientId);
		clientidentification.setMacaddress(macaddress);
		clientidentification.setModifieddate(modifieddate);
		clientidentification.setRegid(regid);
		clientidentification.setRegisterdate(registerdate);
		clientidentification.setStatus(status);
		clientidentification.setUniqueuid(uniqueuid);

		clientidentificationDao.create(clientidentification);
	}

	private void updateClientidentification(
            Clientidentification clientidentification, String clientId,
            String macaddress, Date modifieddate, String regid,
            Date registerdate, String status, String uniqueuid) throws SQLException {
		clientidentification.setClientId(clientId);
		clientidentification.setMacaddress(macaddress);
		clientidentification.setModifieddate(modifieddate);
		clientidentification.setRegid(regid);
		clientidentification.setRegisterdate(registerdate);
		clientidentification.setStatus(status);
		clientidentification.setUniqueuid(uniqueuid);

		clientidentificationDao.update(clientidentification);

	}
	
//	public void updateClientidentification(
//			Clientidentification clientidentification, String status)
//			throws SQLException {
//		clientidentification.setStatus(status);
//
//		clientidentificationDao.update(clientidentification);
//	}

	public void updateClientidentification(
            Clientidentification clientidentification, String status, String uniqueuid)
			throws SQLException {
		clientidentification.setStatus(status);
		clientidentification.setUniqueuid(uniqueuid);

		clientidentificationDao.update(clientidentification);
	}

}
