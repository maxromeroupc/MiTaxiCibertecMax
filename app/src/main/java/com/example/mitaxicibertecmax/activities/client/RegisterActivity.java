package com.example.mitaxicibertecmax.activities.client;

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
import com.example.mitaxicibertecmax.activities.driver.RegisterDriverActivity;
import com.example.mitaxicibertecmax.include.MyToolbar;
import com.example.mitaxicibertecmax.model.Client;
import com.example.mitaxicibertecmax.providers.AuthProvider;
import com.example.mitaxicibertecmax.providers.ClientProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;

    private Button btnRegister;
    private TextInputEditText edtxtName,edtxtEmail, edtxtPassword;
    private AlertDialog alertDialog;

    private AuthProvider mAuthProvider;
    private ClientProvider mClientProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtxtName = findViewById(R.id.edtxtName);
        edtxtEmail = findViewById(R.id.edtxtEmail);
        edtxtPassword = findViewById(R.id.edtxtPassword);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        MyToolbar.showToolbar(RegisterActivity.this,"Registrar Usuario",true);

        sharedPreferences = getApplicationContext().getSharedPreferences("typeuser",MODE_PRIVATE);

        mAuthProvider = new AuthProvider();
        mClientProvider = new ClientProvider();

        //Toast.makeText(this, "Usuario:" + selectedUser, Toast.LENGTH_SHORT).show();
        alertDialog = new SpotsDialog.Builder().setContext( RegisterActivity.this )
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
        final String name, email, password;
        name = edtxtName.getText().toString();
        email = edtxtEmail.getText().toString();
        password = edtxtPassword.getText().toString();

        if( !name.isEmpty() && !email.isEmpty() && !password.isEmpty() ){
            if( password.length() >= 6 ){
                alertDialog.show();
                register(name,email,password);
            }else{
                Toast.makeText(this, "clave mayor a 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "no vacio", Toast.LENGTH_SHORT).show();
        }
    }

    private void register(final String name, final String email, final String password) {
        mAuthProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if( task.isSuccessful()){
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Client client = new Client();
                    client.setId(id);
                    client.setName(name);
                    client.setEmail(email);
                    create(client);
                }else{
                    Toast.makeText(getBaseContext(), "No completado", Toast.LENGTH_SHORT).show();
                }
                alertDialog.dismiss();
            }
        });
    }

    private void create( Client client){
        mClientProvider.create(client).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if( task.isSuccessful()){
                    Toast.makeText(getBaseContext(), "El cliente fue registrado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent( RegisterActivity.this, MapClientFragment.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(getBaseContext(), "No se pudo crear el cliente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
/*
    private void registerUser() {
        final String name, email, password;
        name = edtxtName.getText().toString();
        email = edtxtEmail.getText().toString();
        password = edtxtPassword.getText().toString();

        if( !name.isEmpty() && !email.isEmpty() && !password.isEmpty() ){
            if( password.length() >= 6 ){
                alertDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if( task.isSuccessful()){
                            String id = firebaseAuth.getCurrentUser().getUid();
                            saveUser(id,name, email, password);
                        }else{
                            Toast.makeText(getBaseContext(), "No completado", Toast.LENGTH_SHORT).show();
                        }
                        alertDialog.dismiss();
                    }
                });
            }else{
                Toast.makeText(this, "clave mayor a 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "no vacio", Toast.LENGTH_SHORT).show();
        }

    }


    private void saveUser( String id, String name, String email, String password){
        String selectedUser = "";
        User oUser = new User();
        oUser.setEmail(email);
        oUser.setName(name);
        oUser.setId(id);

        selectedUser = sharedPreferences.getString("user","");
        if(selectedUser.equals("driver")){
            databaseReference.child("Users").child("Drivers").child(id).setValue(oUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Registro Correcto", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegisterActivity.this, "No se registró.", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }else if( selectedUser.equals("client")){
            databaseReference.child("Users").child("Clients").child(id).setValue(oUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Registro Correcto", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegisterActivity.this, "No se registró.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }


 */
}
