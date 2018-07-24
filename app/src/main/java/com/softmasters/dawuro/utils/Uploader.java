package com.softmasters.dawuro.utils;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Uploader {

	private static final int CONNECTION_TIMEOUT = 20 * 1000;
	private static final int READ_TIMEOUT = 20 * 1000;
	final private String protocol;
	final private String server;
 	String TAG = "Uploader";

	public Uploader(String protocol, String server) {
		this.protocol = protocol;
		this.server = server;
	}

	protected HttpURLConnection getBaseConnection(String endpoint)
			throws IOException {
		HttpURLConnection connection;
		URL url;

		try {
			url = new URL(protocol + "://" + server + "/" + endpoint);
			connection = (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			throw new IOException("Malformed URL");
		}
		connection.setDoInput(true);
		//connection.setConnectTimeout(CONNECTION_TIMEOUT);
		//connection.setReadTimeout(READ_TIMEOUT);
		return connection;
	}

	public String upload(String endpoint, PostData postData) {
		try {
			Log.d("Uploader", "Uploading...");
			HttpURLConnection connection = null;
			OutputStream out = null;
			String response = null;
			connection = getBaseConnection(endpoint);
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Charset", "utf-8");
			connection.setRequestProperty("Content-Type",
					postData.getContentType());
			connection.setRequestProperty("Accept", "text/json");
			try {
				out = new BufferedOutputStream(connection.getOutputStream(),
						8192);
				postData.write(out);
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
				Log.d(TAG, "Outputstream exception ");
			}finally {
				if(out != null){
					out.close();
				}
			}

			int result = connection.getResponseCode();
			Log.d(TAG, "Response Code : " + connection.getResponseCode());

			if (result == HttpURLConnection.HTTP_OK) {
				// reads server's response
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(connection.getInputStream()));
				response = reader.readLine();
				System.out.println("Server's response: " + response);
			} else {
				System.out.println("Message : "
						+ connection.getResponseMessage());
				System.out.println("Error : " + connection.getErrorStream());
				System.out.println("Server returned non-OK code: " + result);
			}
			Log.d(TAG, "Received a result");
			connection.disconnect();

			return response;
		} catch (Exception e) {
			Log.d(TAG, "Error connecting");
			e.printStackTrace();
			return null;
		}
	}
}
