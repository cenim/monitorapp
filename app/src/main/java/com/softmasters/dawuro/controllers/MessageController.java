package com.softmasters.dawuro.controllers;

import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.softmasters.dawuro.umid.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageController {

	RuntimeExceptionDao<Message, Integer> msgDao;
	Context context;

	public MessageController(Context context,
			RuntimeExceptionDao<Message, Integer> msgDao) {
		this.context = context;
		this.msgDao = msgDao;
	}

	public void saveOrUpdateMessage(String destinationid, String macaddress,
                                    String status, String messagetype, String msg, String regid, String sourceid,
                                    String token, String uniqueuid) {
		List<Message> messages = new ArrayList<Message>();
		Message message = new Message();
		try {
			messages = msgDao.query(msgDao.queryBuilder().where()
					.eq("uniqueuid", uniqueuid)// .eq("sourceid",
												// sourceid).and()
					.prepare());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (messages.size() > 0) {
			message = messages.get(0);
			updateMessage(message, destinationid, macaddress, status,
					messagetype, msg, regid, sourceid, token, uniqueuid);
		} else {
			saveMessage(destinationid, macaddress, status, messagetype, msg, regid,
					sourceid, token, uniqueuid);

		}
	}

	public void saveMessage(String destinationid, String macaddress,
                            String status, String messagetype, String msg, String regid, String sourceid,
                            String token, String uniqueuid) {
		Message message = new Message();
		message.setDestinationid(destinationid);
		message.setMacaddress(macaddress);
		message.setMessagestatus(status);
		message.setMessagetype(messagetype);
		message.setMsg(msg);
		message.setRegid(regid);
		message.setSourceid(sourceid);
		message.setToken(token);
		message.setUniqueuid(uniqueuid);

		msgDao.create(message);
	}

	private void updateMessage(Message message, String destinationid,
                               String macaddress, String status, String messagetype, String msg, String regid,
                               String sourceid, String token, String uniqueuid) {
		message.setDestinationid(destinationid);
		message.setMacaddress(macaddress);
		message.setMessagestatus(status);
		message.setMessagetype(messagetype);
		message.setMsg(msg);
		message.setRegid(regid);
		message.setSourceid(sourceid);
		message.setToken(token);
		message.setUniqueuid(uniqueuid);

		msgDao.update(message);
	}

	private void updateMessage(String uniqueuid, String status) {
		List<Message> messages = new ArrayList<Message>();
		Message message = new Message();
		try {
			messages = msgDao.query(msgDao.queryBuilder().where()
					.eq("uniqueuid", uniqueuid)// .eq("sourceid",
												// sourceid).and()
					.prepare());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (messages.size() > 0) {
			message = messages.get(0);
			message.setMessagestatus(status);

			msgDao.update(message);
		}
	}
}
