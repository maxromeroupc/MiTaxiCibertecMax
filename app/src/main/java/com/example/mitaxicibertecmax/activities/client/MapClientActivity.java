package com.example.mitaxicibertecmax.activities.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mitaxicibertecmax.R;
import com.example.mitaxicibertecmax.providers.AuthProvider;



public class MapClientActivity extends AppCompatActivity {


    private Button mButtonLogOut;
    private AuthProvider mAuthProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_client);


        //mButtonLogOut = findViewById(R.id.btnLogOut);

        mAuthProvider= new AuthProvider();

/*
        mButtonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuthProvider.logOut();
                Intent intent = new Intent(MapClientActivity.this , HomeActivity.class);

                startActivity(intent);
                finish();
            }
        });


 */
    }
}