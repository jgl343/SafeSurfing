package com.pepe.app.safesurfing;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.LocationListener;

/**
 * Created by 0011445 on 12/09/2016.
 */
public class localizacion extends AppCompatActivity {
    private static localizacion ourInstance = new localizacion();

    public static localizacion getInstance() {
        return ourInstance;
    }



    private void makeUseOfNewLocation(Location location) {
    }


    private localizacion() {
// Acquire a reference to the system Location Manager

        LocationManager locationManager = (LocationManager) super.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                makeUseOfNewLocation(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };
        // Register the listener with the Location Manager to receive location updates
        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (android.location.LocationListener) locationListener);
    }
}
