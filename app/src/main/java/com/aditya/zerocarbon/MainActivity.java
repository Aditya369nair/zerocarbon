package com.aditya.zerocarbon;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    public static final String FROM_FIELD = "from";
    public static final String TO_FIELD = "to";
    public static final String SEAT_FIELD = "seats";
    public static final String DATE_FIELD = "date";

    private CollectionReference rides = FirebaseFirestore.getInstance().collection("rides");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnSubmit = findViewById(R.id.submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup usertype = findViewById(R.id.usertyperadio);
                int selectedId = usertype.getCheckedRadioButtonId();
                if (selectedId == R.id.driver_radio) {
                    setContentView(R.layout.activity_driver_activity);


                    final Button btnDriverSubmit = (Button) findViewById(R.id.driverSubmit);
                    btnDriverSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            String fromAddress =  ((EditText)findViewById(R.id.fromAddress)).getText().toString();
                            String toAddress =  ((EditText)findViewById(R.id.toAddress)).getText().toString();


                            Spinner seatSpinner = (Spinner)findViewById(R.id.seatCount);
                            String seatString = seatSpinner.getSelectedItem().toString();
                            int seatValue = Integer.parseInt(seatString);

                            Map<String,Object> ride = new HashMap<>();
                            ride.put(FROM_FIELD, fromAddress);
                            ride.put(TO_FIELD, toAddress);
                            ride.put(SEAT_FIELD, seatValue);

                            rides.add(ride)
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

                    final Button btnDriverClear = findViewById(R.id.driverClear);
                    btnDriverClear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText fromAddress =  findViewById(R.id.fromAddress);
                            EditText toAddress =  findViewById(R.id.toAddress);
                            Spinner seatSpinner = findViewById(R.id.seatCount);
                            fromAddress.setText("");
                            toAddress.setText("");
                            seatSpinner.setSelected(false);
                        }
                    });
                } else if (selectedId == R.id.rider_radio) {
                    setContentView(R.layout.activity_ride_list);
                    final ListView rideListView = findViewById(R.id.ride_list);

                    rides.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                List<String> myRideList = new ArrayList<>();
                                for(QueryDocumentSnapshot document : task.getResult()) {
                                    String s = document.get("seats").toString();
                                    String f = document.get("from").toString();
                                    String t = document.get("to").toString();

                                    myRideList.add("Ride from '"+f+"' to '"+t+"' with "+s+" seats");
                                }

                                ArrayAdapter arrayAdapter = new ArrayAdapter(rideListView.getContext(),android.R.layout.simple_list_item_1,myRideList);
                                rideListView.setAdapter(arrayAdapter);

                            } else {
                                Toast.makeText(rideListView.getContext(), "Failed to get any rides",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

        Button btnClear = findViewById(R.id.clear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                RadioGroup usertype = (RadioGroup)findViewById(R.id.usertyperadio);
                usertype.clearCheck();
            }
        });


  }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        System.out.println("Aditya : Being called at onMenu");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("Aditya : Being called at onItemSelected");

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
