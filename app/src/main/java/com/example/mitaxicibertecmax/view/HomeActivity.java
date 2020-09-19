package com.example.mitaxicibertecmax.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mitaxicibertecmax.R;

/*
Clave para firebase
Variant: debug
Config: debug
Store: C:\Users\SUITE\.android\debug.keystore
Alias: AndroidDebugKey
MD5: A6:35:06:C9:56:40:E2:AA:F5:4E:EA:7F:E4:AE:27:9D
SHA1: B3:0E:2B:B6:5E:E5:C1:D4:99:3A:3A:FD:58:BF:37:FB:A3:39:98:31
SHA-256: 8F:17:28:9F:B9:57:AE:32:B6:CB:CE:00:C8:E1:BF:03:DF:35:2B:69:6C:66:F2:E2:8E:8D:24:CE:5F:E0:23:EE

 */

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
        switch (v.getId()){
            case R.id.btnDriver:
                    goToSelect();
                break;
            case R.id.btnClient:
                    goToSelect();
                break;

        }

    }

    private void goToSelect(){
        Intent intentSelect = new Intent( this, SelectOptionAuthActivity.class );
        startActivityForResult(intentSelect,0);
    }

}
