package com.softmasters.dawuro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.softmasters.dawuro.activities.HomeActivity;
import com.softmasters.dawuro.utils.Config;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Softmasters on 18-Sep-17.
 */

public class ImageUploaderAsyncTask extends AsyncTask<String, Integer, String> {

    ProgressDialog progressDialog;
    Context context;
    String jsonString;

    public ImageUploaderAsyncTask(Context context, String jsonString) {
        this.context = context;
        this.jsonString = jsonString;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection.setFollowRedirects(false);
        HttpURLConnection connection = null;
        String fileName = "Data.gz";
        StringBuilder builder = new StringBuilder();
        try {
            String path = Config.REGISTRATION_PROTOCOL + File.pathSeparator
                    + Config.REGISTRATION_SERVER + File.pathSeparator+ Config.ENDPOINT;
            System.out.println("Path : "+path);
            connection = (HttpURLConnection) new URL(path).openConnection();
            connection.setRequestMethod("POST");
            String boundary = "---------------------------boundary";
            String tail = "\r\n--" + boundary + "--\r\n";
            connection.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            connection.setDoOutput(true);

            String metadataPart = "--"
                    + boundary
                    + "\r\n"
                    + "Content-Disposition: form-data; name=\"metadata\"\r\n\r\n"
                    + "" + "\r\n";

            String fileHeader1 = "--"
                    + boundary
                    + "\r\n"
                    + "Content-Disposition: form-data; name=\"uploadfile\"; filename=\""
                    + fileName + "\"\r\n"
                    + "Content-Type: application/octet-stream\r\n"
                    + "Content-Transfer-Encoding: binary\r\n" +
                    "compression: false\r\n";

            long fileLength = jsonString.length() + tail.length();
            String fileHeader2 = "Content-length: " + fileLength + "\r\n";
            String fileHeader = fileHeader1 + fileHeader2 + "\r\n";
            String stringData = metadataPart + fileHeader;

            long requestLength = stringData.length() + fileLength;
            connection.setRequestProperty("Content-length", "" + requestLength);
            connection.setRequestProperty("compression","false");
            connection.setFixedLengthStreamingMode((int) requestLength);
            connection.connect();

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(stringData);
            out.flush();

            int progress = 0;
            int bytesRead = 0;
            byte buf[] = new byte[1024];
            BufferedInputStream bufInput = new BufferedInputStream(IOUtils.toInputStream(jsonString));
            while ((bytesRead = bufInput.read(buf)) != -1) {
                // write output
                out.write(buf, 0, bytesRead);
                out.flush();
                progress += bytesRead;
                // update progress bar
                publishProgress(progress);
            }

            // Write closing boundary and close stream
            out.writeBytes(tail);
            out.flush();
            out.close();

            // Get server response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

        } catch (Exception e) {
            // Exception
        } finally {
            if (connection != null)
                connection.disconnect();
        }

        return builder.toString();
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "Pre Execute", Toast.LENGTH_SHORT).show();
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage("Uploading File...");
            progressDialog.setCancelable(false);
            progressDialog.setMax((int) jsonString.length());
            progressDialog.show();
        }
    }

    @Override
    protected void onPostExecute(String o) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        Toast.makeText(context, "Post Execute Done", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }
}
