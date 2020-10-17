package com.example.mitaxicibertecmax.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mitaxicibertecmax.R;
import com.example.mitaxicibertecmax.activities.client.MapClientActivity;
import com.example.mitaxicibertecmax.activities.client.MapClientFragment;
import com.example.mitaxicibertecmax.activities.client.MapDriverActivity;
import com.example.mitaxicibertecmax.activities.client.RegisterActivity;
import com.example.mitaxicibertecmax.activities.driver.RegisterDriverActivity;
import com.example.mitaxicibertecmax.include.MyToolbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText edtxtEmail, edtxtPassword;
    private Button btnLogin;
    FirebaseAuth fireAuth;
    DatabaseReference databaseReference;
    AlertDialog alertDialog;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MyToolbar.showToolbar(LoginActivity.this,"Login",true);

        edtxtEmail = findViewById(R.id.edtxtEmail);
        edtxtPassword = findViewById(R.id.edtxtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        fireAuth = FirebaseAuth.getInstance();
        //databaseReference = FirebaseDatabase.getInstance().getReference();

        sharedPreferences = getApplicationContext().getSharedPreferences("typeuser",MODE_PRIVATE);


        alertDialog = new SpotsDialog.Builder().setContext( LoginActivity.this )
            .setMessage("Espere un momento").build()
            ;

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnLogin:
                    login();
                break;
        }
    }

    private void login(){
        String email, password;
        email = edtxtEmail.getText().toString();
        password = edtxtPassword.getText().toString();
        Toast.makeText(LoginActivity.this, "Login.", Toast.LENGTH_SHORT).show();

        if(!email.isEmpty() && !password.isEmpty()){
            if(password.length() >= 6){
                alertDialog.show();
                fireAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if( task.isSuccessful() ){
                            Toast.makeText(LoginActivity.this, "Ingresaste.", Toast.LENGTH_SHORT).show();

                            String typeUser = "";
                            typeUser = sharedPreferences.getString("user","");
                            Intent intent;
                            if( typeUser.equalsIgnoreCase("client")){
                                Fragment fragment = new Fragment( R.layout.fragment_map_client);
                                //startActivityFromFragment(fragment,intent,15);
                                intent = new Intent(LoginActivity.this, MapClientActivity.class);
                                startActivity(intent);
                            }else{
                                intent = new Intent(LoginActivity.this, MapDriverActivity.class);
                                startActivity(intent);
                            }


                        }
                        else{
                            Toast.makeText(LoginActivity.this, "El email o password son incorrectos.", Toast.LENGTH_SHORT).show();
                        }
                        alertDialog.dismiss();
                    }
                });

            }
            else{
                Toast.makeText(this, "La clave debe tener más de 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Deben completar os datos", Toast.LENGTH_SHORT).show();
        }


    }

}