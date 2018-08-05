package com.softmasters.dawuro.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.softmasters.dawuro.R;
import com.softmasters.dawuro.activities.IncidentActivity;
import com.softmasters.dawuro.umid.Gallery;

import java.util.List;

public class GalleryAdapter extends BaseAdapter {

    Dao<Gallery, Integer> galleryDao;
    List<Gallery> galleryItems;


    UMIDDatabaseHelper umidDBHelper;


    private Context context;

    public Integer[] images = {
            R.drawable.building

    };
    public String id;
    public int size;
    String []imageList;

    public void setSize(int size) {
        this.size = size;
    }
    public int getSize() {
        return imageList.length;
    }



    public GalleryAdapter(Context context, String id,String []imageList) {
        this.context = context;
        this.id = id;
        this.imageList=imageList;
    }

    @Override
    public int getCount() {
        return this.getSize();
    }

    @Override
    public Object getItem(int position) {
        return imageList[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        String paths = getGalleryByApplicantID().get(0).getImagepath();
        String filter[] = paths.split(",");
        imageList=filter;
        this.setSize(filter.length);
        this.getSize();

        ImageView imageView = new ImageView(context);
        imageView.setImageURI(Uri.parse(filter[position]));

        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setLayoutParams(new GridView.LayoutParams(130, 130));
        return imageView;
    }

    public List<Gallery> getGalleryByApplicantID() {
        if (umidDBHelper == null)
            umidDBHelper = OpenHelperManager.getHelper(context, UMIDDatabaseHelper.class);
        try {
            galleryDao = umidDBHelper.getGalleryDao();

            galleryItems = galleryDao.queryBuilder().where().eq("applicantid", id).query();


        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return galleryItems;
    }
}
