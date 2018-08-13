package com.example.hp.gall8;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.txusballesteros.SnakeView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import at.grabner.circleprogress.CircleProgressView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    CircleProgressView mCircleView;
    String email;
    double stresslevel;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private SnakeView snakeView;
    private Button button, button1;
    FirebaseApp app;
    SessionManagement session;
    FirebaseOptions secondaryAppConfig;
    FirebaseDatabase secondaryDatabase;
    TextView welc, stat;
    String name;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);



        secondaryAppConfig = new FirebaseOptions.Builder().setApplicationId("1:686755681032:android:1834a810397fd8dd").setApiKey("AIzaSyCa2ZuymenMZtGuUUyM812t_pewjr9l_iE ")
                .setDatabaseUrl("https://stress-c2014.firebaseio.com/") // Required for RTDB.
                .build();

        app = FirebaseApp.initializeApp(getContext(), secondaryAppConfig, UUID.randomUUID().toString());


        secondaryDatabase = FirebaseDatabase.getInstance(app);

        snakeView = (SnakeView) view.findViewById(R.id.snake);
        welc = (TextView) view.findViewById(R.id.welcome);
        stat = (TextView) view.findViewById(R.id.status);
        session = new SessionManagement(getContext());
        HashMap<String, String> user = session.getUserDetails();

        name = user.get(SessionManagement.KEY_LNAME);

        String email = user.get(SessionManagement.KEY_EMAIL);

        String userlevel = user.get(SessionManagement.KEY_USERLEVEL);

        welc.setText("Welcome " + name + "!");


        snakeView.setMinValue(0);
        snakeView.setMaxValue(100);
        snakeView.addValue(100);
        snakeView.addValue(10);
        snakeView.addValue(30);
        snakeView.addValue(40);
        snakeView.addValue(80);
        snakeView.addValue(90);
        snakeView.addValue(20);
        snakeView.addValue(60);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("data");
        mCircleView = (CircleProgressView) view.findViewById(R.id.cpv);

        //mCircleView.spin(); // start spinning
        stat.setVisibility(View.INVISIBLE);
        readData1(new HomeFragment.MyCallback() {

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
                       // Toast.makeText(getContext(), "BPM is below or above the minimum or the maximum", Toast.LENGTH_SHORT).show();
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
                        //Toast.makeText(getContext(), "BPM is below or above the minimum or the maximum", Toast.LENGTH_SHORT).show();
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
                } else{
                    skin = 100.0;
                }

                stresslevel = (bpm * 0.33f) + (gsr * 0.33f) + (skin * 0.33f);



                mCircleView.stopSpinning(); // stops spinning. Spinner gets shorter until it disappears.
                mCircleView.setValueAnimated((float) stresslevel); // stops spinning. Spinner spins until on top. Then fills to set value.

                if (stresslevel >= 0 && stresslevel <= 23) {
                    stat.setVisibility(View.VISIBLE);
                    stat.setText("Stress free continue smiling and have a great day!!!");
                } else if (stresslevel >= 24 && stresslevel <= 50) {
                    stat.setVisibility(View.VISIBLE);
                    stat.setText("You're moderately Stressed");
                } else if (stresslevel >= 51 && stresslevel <= 75) {
                    stat.setVisibility(View.VISIBLE);
                    stat.setText("You're critical level of stress");
                } else if (stresslevel >= 76 && stresslevel <= 100) {
                    stat.setVisibility(View.VISIBLE);
                    stat.setText("You're very stress (please look for psychologist immediately)");
                }



            }
        });


        button = (Button) view.findViewById(R.id.button);
        button1 = (Button) view.findViewById(R.id.button1);

        // Capture button clicks
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                getStressLevel menu = new getStressLevel();
                FragmentManager manager3 = getFragmentManager();
                manager3.beginTransaction().replace(R.id.contentLayout,
                        menu,
                        menu.getTag()).commit();
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                getBPM menu = new getBPM();
                FragmentManager manager3 = getFragmentManager();
                manager3.beginTransaction().replace(R.id.contentLayout,
                        menu,
                        menu.getTag()).commit();
            }
        });

        return view;
    }

    public void readData1(final HomeFragment.MyCallback myCallback) {
        session = new SessionManagement(getContext());
        HashMap<String, String> user = session.getUserDetails();

        email = user.get(SessionManagement.KEY_EMAIL);
        final DateFormat df = new SimpleDateFormat("dd/MM/yy");
        final Date date = new Date();


        secondaryDatabase.getReference("Stress").orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {

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


                    myCallback.onCallback(firstValue, secondValue, thirdValue, fourthValue, fifthValue, sixthValue);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    private void inputData() {

        DateFormat df = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
        Date date = new Date();
        Notification data1 = new Notification("Your stress level is high!", df.format(date));

        mDatabase.child(email.replaceAll("[-+.^:,@]", "")).child("notifications").child(UUID.randomUUID().toString()).setValue(data1);


    }
    public void readData3(final MyCallback2 myCallback2){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("path");
        dbRef.child(email.replaceAll("[-+.^:,@]", "")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                String firstValue = (String) map.get("userPsych");
                String secondValue = (String) map.get("userGuardian");

                myCallback2.onCallback(firstValue,secondValue);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public interface MyCallback {
        void onCallback(String value, String value1, String value2, String value3, String value4, String value5 );
    }
    public interface MyCallback2 {
        void onCallback(String value, String value1);
    }

}
