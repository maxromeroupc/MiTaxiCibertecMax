package com.example.mitaxicibertecmax;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.mitaxicibertecmax.activities.client.MapClientFragment;

public class MainActivity extends AppCompatActivity {

    private ImageButton imgBtnDrawerMenu;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer);
        imgBtnDrawerMenu = findViewById(R.id.imgBtnDrawerMenu);
        imgBtnDrawerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer( GravityCompat.START );
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 99 && resultCode == RESULT_OK){
            MapClientFragment f = (MapClientFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
            if( f != null){
                f.requestLocationUpdates();
            }
        }
    }
}
