package com.example.mitaxicibertecmax.include;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mitaxicibertecmax.R;

public class MyToolbar {

    public static void showToolbar(AppCompatActivity activity, String title,boolean upButton){
        Toolbar toolbar;
        toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(title);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

}
