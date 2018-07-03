package com.softmasters.dawuro.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.softmasters.dawuro.R;
import com.softmasters.dawuro.services.CapturedDataService;
import com.softmasters.dawuro.utils.AdvertsPagerAdapter;
import com.softmasters.dawuro.utils.MonitorUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Softmasters on 09-May-17.
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    Intent intent;
    LinearLayout imageUser;
    LinearLayout imageMiners;
    ViewPager viewPager;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = HomeActivity.this;

        imageUser = (LinearLayout) findViewById(R.id.imageUser);
        imageMiners = (LinearLayout) findViewById(R.id.imageMiner);
        viewPager = (ViewPager) findViewById(R.id.advertsPager);

        advertsPagerAdapter =  new AdvertsPagerAdapter(HomeActivity.this, images);
        viewPager.setAdapter(advertsPagerAdapter);

        imageUser.setOnClickListener(this);
        imageMiners.setOnClickListener(this);

        advertsTimer = new Timer();
        currentPage = viewPager.getCurrentItem();
        advertsTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (currentPage == images.length){
                            currentPage = -1;
                        }
                        viewPager.setCurrentItem(currentPage++, true);
                    }
                });
            }
        }, timerDelay, timerPeriod);


        Log.w("","Service running : "+ MonitorUtils.isServiceRunning(HomeActivity.class, context));

        Intent intent = new Intent(context, CapturedDataService.class);
        startService(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.cart:
                Intent intent = new Intent(context, SavedItemsActivity.class);
                startActivity(intent);
                break;

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
        switch (v.getId()){
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
}
