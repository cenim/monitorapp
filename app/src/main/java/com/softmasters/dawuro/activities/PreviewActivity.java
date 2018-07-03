package com.softmasters.dawuro.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.softmasters.dawuro.R;
import com.softmasters.dawuro.controllers.CommentsController;
import com.softmasters.dawuro.controllers.ContactinformationController;
import com.softmasters.dawuro.controllers.GalleryController;
import com.softmasters.dawuro.controllers.LocationController;
import com.softmasters.dawuro.umid.Comments;
import com.softmasters.dawuro.umid.Contactinformation;
import com.softmasters.dawuro.umid.Gallery;
import com.softmasters.dawuro.umid.Location;
import com.softmasters.dawuro.utils.AddressResults;
import com.softmasters.dawuro.utils.MonitorUtils;
import com.softmasters.dawuro.utils.UMIDDatabaseHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Softmasters on 02-May-17.
 */

public class PreviewActivity extends AppCompatActivity implements View.OnClickListener{

    Intent intent;
    ImageView imagePicture;
    VideoView imageVideo;
    TextView textGPSCoordinates, textTimestamp;
    ImageButton imageSend;
    String locationText, timestamp;
    EditText editComment;
    Button btnSend;

    Dao<Comments, Integer> commentsDao;
    Dao<Location, Integer> locationDao;
    Dao<Contactinformation, Integer> contactinformationDao;
    Dao<Gallery, Integer> galleryDao;

    ContactinformationController contactinformationController;
    CommentsController commentsController;
    GalleryController galleryController;
    LocationController locationController;

    UMIDDatabaseHelper umidDBHelper;
    Context context;
    static int READ_PHONE_STATE = 2;

    WifiManager wifiManager;
    WifiInfo wifiInfo;
    String macAddress;
    Thread thread;
    Gson gson;
    String city;
    String assembly;
    String region;
    String street;
    String country;
    String latitude;
    String longitude;
    byte[] picture;
    String filename;
    File picturePath;
    int pictureindex;
    boolean video;
    Bitmap bitmap;
    MediaController mediaController;
    private int position = 0;
    Bitmap imageBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.softmasters.dawuro.R.layout.activity_preview);

        System.out.println("Preview Activity");

        context = PreviewActivity.this;
        umidDBHelper = new UMIDDatabaseHelper(context);
        gson = new Gson();
        initDaos();
        initDaoControllers();

        if(mediaController == null){
            mediaController = new MediaController(context);
        }

        Toast.makeText(context, "Preview Activity", Toast.LENGTH_SHORT).show();

        imagePicture = (ImageView) findViewById(R.id.imagePicture);
        textGPSCoordinates = (TextView) findViewById(R.id.textGPSCoordinates);
        textTimestamp = (TextView) findViewById(R.id.textTimestamp);
        imageSend = (ImageButton) findViewById(R.id.conversation_send);
        editComment = (EditText) findViewById(R.id.conversation_message);
        btnSend = (Button) findViewById(R.id.btn_send);
        imageVideo = (VideoView) findViewById(R.id.imageVideo);
        imageVideo.setMediaController(mediaController);

        intent = getIntent();
        video = intent.getBooleanExtra("video", false);
        pictureindex = intent.getIntExtra("pictureindex", -1);
        locationText = intent.getStringExtra("location");
        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");
        timestamp = intent.getStringExtra("timestamp");

        System.out.println("Video : "+ video);

        textTimestamp.setText("Time: " + timestamp);
        textGPSCoordinates.setText("Location: " + locationText);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        if(video == false){
            //need to get the exact image to display here so we need the image path index
            if(pictureindex != -1){
                imagePicture.setImageURI(Uri.fromFile(
                        MonitorUtils.compressImage(FullPreviewActivity.getGalleries().get(pictureindex))));
                imagePicture.setScaleType(ImageView.ScaleType.FIT_XY);
                imageSend.setOnClickListener(this);
            }
        }else{
            //imagePicture.setVisibility(View.GONE);
//            imageVideo.setVideoPath(FullPreviewActivity.getPicturePaths().get(pictureindex));
//            imageVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                // Close the progress bar and play the video
//                public void onPrepared(MediaPlayer mp) {
//                    imageVideo.seekTo(position);
//                    if (position == 0) {
//                        imageVideo.start();
//                    } else {
//                        imageVideo.pause();
//                    }
//                }
//            });
            imageBitmap = ThumbnailUtils.createVideoThumbnail(FullPreviewActivity.getPicturePaths().get(pictureindex),
                    0);
            imagePicture.setImageBitmap(imageBitmap);
            imagePicture.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        btnSend.setOnClickListener(this);

        startAddressThread();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        filename = null;
        if(bitmap != null){
            bitmap.recycle();
            bitmap = null;
        }
    }

    UMIDDatabaseHelper getUMIDHelper() {
        try {
            if (umidDBHelper == null) {
                umidDBHelper = OpenHelperManager.getHelper(context,
                        UMIDDatabaseHelper.class);
            }
            return umidDBHelper;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    void initDaos() {
        try {
            commentsDao = getUMIDHelper().getCommentsDao();
            locationDao = getUMIDHelper().getLocationDao();
            galleryDao = getUMIDHelper().getGalleryDao();
            contactinformationDao = getUMIDHelper().getContactinformationDao();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void initDaoControllers() {
        try {
            contactinformationController = new ContactinformationController(context, contactinformationDao);
            commentsController = new CommentsController(context, commentsDao);
            galleryController = new GalleryController(context, galleryDao);
            locationController = new LocationController(context, locationDao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startAddressThread() {
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                String data = "";

                try {

                    // encode for special characters
                    String url = "http://maps.googleapis.com/maps/api/geocode/json"
                            + "?latlng="
                            + URLEncoder.encode(locationText, "utf-8")
                            + "&sensor=true";

                    // Fetching the data from web service
                    data = downloadUrl(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                threadMsg(data);
            }

            private void threadMsg(String msg) {

                if (!msg.equals(null) && !msg.equals("")) {
                    android.os.Message msgObj = handler.obtainMessage();
                    Bundle b = new Bundle();
                    b.putString("address", msg);
                    msgObj.setData(b);
                    handler.sendMessage(msgObj);
                }
            }

            Handler handler = new Handler() {
                @Override
                public void handleMessage(android.os.Message msg) {
                    String dataMsg = msg.getData().getString("address");
                    if (!TextUtils.isEmpty(dataMsg)) {
                        AddressResults addressResults = gson.fromJson(dataMsg,
                                AddressResults.class);
                        if (TextUtils.isEmpty(addressResults
                                .getError_message())) {
                            try {
                                List<AddressResults.Results> results = addressResults.getResults();
                                List<AddressResults.Results.AddressComponents> addressComponents = results
                                        .get(0).getAddress_components();

                                for (int i = addressComponents.size(); i > 0; i--) {

                                    for (String st : addressComponents.get(i - 1)
                                            .getTypes()) {
                                        if (st.equalsIgnoreCase("route")) {
                                            street = addressComponents.get(i - 1)
                                                    .getLong_name();
                                        } else if (st.equalsIgnoreCase("locality")) {
                                            city = addressComponents.get(i - 1)
                                                    .getLong_name();
                                        } else if (st
                                                .equalsIgnoreCase("administrative_area_level_1")) {
                                            region = addressComponents.get(i - 1)
                                                    .getLong_name();
                                        } else if (st
                                                .equalsIgnoreCase("administrative_area_level_2")) {
                                            assembly = addressComponents.get(i - 1)
                                                    .getLong_name();
                                        } else if (st.equalsIgnoreCase("country")) {
                                            country = addressComponents.get(i - 1)
                                                    .getLong_name();
                                        }

                                    }
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            Log.d("Empty", "Empty");
                        }

                    }
                }
            };
        });
        thread.start();
    }

    public String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.conversation_send:
                break;
            case R.id.btn_send:
                //launchTask();
                Intent intent = new Intent(context, FullPreviewActivity.class);
                intent.putExtra("location", locationText);
                intent.putExtra("latitude", ""+latitude);
                intent.putExtra("longitude",longitude);
                intent.putExtra("timestamp", " "+ timestamp);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("Position", imageVideo.getCurrentPosition());
        imageVideo.pause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("Position");
        imageVideo.seekTo(position);
    }
}