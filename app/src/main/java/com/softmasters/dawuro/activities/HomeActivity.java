package com.softmasters.dawuro.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    AdvertsPagerAdapter advertsPagerAdapter;
    Timer advertsTimer;
    int currentPage;
    int timerDelay = 3000;
    int timerPeriod = 5000;


    Context context;

    int[] images = new int[]{
            R.layout.advert1,
            R.layout.advert2,
            R.layout.advert3
    };
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = HomeActivity.this;

        imageUser = (LinearLayout) findViewById(R.id.imageUser);
        imageMiners = (LinearLayout) findViewById(R.id.imageMiner);
        viewPager = (ViewFlipper) findViewById(R.id.advertsPager);
        locationIcon = (LinearLayout) findViewById(R.id.location);
        supportIcon = (LinearLayout) findViewById(R.id.support);

//        advertsPagerAdapter =  new AdvertsPagerAdapter(HomeActivity.this, images);
//        viewPager.setAdapter(advertsPagerAdapter);

        imageUser.setOnClickListener(this);
        imageMiners.setOnClickListener(this);

        advertsTimer = new Timer();
        //  currentPage = viewPager.getCurrentItem();
        int flip_images[] = {R.drawable.first, R.drawable.second, R.drawable.third};

        for (int image : flip_images) {
            flipper(image);
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
        }
    }

    public void flipper(int image) {
        ImageView imageView = new ImageView(context);
        imageView.setBackgroundResource(image);

        viewPager.addView(imageView);
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