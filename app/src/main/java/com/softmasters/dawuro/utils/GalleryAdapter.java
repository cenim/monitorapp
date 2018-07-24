package com.softmasters.dawuro.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
            R.drawable.building,
            R.drawable.googleg_disabled_color_18,
            R.drawable.bg_onpressed_state_rect_red,
            R.drawable.add,
            R.drawable.places_ic_search
    };
    public String id;

    public GalleryAdapter(Context context, String id) {
        this.context = context;
        this.id = id;
    }

    @Override

    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


//        Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
        byte picture[] = getGalleryByApplicantID().get(0).getPicture();

        Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);

        List<Byte> pictures = null;
        Bitmap bitmaps[] = {bitmap};

//        Toast.makeText(context, picture.toString(), Toast.LENGTH_LONG).show();
        ImageView imageView = new ImageView(context);
//        imageView.setImageResource(images[position]);
        imageView.setImageBitmap(bitmaps[0]);
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
