package com.softmasters.dawuro.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import android.Manifest;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.softmasters.dawuro.R;
import com.softmasters.dawuro.services.CapturedDataService;
import com.softmasters.dawuro.utils.AdvertsPagerAdapter;
import com.softmasters.dawuro.utils.LoginActivity;
import com.softmasters.dawuro.utils.MonitorUtils;
import com.softmasters.dawuro.utils.ScrollSupportMapFragment;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Softmasters on 09-May-17.
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    LinearLayout imageUser;
    LinearLayout imageMiners;
    ViewFlipper viewPager;
    LinearLayout locationIcon;
    LinearLayout supportIcon;



    Context context;



    private static final int ERROR_DIALOG_REQUEST = 9001;
    ImageView police;
    ImageView fireService;
    ImageView electricity;
    ImageView waterCompany;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        police=(ImageView)findViewById(R.id.policeService);
        fireService=(ImageView)findViewById(R.id.fireService);
        electricity=(ImageView)findViewById(R.id.electricityCompany);
        waterCompany=(ImageView)findViewById(R.id.waterCompany);



        context = HomeActivity.this;

        imageUser = (LinearLayout) findViewById(R.id.imageUser);
        imageMiners = (LinearLayout) findViewById(R.id.imageMiner);
        viewPager = (ViewFlipper) findViewById(R.id.advertsPager);
        locationIcon = (LinearLayout) findViewById(R.id.location);
        supportIcon = (LinearLayout) findViewById(R.id.support);

        imageUser.setOnClickListener(this);
        imageMiners.setOnClickListener(this);

        police.setOnClickListener(this);
        fireService.setOnClickListener(this);
        electricity.setOnClickListener(this);
        waterCompany.setOnClickListener(this);



        int flip_images[] = {R.drawable.omanba, R.drawable.second, R.drawable.third};
        String flipper_texts[] = {context.getResources().getString(R.string.first_slide),
                context.getResources().getString(R.string.second_slide), context.getResources().getString(R.string.third_slide)};

        for (int a = 0; a < flip_images.length; a++) {
            flipper(flip_images[a], flipper_texts[a]);
        }


        locationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LocationActivity.class);
                startActivity(intent);
            }
        });

        Log.w("", "Service running : " + MonitorUtils.isServiceRunning(HomeActivity.class, context));

        Intent intent = new Intent(context, CapturedDataService.class);
        startService(intent);

        if (servicesOk()) {
            init();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            switch (item.getItemId()) {
                case R.id.cart:

                    Intent intent = new Intent(context, SavedItemsActivity.class);
                    startActivity(intent);

                    break;
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_activity_menu, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageMiner:
                intent = new Intent(context, FullCameraActivity.class);
                startActivity(intent);
                break;
            case R.id.imageUser:
                intent = new Intent(context, FullCameraActivity.class);
                startActivity(intent);
                break;
            case R.id.policeService:
                intent=new Intent(context,FullPreviewActivity.class);
                intent.putExtra("serviceType","police");
                startActivity(intent);
                break;

            case R.id.fireService:
                intent=new Intent(context,FullPreviewActivity.class);
                intent.putExtra("serviceType","fire");
                startActivity(intent);
                break;

            case R.id.waterCompany:
                intent=new Intent(context,FullPreviewActivity.class);
                intent.putExtra("serviceType","water");
                startActivity(intent);
                break;

            case R.id.electricityCompany:
                intent=new Intent(context,FullPreviewActivity.class);
                intent.putExtra("serviceType","electricity");
                startActivity(intent);
                break;
        }
    }

    public void flipper(int image, String text) {


        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.setPadding(20, 10, 20, 10);
        ImageView imageView = new ImageView(context);
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextColor(Color.parseColor("#800000"));
        textView.setTypeface(Typeface.SERIF,Typeface.ITALIC);
        textView.setTextSize(10f);
        textView.setPadding(50, 0, 50, 0);
        imageView.setBackgroundResource(image);

        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 220));

        linearLayout.addView(imageView);
        linearLayout.addView(textView);
        viewPager.addView(linearLayout);
        viewPager.setFlipInterval(4000);
        viewPager.setAutoStart(true);

        viewPager.setInAnimation(context, android.R.anim.slide_in_left);
        viewPager.setOutAnimation(context, android.R.anim.slide_out_right);

    }

    public void init() {

    }

    public boolean servicesOk() {
        int available = GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable
                        (HomeActivity.this);
        if (available == ConnectionResult.SUCCESS) {
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Dialog dialog = GoogleApiAvailability.getInstance().
                    getErrorDialog(HomeActivity.this, available,
                            ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(HomeActivity.this,
                    "Unable to make map request", Toast.LENGTH_LONG).show();

        }
        return false;
    }


}