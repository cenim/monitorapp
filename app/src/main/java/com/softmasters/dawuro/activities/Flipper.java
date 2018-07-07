package com.softmasters.dawuro.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.softmasters.dawuro.R;

public class Flipper extends AppCompatActivity {

    ViewFlipper flipper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flipper= (ViewFlipper) findViewById(R.id.advertsPager);


    }

    public void flipperImages(int image){
//        ImageView imageView=new ImageView(Flipper.this);
//        imageView.setBackgroundResource(image);
    }
}
