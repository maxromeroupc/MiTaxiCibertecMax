package com.example.mitaxicibertecmax.activities.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.mitaxicibertecmax.R;
import com.example.mitaxicibertecmax.include.MyToolbar;
import com.example.mitaxicibertecmax.view.AddressAdapter;

import java.util.List;
import java.util.Locale;

public class SearchAddressActivity extends AppCompatActivity {

    private static final String TAG = "SearchAddressActivity";

    private RecyclerView recySearch;
    private EditText edtxtAddress;
    private ImageButton imgBtnSearch;
    private ProgressBar progressBar;
    private AddressAdapter addressAdapter = new AddressAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_address);

        MyToolbar.showToolbar(SearchAddressActivity.this,"Search",true);
        recySearch = findViewById(R.id.recySearch);
        imgBtnSearch = findViewById(R.id.imgBtnSearch);
        progressBar = findViewById(R.id.progressBar);
        edtxtAddress = findViewById(R.id.edtxtAddress);

        //searchAddress("Cibertec miraflores");

        recySearch.setLayoutManager(new LinearLayoutManager(this));
        recySearch.setHasFixedSize(true);
        recySearch.setAdapter(addressAdapter);

        imgBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchAddress(edtxtAddress.getText().toString());
            }
        });
    }

    private void searchAddress(final String address){



        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Geocoder geocoder = new Geocoder(SearchAddressActivity.this,new Locale("es","PE"));
                    final List<Address>  addressList = geocoder.getFromLocationName(address, 5);
                    Log.d(TAG,"searchAddress :" + address);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.VISIBLE);
                            addressAdapter.setAddressList(addressList);
                        }
                    });
                }catch(Exception ex){
                    ex.printStackTrace();
                }finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        }).start();

    }

}