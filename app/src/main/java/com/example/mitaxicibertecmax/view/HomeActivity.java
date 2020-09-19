package com.example.mitaxicibertecmax.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.*;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mitaxicibertecmax.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnDriver, btnClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnDriver = findViewById(R.id.btnDriver);
        btnClient= findViewById(R.id.btnClient);
        btnClient.setOnClickListener(this);
        btnDriver.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
