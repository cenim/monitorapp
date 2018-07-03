package com.softmasters.dawuro.umid;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Message {

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String token;//
	@DatabaseField
	private String sourceid;//person sending, phonenumber
	@DatabaseField
	private String destinationid;//recipient
	@DatabaseField
	private String uniqueuid;//UUID.randomuuid
	@DatabaseField
	private String msg;//json of original message to be verified or updated
	@DatabaseField
	private String messagetype;//location request or verification
	@DatabaseField
	private String messagestatus;//
	@DatabaseField
	private String macaddress;
	@DatabaseField
	private String regid;//token

	public Message() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSourceid() {
		return sourceid;
	}

	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}

	public String getDestinationid() {
		return destinationid;
	}

	public void setDestinationid(String destinationid) {
		this.destinationid = destinationid;
	}

	public String getUniqueuid() {
		return uniqueuid;
	}

	public void setUniqueuid(String uniqueuid) {
		this.uniqueuid = uniqueuid;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMessagetype() {
		return messagetype;
	}

	public void setMessagetype(String messagetype) {
		this.messagetype = messagetype;
	}

	public String getMessagestatus() {
		return messagestatus;
	}

	public void setMessagestatus(String messagestatus) {
		this.messagestatus = messagestatus;
	}

	public String getMacaddress() {
		return macaddress;
	}

	public void setMacaddress(String macaddress) {
		this.macaddress = macaddress;
	}

	public String getRegid() {
		return regid;
	}

	public void setRegid(String regid) {
		this.regid = regid;
	}
}
