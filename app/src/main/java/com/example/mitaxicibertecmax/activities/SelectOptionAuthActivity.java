package com.example.mitaxicibertecmax.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mitaxicibertecmax.R;
import com.example.mitaxicibertecmax.activities.client.RegisterActivity;
import com.example.mitaxicibertecmax.activities.driver.RegisterDriverActivity;

public class SelectOptionAuthActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnIniciar, btnRegisterAccount;
    private Toolbar toolbar;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Seleccione opcion");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnIniciar = findViewById(R.id.btnGoToLogin);
        btnRegisterAccount = findViewById(R.id.btnGoToRegister);
        btnIniciar.setOnClickListener(this);
        btnRegisterAccount.setOnClickListener(this);

        sharedPreferences = getApplicationContext().getSharedPreferences("typeuser",MODE_PRIVATE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId() ){
            case R.id.btnGoToLogin:
                goToLogin();
                break;
            case R.id.btnGoToRegister:
                goToRegister();
                break;
        }
            
    }

    private void goToLogin() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    private void goToRegister() {
        String typeUser = "";
        typeUser = sharedPreferences.getString("user","");
        Intent intent;
        if( typeUser.equalsIgnoreCase("client")){
            intent = new Intent(this, RegisterActivity.class);
        }else{
            intent = new Intent(this, RegisterDriverActivity.class);
        }
        startActivity(intent);
    }

}
