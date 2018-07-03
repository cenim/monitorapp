/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softmasters.dawuro.umid;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ESS
 */
@DatabaseTable
public class Messaging implements Serializable {

	private static final long serialVersionUID = 1L;
	@DatabaseField
	private String applicantid;
	@DatabaseField
	private String sendertelephone;
	@DatabaseField
	private String receivertelephone;
	@DatabaseField
	private String message;
	@DatabaseField(dataType = DataType.DATE_STRING)
	private Date messagedate;
	@DatabaseField
	private String messagestatus;
	@DatabaseField
	private String messagetype;
	@DatabaseField
	private String messageidentifier;
	@DatabaseField
	private String messageinfo;
	@DatabaseField(generatedId = true)
	private int messageid;
	@DatabaseField
	private String macaddress;
	// new field
	@DatabaseField
	private String uniqueuid;

	public Messaging() {
	}

	public Messaging(int messageid) {
		this.messageid = messageid;
	}

	public String getApplicantid() {
		return applicantid;
	}

	public void setApplicantid(String applicantid) {
		this.applicantid = applicantid;
	}

	public String getSendertelephone() {
		return sendertelephone;
	}

	public void setSendertelephone(String sendertelephone) {
		this.sendertelephone = sendertelephone;
	}

	public String getReceivertelephone() {
		return receivertelephone;
	}

	public void setReceivertelephone(String receivertelephone) {
		this.receivertelephone = receivertelephone;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getMessagedate() {
		return messagedate;
	}

	public void setMessagedate(Date messagedate) {
		this.messagedate = messagedate;
	}

	public String getMessagestatus() {
		return messagestatus;
	}

	public void setMessagestatus(String messagestatus) {
		this.messagestatus = messagestatus;
	}

	public String getMessagetype() {
		return messagetype;
	}

	public void setMessagetype(String messagetype) {
		this.messagetype = messagetype;
	}

	public String getMessageidentifier() {
		return messageidentifier;
	}

	public void setMessageidentifier(String messageidentifier) {
		this.messageidentifier = messageidentifier;
	}

	public String getMessageinfo() {
		return messageinfo;
	}

	public void setMessageinfo(String messageinfo) {
		this.messageinfo = messageinfo;
	}

	public int getMessageid() {
		return messageid;
	}

	public void setMessageid(int messageid) {
		this.messageid = messageid;
	}

	public String getMacaddress() {
		return macaddress;
	}

	public void setMacaddress(String macaddress) {
		this.macaddress = macaddress;
	}

	public String getUniqueuid() {
		return uniqueuid;
	}

	public void setUniqueuid(String uniqueuid) {
		this.uniqueuid = uniqueuid;
	}

	@Override
	public String toString() {
		return "Messaging{" +
				"applicantid='" + applicantid + '\'' +
				", sendertelephone='" + sendertelephone + '\'' +
				", receivertelephone='" + receivertelephone + '\'' +
				", message='" + message + '\'' +
				", messagedate=" + messagedate +
				", messagestatus='" + messagestatus + '\'' +
				", messagetype='" + messagetype + '\'' +
				", messageidentifier='" + messageidentifier + '\'' +
				", messageinfo='" + messageinfo + '\'' +
				", messageid=" + messageid +
				", macaddress='" + macaddress + '\'' +
				", uniqueuid='" + uniqueuid + '\'' +
				'}';
	}
}
