package com.example.mitaxicibertecmax.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mitaxicibertecmax.R;
import com.example.mitaxicibertecmax.activities.client.MapClientActivity;
import com.example.mitaxicibertecmax.activities.client.MapClientFragment;
import com.example.mitaxicibertecmax.activities.client.MapDriverActivity;
import com.google.firebase.auth.FirebaseAuth;

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

    private Button btnIAmDriver, btnIAmClient;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnIAmDriver = findViewById(R.id.btnIAmDriver);
        btnIAmClient= findViewById(R.id.btnIAmClient);
        btnIAmClient.setOnClickListener(this);
        btnIAmDriver.setOnClickListener(this);

        sharedPreferences = getApplicationContext().getSharedPreferences("typeuser", MODE_PRIVATE);

    }

    @Override
    public void onClick(View v) {
        final SharedPreferences.Editor editorShare = sharedPreferences.edit();
        switch (v.getId()){
            case R.id.btnIAmDriver:
                editorShare.putString("user","driver");
                editorShare.apply();
                goToSelectAuth();
                break;
            case R.id.btnIAmClient:
                    editorShare.putString("user","client");
                    editorShare.apply();
                    goToSelectAuth();
                break;

        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser() != null ){
            String typeUser = "";
            typeUser = sharedPreferences.getString("user","");
            Intent intent;
            if( typeUser.equalsIgnoreCase("client")){
                intent = new Intent( HomeActivity.this, MapClientActivity.class);
            }else{
                intent = new Intent(HomeActivity.this, MapDriverActivity.class);
            }
            startActivity(intent);
        }


    }

    private void goToSelectAuth(){
        Intent intentSelect = new Intent( this, SelectOptionAuthActivity.class );
        startActivity(intentSelect);
    }



}
