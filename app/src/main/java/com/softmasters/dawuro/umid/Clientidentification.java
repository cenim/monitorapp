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
public class Clientidentification implements Serializable {

	private static final long serialVersionUID = 1L;
	@DatabaseField
	private String clientId;
	@DatabaseField
	private String macaddress;
	@DatabaseField(dataType = DataType.DATE_STRING)
	private Date registerdate;
	@DatabaseField(dataType = DataType.DATE_STRING)
	private Date modifieddate;
	@DatabaseField
	private String regid;
	@DatabaseField(generatedId = true)
	private int cid;
	@DatabaseField
	private String status;
	// new field
	@DatabaseField
	private String uniqueuid;

	public Clientidentification() {
	}

	public Clientidentification(int cid) {
		this.cid = cid;
	}

	public Clientidentification(int cid, String clientId) {
		this.cid = cid;
		this.clientId = clientId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getMacaddress() {
		return macaddress;
	}

	public void setMacaddress(String macaddress) {
		this.macaddress = macaddress;
	}

	public Date getRegisterdate() {
		return registerdate;
	}

	public void setRegisterdate(Date registerdate) {
		this.registerdate = registerdate;
	}

	public Date getModifieddate() {
		return modifieddate;
	}

	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}

	public String getRegid() {
		return regid;
	}

	public void setRegid(String regid) {
		this.regid = regid;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUniqueuid() {
		return uniqueuid;
	}

	public void setUniqueuid(String uniqueuid) {
		this.uniqueuid = uniqueuid;
	}

	@Override
	public String toString() {
		return "Clientidentification{" +
				"clientId='" + clientId + '\'' +
				", macaddress='" + macaddress + '\'' +
				", registerdate=" + registerdate +
				", modifieddate=" + modifieddate +
				", regid='" + regid + '\'' +
				", cid=" + cid +
				", status='" + status + '\'' +
				", uniqueuid='" + uniqueuid + '\'' +
				'}';
	}

}
