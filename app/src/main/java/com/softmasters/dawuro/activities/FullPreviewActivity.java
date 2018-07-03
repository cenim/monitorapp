package com.softmasters.dawuro.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import com.softmasters.dawuro.ImagePath;
import com.softmasters.dawuro.R;
import com.softmasters.dawuro.controllers.CommentsController;
import com.softmasters.dawuro.controllers.ContactinformationController;
import com.softmasters.dawuro.controllers.GalleryController;
import com.softmasters.dawuro.controllers.LocationController;
import com.softmasters.dawuro.umid.Comments;
import com.softmasters.dawuro.umid.Contactinformation;
import com.softmasters.dawuro.umid.Gallery;
import com.softmasters.dawuro.umid.Location;
import com.softmasters.dawuro.umid.MessageDetails;
import com.softmasters.dawuro.umid.ResponseEntity;
import com.softmasters.dawuro.umid.SuccessPojo;
import com.softmasters.dawuro.utils.AddressResults;
import com.softmasters.dawuro.utils.AsyncTaskCompleteListener;
import com.softmasters.dawuro.utils.Config;
import com.softmasters.dawuro.utils.ImageAdapter;
import com.softmasters.dawuro.utils.MonitorUtils;
import com.softmasters.dawuro.utils.UMIDDatabaseHelper;
import com.softmasters.dawuro.utils.UploadAsyncTask;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

public class FullPreviewActivity extends AppCompatActivity implements View.OnClickListener, AsyncTaskCompleteListener<String> {

    EditText editPOI;
    EditText editPOIPhone;
    EditText editSender;
    EditText editSenderPhone;
    EditText editSenderEmail;
    EditText editComments;
    View imageCapture;
    Button btnSend;

    Context context;
    Gson gson;
    UMIDDatabaseHelper umidDBHelper;

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
    Thread thread;
    Intent intent;
    String locationText;
    String timestamp;
    String applicantuid;

    Dao<Comments, Integer> commentsDao;
    Dao<Location, Integer> locationDao;
    Dao<Contactinformation, Integer> contactinformationDao;
    Dao<Gallery, Integer> galleryDao;

    ContactinformationController contactinformationController;
    CommentsController commentsController;
    GalleryController galleryController;
    LocationController locationController;

    static List<String> picturePaths;
    static List<ImagePath> imagePaths;
    static List<Location> locations;
    static List<Timestamp> timestamps;
    ImageAdapter imageAdapter;
    RecyclerView.LayoutManager recyclerManager;
    RecyclerView recyclerView;
    TextView emptyView;
    ImageView recordAudio;
    boolean recording;
    String audioRecordFile;

    MediaRecorder audioRecorder;
    String audioPath;
    File audioFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_preview);

        context = FullPreviewActivity.this;
        gson = new Gson();
        umidDBHelper = new UMIDDatabaseHelper(context);
        initDaos();
        initDaoControllers();

        intent = getIntent();
        locationText = intent.getStringExtra("location");
        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");
        timestamp = intent.getStringExtra("timestamp");

        prepareRecorder();

        //startAddressThread();

        editPOI = (EditText) findViewById(R.id.editPersonOfInterest);
        editPOIPhone = (EditText) findViewById(R.id.editPOIPhone);
        editSender = (EditText) findViewById(R.id.editSenderName);
        editSenderEmail = (EditText) findViewById(R.id.editSenderEmail);
        editSenderPhone = (EditText) findViewById(R.id.editSenderPhone);
        editComments = (EditText) findViewById(R.id.editCommentsPreview);
        recordAudio = (ImageView) findViewById(R.id.record_audio);
        emptyView = (TextView) findViewById(R.id.empty_view);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(recyclerManager);
        if (FullPreviewActivity.getPicturePaths().size() > 0) {
            imageAdapter = new ImageAdapter(context, FullPreviewActivity.getImagePaths());
            recyclerView.setAdapter(imageAdapter);
            emptyView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }

        imageCapture = findViewById(R.id.imageCapture);
        btnSend = (Button) findViewById(R.id.btnSendFullPreview);

        imageCapture.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        recordAudio.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSendFullPreview:
                launchTask();
                break;
            case R.id.imageCapture:
                Intent intent = new Intent(context, FullCameraActivity.class);
                startActivity(intent);
                break;
            case R.id.record_audio:
                if (recording) {
                    Toast.makeText(context, "Stop", Toast.LENGTH_SHORT).show();
                    audioRecorder.stop();
                    audioRecorder.release();
                    recording = false;
                } else {
                    Toast.makeText(context, "Start", Toast.LENGTH_SHORT).show();
                    try {
                        audioRecorder.prepare();
                        audioRecorder.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    recording = true;
                }
                break;
        }
    }

    @Override
    public void launchTask() {
        System.out.println("Starting asynctask...");
        AsyncTask<Void, Void, byte[]> asyncTask = new AsyncTask<Void, Void, byte[]>() {

            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                progressDialog = new ProgressDialog(context);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("Preparing ...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected byte[] doInBackground(Void... params) {
                return sendMessage();
            }

            @Override
            protected void onPostExecute(byte[] bytes) {
                Log.d("","PE Applicant id = "+applicantuid);
                if (progressDialog != null) {
                    progressDialog.dismiss();
                    progressDialog.cancel();
                }

                Toast.makeText(context, "Post Execute", Toast.LENGTH_SHORT).show();
                byte[] filebytes = null;
                Gallery gallery;
                List<Gallery> galleries = new ArrayList<>();
                List<Location> mlocations = new ArrayList<>();
                String galleryuid, locationuid;
                for (int i = 0; i < imagePaths.size(); i++) {
                    try {
                        filebytes = FileUtils.readFileToByteArray(new File(imagePaths.get(i).getImagePath()));
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    String filePath;
                    if(!imagePaths.get(i).isVideo()){
                        filePath = imagePaths.get(i).getImagePath();
                        galleryuid = MonitorUtils.generateUUID();
                        gallery = new Gallery();
                        gallery.setApplicantid(applicantuid);
                        gallery.setLatitude(locations.get(i).getLatitude());
                        gallery.setLongitude(locations.get(i).getLongitude());
                        gallery.setMacaddress(MonitorUtils.getMacAddress(context));
                        if (imagePaths.get(i).isVideo()) {
                            gallery.setPicture(filebytes);
                        } else {
                            gallery.setPicture(MonitorUtils.convertFileToBytes(MonitorUtils.compressImage(filePath, context)));
                        }
                        gallery.setStatus(Config.SEND_STATUS);
                        gallery.setTimestamp(MonitorUtils.convertTimestampToDate(timestamps.get(i)));
                        gallery.setUniqueuid(galleryuid);

                        galleries.add(gallery);

                        locationuid = MonitorUtils.generateUUID();

                        locations.get(i).setApplicantid(applicantuid);
                        locations.get(i).setAssembly(assembly);
                        locations.get(i).setCity(city);
                        locations.get(i).setCountry(country);
                        locations.get(i).setMacaddress(MonitorUtils.getMacAddress(context));
                        locations.get(i).setRegion(region);
                        locations.get(i).setStatus(Config.SEND_STATUS);
                        locations.get(i).setStreet(street);
                        locations.get(i).setUniqueuid(locationuid);

                        mlocations.add(locations.get(i));
                        Log.e("", "Size Locations :" + mlocations.size());
                        Log.e("", "Location : " + locations.get(i).toString());
                    }
                }
                gson = new Gson();

                MessageDetails messageDetail = new MessageDetails();
                List<MessageDetails> messageDetails = new ArrayList<>();
                messageDetail.setGallery(galleries);
                messageDetail.setLocation(mlocations);

                messageDetails.add(messageDetail);
                String jsonString = gson.toJson(messageDetails);

                UploadAsyncTask uploadAsyncTask = new UploadAsyncTask(context, FullPreviewActivity.this, jsonString);
                uploadAsyncTask.execute(bytes);
            }
        };
        asyncTask.execute();
    }

    @Override
    public void onTaskComplete(String result) {
        Log.w("Task Result", "Task Result : " + result);
        String successId = null;
        if (result != null) {
            if (!result.equalsIgnoreCase("error")) {
                SuccessPojo[] successPojos = gson.fromJson(result, SuccessPojo[].class);
                try {
                    for (SuccessPojo successPojo : successPojos) {
                        if (successPojo.getStatus().equalsIgnoreCase("success")) {
                            List<ResponseEntity> responseEntities = new ArrayList<>();

                            responseEntities = successPojo.getResponseentity();
                            for (ResponseEntity responseEntity : responseEntities) {
                                updateStatuses(responseEntity.getUniqueuid());
                            }
                            successId = successPojo.getApplicantid();
                            showSuccessDialog(successId);
                        } else {
                            showErrorDialog();
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    showErrorDialog();
                }
            } else {
                showErrorDialog();
            }
        } else {
            showErrorDialog();
        }
    }

    //data capture before sendMessage();
    private void attemptDataTransfer() {
        //checks for necessary use
        if (TextUtils.isEmpty(editSenderEmail.getText().toString())) {

        }
    }

    byte[] sendMessage() {
        Log.d("PreviewActivity", "Send Message");
        List<MessageDetails> messageDetails = new ArrayList<>();
        try {
            MessageDetails messageDetail = new MessageDetails();

            final Comments comments = new Comments();
            final Contactinformation contactinformation = new Contactinformation();
            final List<Location> mlocations = new ArrayList<>();
            Location location = new Location();
            final List<Gallery> galleries = new ArrayList<>();
            Gallery gallery;

            applicantuid = null;
            String commentsuid = null;
            String contactinfouid = null;
            String locationuid = null;
            String galleryuid = null;

            applicantuid = MonitorUtils.generateUUID();
            commentsuid = MonitorUtils.generateUUID();
            contactinfouid = MonitorUtils.generateUUID();

            comments.setApplicantid(applicantuid);
            comments.setUniqueuid(commentsuid);
            comments.setStatus(Config.SEND_STATUS);
            comments.setMacaddress(MonitorUtils.getMacAddress(context));
            comments.setApplieddate(new Date());
            if (!TextUtils.isEmpty(editComments.getText().toString())) {
                comments.setComment(editComments.getText().toString());
            }
            contactinformation.setApplicantid(applicantuid);
            contactinformation.setMacaddress(MonitorUtils.getMacAddress(context));
            if (!TextUtils.isEmpty(editSenderEmail.getText().toString())) {
                contactinformation.setEmail(editSenderEmail.getText().toString());
            }
            if (!TextUtils.isEmpty(editSenderPhone.getText().toString())) {
                contactinformation.setMobile(editSenderPhone.getText().toString());
            }
            contactinformation.setStatus(Config.SEND_STATUS);
            if (!TextUtils.isEmpty(editSenderPhone.getText().toString())) {
                contactinformation.setTelephone(editSenderPhone.getText().toString());
            }
            contactinformation.setUniqueuid(contactinfouid);
            if (!TextUtils.isEmpty(editPOI.getText().toString())) {
                contactinformation.setPersonofinterest(editPOI.getText().toString());
            }
            if (!TextUtils.isEmpty(editPOIPhone.getText().toString())) {
                contactinformation.setPoitelephone(editPOIPhone.getText().toString());
            }
            if (!TextUtils.isEmpty(editSender.getText().toString())) {
                contactinformation.setContactname(editSender.getText().toString());
            }
            System.out.println("Picture Paths");
            Log.w("", "PicturePaths : " + imagePaths.size());
            for (int i = 0; i < imagePaths.size(); i++) {
                String filePath;
                if(!imagePaths.get(i).isVideo()){
                    filePath = imagePaths.get(i).getImagePath();
                    galleryuid = MonitorUtils.generateUUID();
                    gallery = new Gallery();
                    gallery.setApplicantid(applicantuid);
                    gallery.setLatitude(locations.get(i).getLatitude());
                    gallery.setLongitude(locations.get(i).getLongitude());
                    gallery.setMacaddress(MonitorUtils.getMacAddress(context));
                    if (imagePaths.get(i).isVideo()) {
                        gallery.setPicture(MonitorUtils.convertFileToBytes(filePath));
                    } else {
                        gallery.setPicture(MonitorUtils.convertFileToBytes(MonitorUtils.compressImage(filePath, context)));
                    }
                    gallery.setStatus(Config.SEND_STATUS);
                    gallery.setTimestamp(MonitorUtils.convertTimestampToDate(timestamps.get(i)));
                    gallery.setUniqueuid(galleryuid);

                    galleries.add(gallery);

                    locationuid = MonitorUtils.generateUUID();

                    locations.get(i).setApplicantid(applicantuid);
                    locations.get(i).setAssembly(assembly);
                    locations.get(i).setCity(city);
                    locations.get(i).setCountry(country);
                    locations.get(i).setMacaddress(MonitorUtils.getMacAddress(context));
                    locations.get(i).setRegion(region);
                    locations.get(i).setStatus(Config.SEND_STATUS);
                    locations.get(i).setStreet(street);
                    locations.get(i).setUniqueuid(locationuid);

                    mlocations.add(locations.get(i));
                    Log.e("", "Size Locations :" + mlocations.size());
                    Log.e("", "Location : " + locations.get(i).toString());
                }
            }


            messageDetail = new MessageDetails();
            messageDetail.setComments(comments);
            messageDetail.setContactinformation(contactinformation);
            messageDetail.setGallery(galleries);
            messageDetail.setLocation(mlocations);

            messageDetails.add(messageDetail);
            System.out.println("Method Size : " + messageDetails.size());

            try {
                TransactionManager.callInTransaction(getUMIDHelper().getConnectionSource(), new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {

                        System.out.println("Saving the entities");
                        commentsController.saveOrUpdateCommentsController(
                                comments.getApplicantid(),
                                comments.getApplieddate(),
                                comments.getComment(),
                                comments.getMacaddress(),
                                comments.getStatus(),
                                comments.getUniqueuid());

                        for (Location location : mlocations) {
                            locationController.saveOrUpdateLocation(
                                    location.getApplicantid(),
                                    location.getAreacode(),
                                    location.getAssembly(),
                                    location.getBusinessid(),
                                    location.getCity(),
                                    location.getCountry(),
                                    location.getDestination(),
                                    location.getHousenumber(),
                                    location.getLatitude(),
                                    location.getLocationtype(),
                                    location.getLongitude(),
                                    location.getMacaddress(),
                                    location.getPopularname(),
                                    location.getRegion(),
                                    location.getStatus(),
                                    location.getStreet(),
                                    location.getUniqueuid(),
                                    location.getZipcode());
                        }

                        contactinformationController
                                .saveOrUpdateContactinformation(
                                        contactinformation
                                                .getAlternatemobile(),
                                        contactinformation
                                                .getApplicantid(),
                                        contactinformation
                                                .getBusinessid(),
                                        contactinformation.getContactname(),
                                        contactinformation
                                                .getContacttype(),
                                        contactinformation.getEmail(),
                                        contactinformation.getFax(),
                                        contactinformation.getMacaddress(),
                                        contactinformation.getMobile(),
                                        contactinformation.getPersonofinterest(),
                                        contactinformation
                                                .getPostofficebox(),
                                        contactinformation.getPoitelephone(),
                                        contactinformation.getStatus(),
                                        contactinformation
                                                .getTelephone(),
                                        contactinformation.getUniqueuid());

                        try {
                            for (Gallery gallery : galleries) {
                                galleryController.saveOrUpdateGallery(
                                        gallery.getApplicantid(),
                                        gallery.getBusinessid(),
                                        gallery.getLatitude(),
                                        gallery.getLongitude(),
                                        gallery.getMacaddress(),
                                        gallery.getPicture(),
                                        gallery.getStatus(),
                                        gallery.getTimestamp(),
                                        gallery.getUniqueuid());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println("Done saving");
                        return null;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }


            gson = new Gson();

            try {
                File file = null;
                try {
                    file = new File(Environment.getExternalStorageDirectory().getPath()
                            + "/omanba/");
                    if (!file.exists()) {
                        file.mkdir();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                File nFile = new File(file.getPath() + File.separator + ""
                        + System.currentTimeMillis() + ".json");
                FileOutputStream fos = new FileOutputStream(nFile);

                try {
                    try {
                        if (!nFile.exists()) {
                            nFile.createNewFile();
                        }
                    } catch (Exception e) {
                        Log.w("File", "Exception : " + e.getMessage());
                        e.printStackTrace();
                    }

                    Log.w("MessageDetails", "MessageDetails : " + gson.toJson(messageDetails));
                    byte[] textBytes = gson.toJson(messageDetails).getBytes();
                    Log.w("", "TextBytes : " + textBytes.length);
                    fos.write(textBytes);
                    fos.flush();
                    fos.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            byte[] bytes = MonitorUtils.compress(gson.toJson(messageDetails));
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (messageDetails != null) {
                messageDetails.clear();
            }
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

    public void updateStatuses(String uuid) {
        List<Comments> comments = new ArrayList<Comments>();
        List<Contactinformation> contactinformations = new ArrayList<Contactinformation>();
        List<Gallery> galleries = new ArrayList<Gallery>();
        List<Location> locations = new ArrayList<Location>();

        Log.d("Update", "Updating Status...");
        try {
            try {
                comments = commentsDao.query(commentsDao.queryBuilder().where().eq("uniqueuid", uuid).prepare());
                if (comments.size() > 0) {
                    commentsController.updateComment(comments.get(0), Config.UPDATE_STATUS, uuid);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                contactinformations = contactinformationDao
                        .query(contactinformationDao.queryBuilder().where()
                                .eq("uniqueuid", uuid).prepare());

                Log.d("", "Contactinformation size : " + contactinformations.size());
                if (contactinformations.size() > 0) {
                    contactinformationController.updateContactinformation(
                            contactinformations.get(0), Config.UPDATE_STATUS, uuid);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                galleries = galleryDao.query(galleryDao.queryBuilder().where()
                        .eq("uniqueuid", uuid)
                        .prepare());
                if (galleries.size() > 0) {
                    galleryController.updateGallery(galleries.get(0),
                            Config.UPDATE_STATUS, uuid);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                locations = locationDao.query(locationDao.queryBuilder()
                        .where().eq("uniqueuid", uuid).prepare());
                if (locations.size() > 0) {
                    locationController.updateLocation(locations.get(0),
                            Config.UPDATE_STATUS, uuid);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSuccessDialog(final String successId) {
        // Your location* has been registered successfully
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Registration");
        builder.setMessage("Sent successfully");
        final EditText editInputTelephone = new EditText(context);
        editInputTelephone.setHint(R.string.telephone_hint);
        editInputTelephone.setTextAppearance(context, android.R.style.TextAppearance_Small);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                picturePaths = new ArrayList<String>();
                Intent intent = new Intent(context, HomeActivity.class);
                startActivity(intent);
            }
        });

        builder.show();
    }

    public void showErrorDialog() {
        // Your location* has been registered unsuccessfully
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Registration");
        builder.setMessage("Registration successfully saved.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                picturePaths = new ArrayList<String>();
                Intent intent = new Intent(context, HomeActivity.class);
                startActivity(intent);
            }
        });

        if (builder != null) {
            builder.show();
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
                    data = MonitorUtils.downloadUrl(url);
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

    public static List<String> getGalleries() {
        if (picturePaths == null) {
            picturePaths = new ArrayList<>();
        }
        return picturePaths;
    }

    public static void setGalleries(List<String> picturePaths) {
        FullPreviewActivity.picturePaths = picturePaths;
    }

    public static List<Location> getLocations() {
        if (locations == null) {
            locations = new ArrayList<>();
        }
        return locations;
    }

    public static void setLocations(List<Location> locations) {
        FullPreviewActivity.locations = locations;
    }

    public static List<Timestamp> getTimestamps() {
        if (timestamps == null) {
            timestamps = new ArrayList<>();
        }
        return timestamps;
    }

    public static void setTimestamps(List<Timestamp> timestamps) {
        FullPreviewActivity.timestamps = timestamps;
    }

    public static List<String> getPicturePaths() {
        if (picturePaths == null) {
            picturePaths = new ArrayList<>();
        }
        return picturePaths;
    }

    public static List<ImagePath> getImagePaths() {
        if (imagePaths == null) {
            imagePaths = new ArrayList<>();
        }
        return imagePaths;
    }

    public static void setImagePaths(List<ImagePath> imagePaths) {
        FullPreviewActivity.imagePaths = imagePaths;
    }

    public static void setPicturePaths(List<String> picturePaths) {
        FullPreviewActivity.picturePaths = picturePaths;
    }

    private void prepareRecorder() {
        audioRecordFile = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/omanba/" + System.currentTimeMillis() + ".3gp";
        audioRecorder = new MediaRecorder();
        audioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        audioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        audioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        audioRecorder.setOutputFile(audioRecordFile);
    }
}
