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

    private CollectionReference riders = FirebaseFirestore.getInstance().collection("riders");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Geocoder g = new Geocoder(this);

        final Button btnSubmit = (Button) findViewById(R.id.driverSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String fromAddress =  ((EditText)findViewById(R.id.fromAddress)).getText().toString();
                String toAddress =  ((EditText)findViewById(R.id.toAddress)).getText().toString();
                List<Address> fromAddresses = null;
                List<Address> toAddresses = null;
                double fromLatitude=0.0;
                double fromLongitude=0.0;
                double toLatitude=0.0;
                double toLongitude=0.0;
                try {
                    fromAddresses = g.getFromLocationName(fromAddress, 1);
                    toAddresses = g.getFromLocationName(toAddress, 1);
                } catch (IOException e) {
                    Toast.makeText(v.getContext(), "Location not found",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } finally {
                    if (fromAddresses.get(0).hasLatitude() && fromAddresses.get(0).hasLongitude()) {
                        fromLatitude = fromAddresses.get(0).getLatitude();
                        fromLongitude = fromAddresses.get(0).getLongitude();
                    }
                    if (toAddresses.get(0).hasLatitude() && toAddresses.get(0).hasLongitude()) {
                        toLatitude = toAddresses.get(0).getLatitude();
                        toLongitude = toAddresses.get(0).getLongitude();
                    }
                }

                Spinner seatSpinner = (Spinner)findViewById(R.id.seatCount);
                String seatString = seatSpinner.getSelectedItem().toString();
                int seatValue = Integer.parseInt(seatString);

                Map<String,Object> ride = new HashMap<>();
                ride.put(FROM_FIELD, new GeoPoint(fromLatitude,fromLongitude));
                ride.put(TO_FIELD, new GeoPoint(toLatitude,toLongitude));
                ride.put(SEAT_FIELD, new Integer(seatValue));

                riders.add(ride)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(v.getContext(), "Your ride has been added",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(v.getContext(), "Failed to add ride",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


        final Button btnClear = findViewById(R.id.driverClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(">>>>>> Aditya : Clear called");
                Toast.makeText(btnClear.getContext(),"Clear called",Toast.LENGTH_SHORT).show();
                EditText fromAddress =  findViewById(R.id.fromAddress);
                EditText toAddress =  findViewById(R.id.toAddress);
                Spinner seatSpinner = findViewById(R.id.seatCount);
                fromAddress.clearComposingText();
                toAddress.clearComposingText();
                seatSpinner.setSelected(false);
            }
        });
    }

}
