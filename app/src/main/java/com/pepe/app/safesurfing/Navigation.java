package com.pepe.app.safesurfing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkerParameters;

import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class Navigation extends AppCompatActivity {

    private GoogleApiClient client;
    private Location mLastLocation;
    private String latitud="";
    private String longitud="";
    private String altitud="";
    private FusedLocationProviderClient fusedLocationClient;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(UploadPositionWorker.class).setInitialDelay(10, TimeUnit.SECONDS).build();
        WorkManager.getInstance(this.getApplication()).enqueueUniqueWork(UploadPositionWorker.UNIQUE_WORK_NAME, ExistingWorkPolicy.KEEP, request);
        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.screenBrightness = 0;
        getWindow().setAttributes(params);

        DatabaseReference mDatabaseReference = mDatabase.getReference();
        mDatabaseReference = mDatabase.getReference().child("navegante");
        mDatabaseReference.setValue(Weather.getInstance());

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.


    }

    @Override
    protected void onStop() {
        super.onStop();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = -1;
        getWindow().setAttributes(params);
    }
}
