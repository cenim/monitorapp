package com.softmasters.dawuro.controllers;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.softmasters.dawuro.umid.Messaging;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessagingController {

	Context context;
	Dao<Messaging, Integer> messagingDao;

	public MessagingController(Context context,
			Dao<Messaging, Integer> messagingDao) {
		this.context = context;
		this.messagingDao = messagingDao;
	}

	public void saveOrUpdateMessaging(String applicantid, String macaddress,
                                      String message, Date messagedate, String messageidentifier,
                                      String messageinfo, String messagestatus, String messagetype,
                                      String receivertelephone, String sendertelephone, String uniqueuid)
			throws SQLException {

		List<Messaging> messagings = new ArrayList<Messaging>();
		Messaging messaging = new Messaging();
		try {
			messagings = messagingDao.query(messagingDao.queryBuilder().where()
					.eq("", "").prepare());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (messagings.size() > 0) {
			messaging = messagings.get(0);
			updateMessaging(messaging, applicantid, macaddress, message,
					messagedate, messageidentifier, messageinfo, messagestatus,
					messagetype, receivertelephone, sendertelephone, uniqueuid);
		} else {
			saveMessaging(applicantid, macaddress, message, messagedate,
					messageidentifier, messageinfo, messagestatus, messagetype,
					receivertelephone, sendertelephone, uniqueuid);
		}

	}

	private void saveMessaging(String applicantid, String macaddress,
                               String message, Date messagedate, String messageidentifier,
                               String messageinfo, String messagestatus, String messagetype,
                               String receivertelephone, String sendertelephone, String uniqueuid)
			throws SQLException {
		Messaging messaging = new Messaging();
		messaging.setApplicantid(applicantid);
		messaging.setMacaddress(macaddress);
		messaging.setMessage(message);
		messaging.setMessagedate(messagedate);
		messaging.setMessageidentifier(messageidentifier);
		messaging.setMessageinfo(messageinfo);
		messaging.setMessagestatus(messagestatus);
		messaging.setMessagetype(messagetype);
		messaging.setReceivertelephone(receivertelephone);
		messaging.setSendertelephone(sendertelephone);
		messaging.setUniqueuid(uniqueuid);

		messagingDao.create(messaging);
	}

	private void updateMessaging(Messaging messaging, String applicantid,
                                 String macaddress, String message, Date messagedate,
                                 String messageidentifier, String messageinfo, String messagestatus,
                                 String messagetype, String receivertelephone, String sendertelephone, String uniqueuid)
			throws SQLException {
		messaging.setApplicantid(applicantid);
		messaging.setMacaddress(macaddress);
		messaging.setMessage(message);
		messaging.setMessagedate(messagedate);
		messaging.setMessageidentifier(messageidentifier);
		messaging.setMessageinfo(messageinfo);
		messaging.setMessagestatus(messagestatus);
		messaging.setMessagetype(messagetype);
		messaging.setReceivertelephone(receivertelephone);
		messaging.setSendertelephone(sendertelephone);
		messaging.setUniqueuid(uniqueuid);

		messagingDao.update(messaging);
	}

//	public void updateMessaging(Messaging messaging, String status)
//			throws SQLException {
//		messaging.setMessagestatus(status);
//
//		messagingDao.update(messaging);
//	}

	public void updateMessaging(Messaging messaging, String status, String uniqueuid)
			throws SQLException {
		messaging.setMessagestatus(status);
		messaging.setUniqueuid(uniqueuid);

		messagingDao.update(messaging);
	}

}
