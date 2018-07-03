package com.softmasters.dawuro.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.softmasters.dawuro.R;
import com.softmasters.dawuro.umid.Comments;
import com.softmasters.dawuro.umid.Contactinformation;
import com.softmasters.dawuro.umid.Gallery;
import com.softmasters.dawuro.umid.Location;
import com.softmasters.dawuro.umid.MessageDetails;
import com.softmasters.dawuro.utils.SavedItemsAdapter;
import com.softmasters.dawuro.utils.UMIDDatabaseHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SavedItemsActivity extends AppCompatActivity {

    Dao<Comments, Integer> commentsDao;
    Dao<Location, Integer> locationDao;
    Dao<Contactinformation, Integer> contactinformationDao;
    Dao<Gallery, Integer> galleryDao;

    UMIDDatabaseHelper umidDBHelper;

    ListView listViewSavedItems;
    Context context;
    SavedItemsAdapter itemsAdapter;
    List messages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_items);

        context = SavedItemsActivity.this;
        initDaos();

        listViewSavedItems = (ListView) findViewById(R.id.listViewSavedItems);
        messages = loadMessageDetails();
        Collections.reverse(messages);
        if(messages.size() > 0){
            itemsAdapter = new SavedItemsAdapter(context, messages);
            listViewSavedItems.setAdapter(itemsAdapter);
        }else{
            listViewSavedItems.setEmptyView(findViewById(R.id.empty_view_text));
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

    //populate the messageDetails and use it as data model
    List<MessageDetails> loadMessageDetails(){
        List<MessageDetails> messageDetails = new ArrayList<>();
        List<Gallery> galleries = new ArrayList<>();
        List<Location> locations  = new ArrayList<>();
        List<Contactinformation> contactinformations = new ArrayList<>();
        List<Comments> comments = new ArrayList<>();

        MessageDetails messageDetail = null;

        try {
            comments = commentsDao.queryForAll();

            for(int i = 0; i < comments.size();i++){
                messageDetail = new MessageDetails();
                try{
                    galleries = galleryDao.query(galleryDao.queryBuilder().where()
                            .eq("applicantid",comments.get(i).getApplicantid()).prepare());
                    if(galleries.size() > 0){
                        messageDetail.setGallery(galleries);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                try{
                    locations = locationDao.query(locationDao.queryBuilder().where()
                            .eq("applicantid", comments.get(i).getApplicantid()).prepare());
                    if(locations.size() > 0){
                        messageDetail.setLocation(locations);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                try{
                    contactinformations = contactinformationDao.query(contactinformationDao.queryBuilder()
                            .where().eq("applicantid", comments.get(i).getApplicantid()).prepare());
                    if(contactinformations.size() > 0){
                        messageDetail.setContactinformation(contactinformations.get(0));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                messageDetail.setComments(comments.get(i));

                messageDetails.add(messageDetail);
            }
            return messageDetails;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
