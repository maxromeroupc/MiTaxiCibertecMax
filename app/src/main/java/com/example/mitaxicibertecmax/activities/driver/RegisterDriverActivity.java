package com.example.mitaxicibertecmax.activities.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mitaxicibertecmax.R;
import com.example.mitaxicibertecmax.activities.client.MapDriverActivity;
import com.example.mitaxicibertecmax.activities.client.RegisterActivity;
import com.example.mitaxicibertecmax.include.MyToolbar;
import com.example.mitaxicibertecmax.model.Client;
import com.example.mitaxicibertecmax.model.Driver;
import com.example.mitaxicibertecmax.providers.AuthProvider;
import com.example.mitaxicibertecmax.providers.ClientProvider;
import com.example.mitaxicibertecmax.providers.DriverProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;

public class RegisterDriverActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;

    private Button btnRegister;
    private TextInputEditText edtxtName,edtxtEmail, edtxtPassword, edtxtBrand, edtxtPlate;
    private AlertDialog alertDialog;

    private AuthProvider mAuthProvider;
    private DriverProvider mDriverProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_driver);

        edtxtName = findViewById(R.id.edtxtName);
        edtxtEmail = findViewById(R.id.edtxtEmail);
        edtxtPassword = findViewById(R.id.edtxtPassword);
        edtxtBrand = findViewById(R.id.edtxtBrand);
        edtxtPlate = findViewById(R.id.edtxtPlate);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        MyToolbar.showToolbar(RegisterDriverActivity.this,"Registrar Conductor",true);

        sharedPreferences = getApplicationContext().getSharedPreferences("typeuser",MODE_PRIVATE);

        mAuthProvider = new AuthProvider();
        mDriverProvider = new DriverProvider();

        //Toast.makeText(this, "Usuario:" + selectedUser, Toast.LENGTH_SHORT).show();
        alertDialog = new SpotsDialog.Builder().setContext( RegisterDriverActivity.this )
                .setMessage("Espere un momento").build()
        ;
    }

    @Override
    public void onClick(View view) {
        switch( view.getId() ){
            case R.id.btnRegister:
                registerClient();
                break;
        }
    }

    private void registerClient() {
        final String name, email, password, brand, plate;
        name = edtxtName.getText().toString();
        email = edtxtEmail.getText().toString();
        password = edtxtPassword.getText().toString();
        brand = edtxtBrand.getText().toString();
        plate = edtxtPlate.getText().toString();

        if( !name.isEmpty() && !email.isEmpty() && !password.isEmpty() ){
            if( password.length() >= 6 ){
                alertDialog.show();
                register(name,email,password, brand, plate);
            }else{
                Toast.makeText(this, "clave mayor a 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "no vacio", Toast.LENGTH_SHORT).show();
        }
    }

    private void register(final String name, final String email, final String password, final String brand, final String plate) {
        mAuthProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if( task.isSuccessful()){
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Driver driver = new Driver(id,name,email, brand, plate);
                    create(driver);
                }else{
                    Toast.makeText(getBaseContext(), "No completado", Toast.LENGTH_SHORT).show();
                }
                alertDialog.dismiss();
            }
        });
    }

    private void create( Driver driver){
        mDriverProvider.create(driver).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if( task.isSuccessful()){
                    Toast.makeText(getBaseContext(), "El Conductor fue registrado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent( RegisterDriverActivity.this, MapDriverActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(getBaseContext(), "No se pudo crear el conductor", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}