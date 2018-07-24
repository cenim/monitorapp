package com.softmasters.dawuro.services;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.softmasters.dawuro.controllers.CommentsController;
import com.softmasters.dawuro.controllers.ContactinformationController;
import com.softmasters.dawuro.controllers.GalleryController;
import com.softmasters.dawuro.controllers.LocationController;
import com.softmasters.dawuro.umid.Basicinformation;
import com.softmasters.dawuro.umid.Businessinformation;
import com.softmasters.dawuro.umid.Clientidentification;
import com.softmasters.dawuro.umid.Comments;
import com.softmasters.dawuro.umid.Contactinformation;
import com.softmasters.dawuro.umid.Gallery;
import com.softmasters.dawuro.umid.Identification;
import com.softmasters.dawuro.umid.Location;
import com.softmasters.dawuro.umid.MessageDetails;
import com.softmasters.dawuro.umid.ResponseEntity;
import com.softmasters.dawuro.umid.SuccessPojo;
import com.softmasters.dawuro.utils.Config;
import com.softmasters.dawuro.utils.MonitorUtils;
import com.softmasters.dawuro.utils.PostData;
import com.softmasters.dawuro.utils.UMIDDatabaseHelper;
import com.softmasters.dawuro.utils.Uploader;

import java.util.ArrayList;
import java.util.List;


public class CapturedDataService extends Service {

    String TAG = "CapturedDataService";
    Dao<Comments, Integer> commentsDao;
    Dao<Basicinformation, Integer> basicinformationDao;
    Dao<Businessinformation, Integer> businessinformationDao;
    Dao<Contactinformation, Integer> contactinformationDao;
    Dao<Clientidentification, Integer> clientidentificationDao;
    Dao<Gallery, Integer> galleryDao;
    Dao<Identification, Integer> idDao;
    Dao<Location, Integer> locationDao;

    UMIDDatabaseHelper umidDBHelper;
    Context context;
    Gson gson;

    PostData postData;
    Uploader uploader;

    ContactinformationController contactinformationController;
    CommentsController commentsController;
    GalleryController galleryController;
    LocationController locationController;


    public CapturedDataService(){

    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = CapturedDataService.this;
        Log.w(TAG,"CDS created");

        umidDBHelper = new UMIDDatabaseHelper(context);
        gson = new Gson();
        initDaos();
        initDaoControllers();

        uploader = new Uploader(Config.REGISTRATION_PROTOCOL, Config.REGISTRATION_SERVER);
        postData = new PostData();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.w(TAG,"Start Command initiated");
                //Perform all background tasks here.
                if(MonitorUtils.checkNetworkConnectivity(context)){
                    sendUnsentData();
                }
            }
        });

        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public List<MessageDetails> prepUnsentData(){

        Log.w(TAG,"CDS unsent prep");
        List<MessageDetails> messageDetails = new ArrayList<>();
        List<Comments> comments = new ArrayList<>();
        List<Location> locations = new ArrayList<>();
        List<Gallery> galleries = new ArrayList<>();
        List<Contactinformation> contactinformations = new ArrayList<>();
        List<Basicinformation> basicinformations = new ArrayList<>();
        List<Businessinformation> businessinformations = new ArrayList<>();
        MessageDetails messageDetail;

        Log.w(TAG, "Before Try");
        try {

            Log.w(TAG, "After Try");
            comments = commentsDao.query(commentsDao.queryBuilder().where().eq("status",Config.SEND_STATUS).prepare());

            Log.w(TAG, "Comments "+ comments.size());
            if(comments.size() > 0){
                Log.w(TAG, "Comments : "+ comments.size());
                //get all related

                messageDetail = new MessageDetails();
                messageDetail.setComments(comments.get(0));
                for(Comments comment : comments){
                    try{
                        locations = locationDao.query(locationDao.queryBuilder().where().
                                eq("applicantid", comment.getApplicantid()).prepare());
                        if(locations.size() > 0){
                            messageDetail.setLocation(locations);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    try{
                        galleries = galleryDao.query(galleryDao.queryBuilder().where()
                                .eq("applicantid",comment.getApplicantid()).prepare());
                        if(galleries.size() > 0){
                            messageDetail.setGallery(galleries);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    try {
                        contactinformations = contactinformationDao.query(contactinformationDao.queryBuilder()
                                .where().eq("applicantid",comment.getApplicantid()).prepare());
                        if (contactinformations.size() > 0){
                            messageDetail.setContactinformation(contactinformations.get(0));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    messageDetails.add(messageDetail);
                }

                Log.w(TAG, "MessageDetails : "+messageDetails.size());
            }else{
                Log.w(TAG, "No Comments");
            }
            return messageDetails;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void sendUnsentData(){
        try {
            //complie and send the data to server
            AsyncTask<byte[], Void, String> asyncTask = new AsyncTask<byte[], Void, String>() {
                @Override
                protected String doInBackground(byte[]... params) {
                    Log.w(TAG,"Params : "+params.length);
                    if (params[0].length > 0) {
                        postData.addValue("compression", "gzip");
                        postData.addData("Data", "application/octet-stream", params[0]);// "application/octet-stream"
                        return uploader.upload("gms", postData);
                    } else {
                        return "Error";
                    }
                }

                @Override
                protected void onPostExecute(String s) {
                    if(!s.isEmpty()){
                        SuccessPojo[] successPojos = gson.fromJson(s, SuccessPojo[].class);
                        try {
                            for (SuccessPojo successPojo : successPojos) {
                                Log.w("Success", "before updates");
                                if (successPojo.getStatus().equalsIgnoreCase("success")) {
                                    List<ResponseEntity> responseEntities = new ArrayList<>();

                                    responseEntities = successPojo.getResponseentity();
                                    for (ResponseEntity responseEntity : responseEntities) {
                                        updateStatuses(responseEntity.getUniqueuid());
                                        Log.w("Response Entity","unique uid : "+ responseEntity.getUniqueuid());
                                    }
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            };

            //prepare the data first
            asyncTask.execute(MonitorUtils.compress(gson.toJson(prepUnsentData())));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void initDaos() {
        try {
            commentsDao = getUMIDHelper().getCommentsDao();
            clientidentificationDao = getUMIDHelper().getClientidentificationDao();
            idDao = getUMIDHelper().getIdentificationDao();
            locationDao = getUMIDHelper().getLocationDao();
            galleryDao = getUMIDHelper().getGalleryDao();
            contactinformationDao = getUMIDHelper().getContactinformationDao();
            basicinformationDao = getUMIDHelper().getBasicinformationDao();
            businessinformationDao = getUMIDHelper().getBusinessinformationDao();
        } catch (Exception e) {
            e.printStackTrace();
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
        Log.w("","Update Statuses");
        List<Comments> comments = new ArrayList<Comments>();
        List<Contactinformation> contactinformations = new ArrayList<Contactinformation>();
        List<Gallery> galleries = new ArrayList<Gallery>();
        List<Location> locations = new ArrayList<Location>();

        Log.d("Update", "Updating Status...");
        try {
            try{
                comments = commentsDao.query(commentsDao.queryBuilder().where().eq("uniqueuid",uuid).prepare());
                if(comments.size() > 0){
                    commentsController.updateComment(comments.get(0), Config.UPDATE_STATUS, uuid);
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            try {
                contactinformations = contactinformationDao
                        .query(contactinformationDao.queryBuilder().where()
                                .eq("uniqueuid", uuid).prepare());

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
}
