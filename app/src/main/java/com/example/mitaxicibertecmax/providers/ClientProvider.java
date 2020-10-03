package com.example.mitaxicibertecmax.providers;

import com.example.mitaxicibertecmax.model.Client;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ClientProvider {

    DatabaseReference mDatabase;

    public ClientProvider(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Clients");

    }

    public Task<Void> create(Client client){
        Map<String, Object> mapClient = new HashMap<>();
        mapClient.put("name",client.getName());
        mapClient.put("email",client.getEmail());
        return mDatabase.child(client.getId()).setValue(mapClient);
    }



}
