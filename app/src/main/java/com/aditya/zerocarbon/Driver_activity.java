package com.aditya.zerocarbon;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Driver_activity extends AppCompatActivity {

    public static final String FROM_FIELD = "from";
    public static final String TO_FIELD = "to";
    public static final String SEAT_FIELD = "seats";
    public static final String DATE_FIELD = "date";

    private CollectionReference rides = FirebaseFirestore.getInstance().collection("rides");

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
