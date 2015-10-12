package com.goldengateway.apps.mijnlocaties.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.goldengateway.apps.mijnlocaties.R;
import com.goldengateway.apps.mijnlocaties.utils.DbHelper;

public class SplashActivity extends Activity {

    private DbHelper mDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mDbHelper = new DbHelper(getApplicationContext());
        if (mDbHelper.isTableLocatieSEmpty()) {
            Toast.makeText(getApplicationContext(), "Tabel locaties is nog leeg", Toast.LENGTH_LONG).show();
        }

        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        SplashActivity.this.finish();
    }

}
