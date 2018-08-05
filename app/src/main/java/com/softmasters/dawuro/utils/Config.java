package com.softmasters.dawuro.utils;

public interface Config {

	//Registration Server
	public String REGISTRATION_PROTOCOL = "http";
	public String REGISTRATION_SERVER = "10.0.0.3:8080/galamsey";//192.168.1.17:27261,omanba.ucomgh.com:8089

	public String SUCCESSFUL_REGISTRATION = "SUCCESSFUL";
	public String START = "START_APP";
	public String PACKAGE = "package";

	public int READ_PHONE_STATE = 2;
	public int GPS_CHECK = 1;

	public String SEND_STATUS = "1";
	public String UPDATE_STATUS = "2";
	String SENT_TO_SERVER="3";

	public String ENDPOINT = "gms";

}
