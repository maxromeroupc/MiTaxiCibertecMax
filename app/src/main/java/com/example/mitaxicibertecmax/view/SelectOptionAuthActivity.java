package com.example.mitaxicibertecmax.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mitaxicibertecmax.R;

public class SelectOptionAuthActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnIniciar, btnRegisterAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth);

        btnIniciar = findViewById(R.id.btnIniciar);
        btnRegisterAccount = findViewById(R.id.btnRegisterAccount);
        btnIniciar.setOnClickListener(this);
        btnRegisterAccount.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }
}
