package com.example.hp.gall8;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import android.Manifest;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Permission;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import at.grabner.circleprogress.CircleProgressView;

import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class guardian_home extends Fragment {

    double stresslevel;
    Button btn;
    DatabaseReference mDatabase;
    SessionManagement session;
    String email, emailUser;
    CircleProgressView mCircleView;
    FirebaseOptions secondaryAppConfig;
    FirebaseDatabase secondaryDatabase;
    FirebaseApp app;
    public guardian_home() {
        // Required empty public constructor
    }


    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_guardian_home, container, false);
        //btn = (Button) mView.findViewById(R.id.btnDialog);
        session = new SessionManagement(getContext());
        mDatabase = FirebaseDatabase.getInstance().getReference("data");
        HashMap<String, String> user = session.getUserDetails();

        email = user.get(SessionManagement.KEY_EMAIL);

        secondaryAppConfig = new FirebaseOptions.Builder().setApplicationId("1:686755681032:android:1834a810397fd8dd").setApiKey("AIzaSyCa2ZuymenMZtGuUUyM812t_pewjr9l_iE ")
                .setDatabaseUrl("https://stress-c2014.firebaseio.com/") // Required for RTDB.
                .build();

        app = FirebaseApp.initializeApp(getContext(), secondaryAppConfig, UUID.randomUUID().toString());


        secondaryDatabase = FirebaseDatabase.getInstance(app);

        mCircleView = (CircleProgressView) mView.findViewById(R.id.cpv);

        mCircleView.spin(); // start spinning

        readData2(new psych_home.MyCallback1() {

            @Override
            public void onCallback(String value, String value1, String value2, String value3, String value4, String value5) {
                double gsr = 0.0, skin = 0.0, bpm = 0.0;
                DecimalFormat numberFormat = new DecimalFormat("##.000000");
                double holdgsr = Float.parseFloat(value1);
                //stresslevel = (Double.parseDouble(numberFormat.format(gsr)) * 0.25f) + (Float.parseFloat(value1) * 0.25f) + (Float.parseFloat(value1) * 0.25f) + (Float.parseFloat(value1) * 0.25f);

                int age = 15;


                //GET BPM
                if (age >= 6 || age <= 15){
                    if (Integer.parseInt(value) >= 70 && Integer.parseInt(value) <= 100){
                        bpm = 10.00;
                    } else if (Integer.parseInt(value) >= 101 && Integer.parseInt(value) <= 110) {
                        bpm = 14.28;
                    } else if (Integer.parseInt(value) >= 111 && Integer.parseInt(value) <= 120) {
                        bpm = 28.56;
                    } else if (Integer.parseInt(value) >= 121 && Integer.parseInt(value) <= 130) {
                        bpm = 42.84;
                    } else if (Integer.parseInt(value) >= 131 && Integer.parseInt(value) <= 140) {
                        bpm = 57.12;
                    } else if (Integer.parseInt(value) >= 141 && Integer.parseInt(value) <= 150) {
                        bpm = 71.4;
                    } else if (Integer.parseInt(value) >= 151 && Integer.parseInt(value) <= 160) {
                        bpm = 85.68;
                    } else if (Integer.parseInt(value) >= 161 && Integer.parseInt(value) <= 170) {
                        bpm = 100;
                    } else {
                        Toast.makeText(getContext(), "BPM is below or above the minimum or the maximum", Toast.LENGTH_SHORT).show();
                    }
                } else if (age > 16) {

                    if (Integer.parseInt(value) >= 60 || Integer.parseInt(value) <= 100){
                        bpm = 10.00;
                    } else if (Integer.parseInt(value) >= 101 && Integer.parseInt(value) <= 110) {
                        bpm = 14.28;
                    } else if (Integer.parseInt(value) >= 111 && Integer.parseInt(value) <= 120) {
                        bpm = 28.56;
                    } else if (Integer.parseInt(value) >= 121 && Integer.parseInt(value) <= 130) {
                        bpm = 42.84;
                    } else if (Integer.parseInt(value) >= 131 && Integer.parseInt(value) <= 140) {
                        bpm = 57.12;
                    } else if (Integer.parseInt(value) >= 141 && Integer.parseInt(value) <= 150) {
                        bpm = 71.4;
                    } else if (Integer.parseInt(value) >= 151 && Integer.parseInt(value) <= 160) {
                        bpm = 85.68;
                    } else if (Integer.parseInt(value) >= 161 && Integer.parseInt(value) <= 170) {
                        bpm = 100;
                    } else {
                        Toast.makeText(getContext(), "BPM is below or above the minimum or the maximum", Toast.LENGTH_SHORT).show();
                    }
                }
                // GET GSR
                if (holdgsr >= 221 && holdgsr <= 512 ) {
                    gsr = 10.00;
                } else  if (holdgsr >= 200 && holdgsr <= 220){
                    gsr = 14.28;
                } else  if (holdgsr >= 180 && holdgsr <= 199){
                    gsr = 28.56;
                } else  if (holdgsr >= 160 && holdgsr <= 179){
                    gsr = 42.84;
                } else  if (holdgsr >= 140 && holdgsr <= 159){
                    gsr = 57.12;
                } else  if (holdgsr >= 120 && holdgsr <= 139){
                    gsr = 71.4;
                } else  if (holdgsr >= 100 && holdgsr <= 119){
                    gsr = 85.68;
                } else  if (holdgsr >= 1 && holdgsr <= 99){
                    gsr = 100;
                }
                //GET SKIN TEMP
                if (Float.parseFloat(value2) >= 29.0 && Float.parseFloat(value2) <= 36.5){
                    skin = 50.0;
                } else if (Float.parseFloat(value2) >= 36.6 & Float.parseFloat(value2) <= 39.0) {
                    skin = 100.0;
                }

                stresslevel = (bpm * 0.33f) + (gsr * 0.33f) + (skin * 0.33f);
                Toast.makeText(getContext(), String.valueOf(bpm), Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), String.valueOf(gsr), Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), String.valueOf(skin), Toast.LENGTH_SHORT).show();

                mCircleView.stopSpinning(); // stops spinning. Spinner gets shorter until it disappears.
                mCircleView.setValueAnimated((float) stresslevel); // stops spinning. Spinner spins until on top. Then fills to set value.
            }
        });
        readData1(new psych_home.MyCallback() {
            @Override
            public void onCallback(String value) {
                emailUser = value;
            }
        });








        return mView;
    }




    public void readData1(final psych_home.MyCallback myCallback){
        DatabaseReference mDatabase1 =  FirebaseDatabase.getInstance().getReference("path");
        mDatabase1.child(email.replaceAll("[-+.^:,@]", "")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                String firstValue = (String) map.get("psychUser");

                myCallback.onCallback(firstValue);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void readData2(final psych_home.MyCallback1 myCallback1) {
        session = new SessionManagement(getContext());
        HashMap<String, String> user = session.getUserDetails();

        email = user.get(SessionManagement.KEY_EMAIL);
        final DateFormat df = new SimpleDateFormat("dd/MM/yy");
        final Date date = new Date();


        secondaryDatabase.getReference("Pasay").orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                    String firstValue = (String) map.get("BPM");
                    String secondValue = (String) map.get("GSR");
                    String thirdValue = (String) map.get("Temperature");
                    String fourthValue = (String) map.get("X-Accelerometer");
                    String fifthValue = (String) map.get("Y-Accelerometer");
                    String sixthValue = (String) map.get("Z-Accelerometer");


                    myCallback1.onCallback(firstValue, secondValue, thirdValue, fourthValue, fifthValue, sixthValue);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }



    public interface MyCallback {
        void onCallback(String value);
    }

    public interface MyCallback1 {
        void onCallback(String value, String value1, String value2, String value3, String value4, String value5 );
    }


}
