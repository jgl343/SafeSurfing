package com.pepe.app.safesurfing;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.util.concurrent.ListenableFuture;

public  class UploadPositionWorker extends ListenableWorker {
    private FusedLocationProviderClient fusedLocationClient;

    private Context context;
    private WorkerParameters workerParams;

    static final String UNIQUE_WORK_NAME = "LocationWorker";
    static final String KEY_NEW_LOCATION = "new_location";
    private static final String TAG = "LocationWorker";

    private Context mContext;
    private String latitud="";
    private String longitud="";
    private String altitud="";


    public UploadPositionWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context=context;
        this.workerParams=workerParams;
    }

    @NonNull
    @Override
    public ListenableFuture<Result> startWork() {


      /* mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                LocationUtils.getInstance(getApplicationContext()).removeLocationUpdates(this);
                Location location = locationResult.getLastLocation();
                Log.d(TAG, "Work " + getId() + " returned: " + location);
                // Rescheduling work
                OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(LocationWorker.class).setInitialDelay(10, TimeUnit.SECONDS).build();
                WorkManager.getInstance().enqueueUniqueWork(LocationWorker.UNIQUE_WORK_NAME, ExistingWorkPolicy.KEEP, request);
                Log.d(TAG, "Rescheduling work. New ID: " + request.getId());

                // Always set the result as the last operation
                mFuture.set(Result.success(Utils.getOutputData(location)));
            }
        };*/
      try {
          fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.context);
          fusedLocationClient.getLastLocation()
                  .addOnSuccessListener(new OnSuccessListener<Location>() {
                      @Override
                      public void onSuccess(Location location) {
                          // Got last known location. In some rare situations this can be null.
                          if (location != null) {
                              // Logic to handle location object
                              Weather.getInstance().setLatitud(location.getLatitude());
                              Weather.getInstance().setLatitud(location.getLongitude());
                              Weather.getInstance().setLatitud(location.getAltitude());
                              Weather.getInstance().setSpeed(location.getSpeedAccuracyMetersPerSecond());

                              latitud = Double.toString(location.getLatitude());
                              longitud = Double.toString(location.getLongitude());
                              altitud = Double.toString(location.getAltitude());
                              Log.d("location", "PEPITO1: latitud: " + latitud + " - longitud: " + longitud + " - altitud: " + altitud);
                          }

                      }
                  });


          return (ListenableFuture<Result>) Result.success();
      }catch (Exception e) {
          Log.d("location", "error en startWork");
          return null;
      }
    }



    @NonNull
    public Result doWork() {

        return Result.success();
    }
}
