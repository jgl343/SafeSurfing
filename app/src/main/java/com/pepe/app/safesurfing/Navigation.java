package com.pepe.app.safesurfing;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class Navigation extends AppCompatActivity {

    private GoogleApiClient client;
    private Location mLastLocation;
    private String latitud="";
    private String longitud="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        if (client == null) {


        }
    }
}
