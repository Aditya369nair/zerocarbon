package com.aditya.zerocarbon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class Driver_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(this.getApplicationContext(),"onCreate called",Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_activity);
        Toolbar toolbar = findViewById(R.id.driver_toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this.getApplicationContext(),"Driver Activity start called",Toast.LENGTH_SHORT).show();
    }



}
