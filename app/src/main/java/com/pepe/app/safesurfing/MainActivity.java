package com.pepe.app.safesurfing;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    @VisibleForTesting
    public ProgressDialog mProgressDialog;
    private static final String TAG = "MainActivity";
    // [END declare_auth]

    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private TextView mDirWindTextView;
    private TextView mCiudadTextView;
    private TextView mLongitudTextView;
    private TextView mLatitudTextView;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private static FirebaseAuth mAuth;
    private static FirebaseDatabase database;
    private FirebaseUser currentUser = null;
    private static final int RC_SIGN_IN = 9001;
    private static boolean LOG_IN = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        // GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        //        .requestEmail()
        //       .build();
        setContentView(R.layout.activity_main);


        loadActivity();


        database = FirebaseDatabase.getInstance();


        currentUser = mAuth.getCurrentUser();
        if (mAuth.getCurrentUser() == null) {
            singInOut(currentUser);
            currentUser = mAuth.getCurrentUser();

            updateUI(currentUser);
            Log.d(TAG, "PEPITO1: isNull" + currentUser);
        } else {
            Log.v("mAuth", mAuth.getCurrentUser().getEmail());
            Toast.makeText(this, mAuth.getCurrentUser().getEmail(),
                    Toast.LENGTH_SHORT).show();
            currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);
            Log.d(TAG, "PEPITO2: isNull" + currentUser);

        }


    }

    private void loadActivity() {

        // Views
        mStatusTextView = (TextView) findViewById(R.id.status);
        mDetailTextView = (TextView) findViewById(R.id.detail);
        mDirWindTextView = (TextView) findViewById(R.id.dirWind);
        mCiudadTextView= (TextView) findViewById(R.id.ciudad);
        mLongitudTextView= (TextView) findViewById(R.id.longitud);
        mLatitudTextView= (TextView) findViewById(R.id.latitud);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //sing in

        Log.d(TAG, "PEPITO1: ONCREATE" + currentUser);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    MenuItem menu = (MenuItem) findViewById(R.id.action_settings);
                    if (menu != null)
                        menu.setTitle(R.string.action_settings);
                    updateUI(user);
                    LOG_IN = true;
                    DatabaseReference myRef = database.getReference("surfista");
                    myRef.setValue(user.getDisplayName());
                    Log.d(TAG, "PEPITO onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    MenuItem menu = (MenuItem) findViewById(R.id.action_settings);
                    if (menu != null)
                        menu.setTitle(R.string.action_settings_in);
                    LOG_IN = false;

                    Log.d(TAG, "PEPITO onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Log.d(TAG, "PEPITO3: isNull" + currentUser);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if (item.getTitle().equals(getString(R.string.action_settings))) {
                item.setTitle(R.string.action_settings_in);
                singInOut(currentUser);
            } else {
                item.setTitle(R.string.action_settings);
                singInOut(currentUser);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance(FirebaseApp.getInstance());
        currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        Log.d(TAG, "PEPITO4: onSTART" + currentUser);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthListener);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        Log.d(TAG, "PEPITO1: OnRESUMEN" + currentUser);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        //loadActivity();

        // mAuth = FirebaseAuth.getInstance(FirebaseApp.getInstance());

        // currentUser = mAuth.getCurrentUser();
        // updateUI(currentUser);

        Log.d(TAG, "PEPITO1: onRESTARTa" + currentUser);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(this, WeatherActivity.class);

            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            try {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("tel:" + "112"));


                if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                    if(MY_PERMISSIONS_REQUEST_READ_CONTACTS!=0){
                        startActivity(intent);
                    }
                }else {
                    startActivity(intent);
                }
            }catch (Exception e){

            }
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void abrirNavegacion(View view){
        Intent intent = new Intent(this, Navigation.class);

        if(LOG_IN) {
            startActivityForResult(intent, RC_SIGN_IN);
        }else {
            Toast.makeText(this, "Debe loguearse antes de acceder a la navegación",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void singInOut(FirebaseUser user){
        Intent intent = new Intent(this, GoogleSignInActivity.class);
        Log.d(TAG, "PEPITO1: lanza intent google sign "+currentUser);
        intent.putExtra("USER", user!=null?user.getEmail():"");
        //checkPlayServices();
        startActivity(intent);
    }
    public void abrirMonitor(View view){
        Intent intent = new Intent(this, Monitorizacion.class);
        //checkPlayServices();
        if(LOG_IN)
        startActivity(intent);
        else
            Toast.makeText(this, "Debe loguearse antes de acceder al Monitor",
                    Toast.LENGTH_SHORT).show();
    }
    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
           //mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, Weather.getInstance().getWind()));

            mCiudadTextView.setText(getString(R.string.label_ciudad, Weather.getInstance().getCiudad()));
            mDirWindTextView.setText(getString(R.string.label_dirwind, Weather.getInstance().getWindDirection()));
            mLatitudTextView.setText(getString(R.string.label_latitud, Weather.getInstance().getLatitud()));
            mLongitudTextView.setText(getString(R.string.label_longitud, Weather.getInstance().getLatitud()));


        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);


        }
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
