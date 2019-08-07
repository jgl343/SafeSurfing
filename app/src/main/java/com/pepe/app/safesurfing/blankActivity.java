package com.pepe.app.safesurfing;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class blankActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }
}
