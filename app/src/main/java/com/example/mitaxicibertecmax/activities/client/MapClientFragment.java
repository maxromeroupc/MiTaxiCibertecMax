package com.example.mitaxicibertecmax.activities.client;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mitaxicibertecmax.R;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapClientFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Marker mLocationMarker;

    private  static final int REQUEST_CODE_PERMISSION_LOCATION = 10;

    private static final LocationRequest locationRequest
            = new LocationRequest()
            .setPriority( LocationRequest.PRIORITY_HIGH_ACCURACY )
            .setInterval(3000)
            .setSmallestDisplacement(3)
            ;

    private TextView txtPickUpLocation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map_client, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if(mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

        verifyLocationSettings();

        txtPickUpLocation = view.findViewById(R.id.txtPickUpLocation);
        txtPickUpLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireContext(),SearchAddressActivity.class) );
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        //this.googleMap.setMapType(Display.Mode);
        // Add a marker in Sydney and move the camera
        /*
        LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        requestLocationPermission();
        //requestLastLocation();
        //requestLocationUpdates();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case REQUEST_CODE_PERMISSION_LOCATION:
                if( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    requestLocation();
                }else{
                    Toast.makeText( getContext(), "no se puede mostrar la posicion", Toast.LENGTH_SHORT).show();
                }
                    
                break;
        }
    }

    private void verifyLocationSettings(){
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        SettingsClient client = LocationServices.getSettingsClient(requireContext());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                requestLocationUpdates();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof ResolvableApiException){
                    try {
                        ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                        resolvableApiException.startResolutionForResult(requireActivity(),99);

                    }catch(Exception ex){

                    }
                }
            }
        });
    }

    private void requestLocationPermission(){
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            if( shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) ){
                Log.d("request","requestLocationPermission should");
            }else{
                requestPermissions( new String []{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE_PERMISSION_LOCATION);
            }
        }else{
            requestLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestLocation(){
        //googleMap.setMyLocationEnabled(true);

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(location.getLatitude(), location.getLongitude()),
                        15f)
                );
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void requestLastLocation(){
        //googleMap.setMyLocationEnabled(true);

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(location.getLatitude(), location.getLongitude()),
                        15f)
                );
            }
        });
    }

    @SuppressLint("MissingPermission")
    public void requestLocationUpdates(){
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                new LocationCallback(){
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        //super.onLocationResult(locationResult);
                        Location location = locationResult.getLastLocation();
                        Log.d("Pos:","Lat:: " + location.getLatitude() + " Lon: " + location.getLongitude() );
                        addMyLocationMarker( new LatLng( location.getLatitude(), location.getLongitude() ) );
                    }
                },
                Looper.getMainLooper() );
    }

    private void addMyLocationMarker(LatLng latLng){

        if(mLocationMarker != null){
            mLocationMarker.setPosition(latLng);
        }else{
            mLocationMarker = googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    //.icon(BitmapDescriptorFactory.defaultMarker())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_my_location) )
            );
        }
    }
}