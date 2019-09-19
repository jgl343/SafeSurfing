package com.pepe.app.safesurfing;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ExistingWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public  class UploadPositionWorker extends ListenableWorker  {
    private FusedLocationProviderClient fusedLocationClient;

    private Context context;
    private WorkerParameters workerParams;

    static final String UNIQUE_WORK_NAME = "LocationWorker";
    static final String KEY_NEW_LOCATION = "new_location";
    private static final String TAG = "LocationWorker";

    private Context mContext;
    private String latitud="";
    private String longitud="";
    private double altitud = 1.0;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    public UploadPositionWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

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


                              Float currentWindDir = location.getBearing();
                              int windDirInt=0;
                              if(currentWindDir!=null&&!currentWindDir.isNaN()) {
                                  windDirInt = Integer.valueOf(String.valueOf(currentWindDir).substring(0,String.valueOf(currentWindDir).indexOf(".")))%360;
                              }
                              int numDirection = windDirInt/45;
                              String direction ="N";
                              switch(numDirection){
                                  case 0:
                                      direction="O";
                                      break;
                                  case 1:
                                      direction="SO";
                                      break;
                                  case 2:
                                      direction="S";
                                      break;
                                  case 3:
                                      direction="SE";
                                      break;
                                  case 4:
                                      direction="E";
                                      break;
                                  case 5:
                                      direction="NE";
                                      break;
                                  case 6:
                                      direction="N";
                                      break;
                                  case 7:
                                      direction="NO";
                                      break;
                              }
                              Weather.getInstance().setDirection(direction);

                              latitud = Double.toString(location.getLatitude());
                              longitud = Double.toString(location.getLongitude());
                              altitud = location.getAltitude();
                                boolean reencolarTarea = true;
                              if(altitud==0){  //comprobamos que estamos en el agua
                                  double umbralVelocidad= location.getSpeedAccuracyMetersPerSecond()*0.7;
                                  if(Weather.getInstance().getWind()<umbralVelocidad*1.20){ //comprobamos que cumplimos el umbral
                                      if(direction.equals(Weather.getInstance().getWindDirection())){ //comprobamos que no vamos en contra del viento
                                          DatabaseReference mDatabaseReference = mDatabase.getReference();
                                          mDatabaseReference = mDatabase.getReference().child("navegante");
                                          mDatabaseReference.setValue(Weather.getInstance());
                                          reencolarTarea=false;
                                      }
                                  }
                              }
                                if(reencolarTarea){
                                    OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(UploadPositionWorker.class).setInitialDelay(5, TimeUnit.MINUTES).build();
                                    WorkManager.getInstance().enqueueUniqueWork(UploadPositionWorker.UNIQUE_WORK_NAME, ExistingWorkPolicy.KEEP, request);
                                }
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
