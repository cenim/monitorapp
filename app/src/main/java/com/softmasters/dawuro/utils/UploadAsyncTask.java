package com.softmasters.dawuro.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.softmasters.dawuro.ImageUploaderAsyncTask;

/**
 * Created by Softmasters on 05-Oct-16.
 */

public class UploadAsyncTask extends AsyncTask<byte[], Void, String> {

    ProgressDialog progressDialog;
    Context context;
    PostData postData;
    Uploader uploader;
    String applicantuid;
    String jsonString;

    AsyncTaskCompleteListener<String> callback;

    public UploadAsyncTask(Context context, AsyncTaskCompleteListener<String> callback,  String jsonString){
        this.context = context;
        this.callback = callback;
        this.jsonString = jsonString;
        postData = new PostData();
        uploader = new Uploader(Config.REGISTRATION_PROTOCOL, Config.REGISTRATION_SERVER);

        Log.d("","Uploader");
    }
    @Override
    protected void onPreExecute() {
        if (progressDialog != null) {
            Log.i("Progress", "Dialog");
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Uploading ...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    protected String doInBackground(byte[]... params) {
        Log.d("Params","Params : "+params[0]);
        if (params[0].length > 0) {

            postData.addValue("compression", "gzip");
            postData.addData("Data", "application/octet-stream", params[0]);// "application/octet-stream"
            return uploader.upload("gms", postData);
        } else {
            return null;
        }
    }

    protected void onPostExecute(String result) {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog.cancel();
        }

        System.out.println("ImageAsyncTask : "+jsonString);
        new ImageUploaderAsyncTask(context, jsonString).execute();

    }
}
