package com.softmasters.dawuro.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.softmasters.dawuro.R;
import com.softmasters.dawuro.umid.Gallery;
import com.softmasters.dawuro.utils.GalleryAdapter;
import com.softmasters.dawuro.utils.UMIDDatabaseHelper;

import java.util.List;


public class IncidentActivity extends AppCompatActivity {

    TextView comments;
    TextView date;
    TextView status;
    ImageView pic;

    GridView gridView;

    private String dateReported;
    private String statusOfIncidence;
    private String commentsOnIncidence;
    public String applicantID;


    public String getApplicantID() {
        return applicantID;
    }

    public void setApplicantID(String applicantID) {
        this.applicantID = applicantID;
    }

    byte[] picture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incident_view);

        date = (TextView) findViewById(R.id.dateOfIncidence);
        status = (TextView) findViewById(R.id.statusOnIncidence);
        comments = (TextView) findViewById(R.id.commentsOnIncidence);
//        pic = (ImageView) findViewById(R.id.firstIncidenceImage);

        gridView = (GridView) findViewById(R.id.gridview);

        try {
            dateReported = getIntent().getStringExtra("date");
            statusOfIncidence = getIntent().getStringExtra("status");
            commentsOnIncidence = getIntent().getStringExtra("comments");
            picture = getIntent().getByteArrayExtra("picture");
            applicantID = getIntent().getStringExtra("applicantID");

            date.setText(dateReported);
            status.setText(statusOfIncidence);
            comments.setText(commentsOnIncidence);

            this.setApplicantID(applicantID);
            this.getApplicantID();

//            Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
//            pic.setImageBitmap(bitmap);

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

//        getGalleryByApplicantID("");

        gridView.setAdapter(new GalleryAdapter(this, getApplicantID()));

    }


}
