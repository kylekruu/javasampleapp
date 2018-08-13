package com.example.hp.gall8;


import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.horizontalpicker.HorizontalPicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import at.grabner.circleprogress.CircleProgressView;
import travel.ithaka.android.horizontalpickerlib.PickerLayoutManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class getStressLevel extends Fragment {
    View view;
    TextView txtskin, txtbpm, txtgsr;
    SessionManagement session;
    Button start;
    double gsr;
    CircleProgressView mCircleView;
    ImageButton b1;
    HorizontalPicker hpImage;
    String email;
    double stresslevel;
    FirebaseOptions secondaryAppConfig;
    FirebaseApp app;
    boolean hasBeenInitialized;
    Button btn;
    double bpm = 0, skin = 0;
    int selectIndex;
    HorizontalPicker.PickerItem selected;
    FirebaseDatabase secondaryDatabase;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    public getStressLevel() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_get_stress_level, container, false);
        start = (Button) view.findViewById(R.id.button5);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("data");
        btn = (Button) view.findViewById(R.id.button5);
        secondaryAppConfig = new FirebaseOptions.Builder().setApplicationId("1:686755681032:android:1834a810397fd8dd").setApiKey("AIzaSyCa2ZuymenMZtGuUUyM812t_pewjr9l_iE ")
                .setDatabaseUrl("https://stress-c2014.firebaseio.com/") // Required for RTDB.
                .build();


        txtbpm = (TextView) view.findViewById(R.id.txtBpm);
        txtgsr = (TextView) view.findViewById(R.id.txtGsr);
        txtskin = (TextView) view.findViewById(R.id.txtSkin);
        app = FirebaseApp.initializeApp(getContext(), secondaryAppConfig, UUID.randomUUID().toString());
        secondaryDatabase = FirebaseDatabase.getInstance(app);

        mCircleView = (CircleProgressView) view.findViewById(R.id.circleProgressView);
        b1 = (ImageButton) view.findViewById(R.id.imageButton);
        hpImage = (HorizontalPicker) view.findViewById(R.id.hpImage);

        List<HorizontalPicker.PickerItem> imageItems = new ArrayList<>();
        imageItems.add(new HorizontalPicker.DrawableItem(R.drawable.good));
        imageItems.add(new HorizontalPicker.DrawableItem(R.drawable.happy));
        imageItems.add(new HorizontalPicker.DrawableItem(R.drawable.frustrated));
        imageItems.add(new HorizontalPicker.DrawableItem(R.drawable.sad));
        imageItems.add(new HorizontalPicker.DrawableItem(R.drawable.angry));
        imageItems.add(new HorizontalPicker.DrawableItem(R.drawable.neutral));
        imageItems.add(new HorizontalPicker.DrawableItem(R.drawable.inlove));
        hpImage.setItems(imageItems);


       // inputData();


        b1.setVisibility(View.INVISIBLE);



        HorizontalPicker.OnSelectionChangeListener listener = new HorizontalPicker.OnSelectionChangeListener() {
            @Override
            public void onItemSelect(HorizontalPicker picker, int index) {
                selected = picker.getSelectedItem();
                selectIndex = picker.getSelectedIndex();
                Toast.makeText(getContext(), selected.hasDrawable() ? "Item at " + (picker.getSelectedIndex() + 1) + " is selected" : selected.getText() + " is selected", Toast.LENGTH_SHORT).show();
            }
        };
        hpImage.setChangeListener(listener);



        readData1(new getStressLevel.MyCallback() {
            @Override
            public void onCallback(String value, String value1, String value2, String value3, String value4, String value5) {

                double gsr = 0.0;
                DecimalFormat numberFormat = new DecimalFormat("##.000000");
                double holdgsr = Float.parseFloat(value1);
                //stresslevel = (Double.parseDouble(numberFormat.format(gsr)) * 0.25f) + (Float.parseFloat(value1) * 0.25f) + (Float.parseFloat(value1) * 0.25f) + (Float.parseFloat(value1) * 0.25f);

                int age = 15;


                //GET BPM
                if (age >= 6 || age <= 15){

                    if (Integer.parseInt(value) >= 70 && Integer.parseInt(value) <= 100){
                        bpm = 10.00;
                        txtbpm.setText("Your Heart Rate is normal");
                        txtbpm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checked, 0, 0, 0);
                        txtbpm.setCompoundDrawablePadding(30);
                    } else if (Integer.parseInt(value) >= 101 && Integer.parseInt(value) <= 110) {
                        bpm = 14.28;
                        txtbpm.setText("Your Heart Rate is slightly high");
                        txtbpm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                        txtbpm.setCompoundDrawablePadding(30);

                    } else if (Integer.parseInt(value) >= 111 && Integer.parseInt(value) <= 120) {
                        bpm = 28.56;

                        txtbpm.setText("Your Heart Rate is slightly high");
                        txtbpm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                        txtbpm.setCompoundDrawablePadding(30);
                    } else if (Integer.parseInt(value) >= 121 && Integer.parseInt(value) <= 130) {
                        bpm = 42.84;
                        txtbpm.setText("Your Heart Rate is slightly high");
                        txtbpm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                        txtbpm.setCompoundDrawablePadding(30);
                    } else if (Integer.parseInt(value) >= 131 && Integer.parseInt(value) <= 140) {
                        bpm = 57.12;
                        txtbpm.setText("Your Heart Rate is high");
                        txtbpm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                        txtbpm.setCompoundDrawablePadding(30);
                    } else if (Integer.parseInt(value) >= 141 && Integer.parseInt(value) <= 150) {
                        bpm = 71.4;
                        txtbpm.setText("Your Heart Rate is very high");
                        txtbpm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                        txtbpm.setCompoundDrawablePadding(30);
                    } else if (Integer.parseInt(value) >= 151 && Integer.parseInt(value) <= 160) {
                        bpm = 85.68;
                        txtbpm.setText("Your Heart Rate is very high");
                        txtbpm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                        txtbpm.setCompoundDrawablePadding(30);
                    } else if (Integer.parseInt(value) >= 161 && Integer.parseInt(value) <= 170) {
                        bpm = 100;
                        txtbpm.setText("Your Heart Rate is very high");
                        txtbpm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                        txtbpm.setCompoundDrawablePadding(30);
                    } else if (Integer.parseInt(value) >= 0 && Integer.parseInt(value) <= 69){
                        bpm = 0;
                        txtbpm.setText("Your Heart Rate is very low");
                        txtbpm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                        txtbpm.setCompoundDrawablePadding(30);
                    }
                } else if (age > 16) {

                    if (Integer.parseInt(value) >= 60 || Integer.parseInt(value) <= 100){
                        bpm = 10.00;
                        txtbpm.setText("Your Heart Rate is normal");
                        txtbpm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checked, 0, 0, 0);
                        txtbpm.setCompoundDrawablePadding(30);
                        txtbpm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                        txtbpm.setCompoundDrawablePadding(30);
                    } else if (Integer.parseInt(value) >= 101 && Integer.parseInt(value) <= 110) {
                        bpm = 14.28;
                        txtbpm.setText("Your Heart Rate is slightly high");
                        txtbpm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                        txtbpm.setCompoundDrawablePadding(30);
                    } else if (Integer.parseInt(value) >= 111 && Integer.parseInt(value) <= 120) {
                        bpm = 28.56;
                        txtbpm.setText("Your Heart Rate is slightly high");
                        txtbpm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                        txtbpm.setCompoundDrawablePadding(30);
                    } else if (Integer.parseInt(value) >= 121 && Integer.parseInt(value) <= 130) {
                        bpm = 42.84;
                        txtbpm.setText("Your Heart Rate is slightly high");
                        txtbpm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                        txtbpm.setCompoundDrawablePadding(30);
                    } else if (Integer.parseInt(value) >= 131 && Integer.parseInt(value) <= 140) {
                        bpm = 57.12;
                        txtbpm.setText("Your Heart Rate is high");
                        txtbpm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                        txtbpm.setCompoundDrawablePadding(30);
                    } else if (Integer.parseInt(value) >= 141 && Integer.parseInt(value) <= 150) {
                        bpm = 71.4;
                        txtbpm.setText("Your Heart Rate is high");
                        txtbpm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                        txtbpm.setCompoundDrawablePadding(30);
                    } else if (Integer.parseInt(value) >= 151 && Integer.parseInt(value) <= 160) {
                        bpm = 85.68;
                        txtbpm.setText("Your Heart Rate is very high");
                        txtbpm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                        txtbpm.setCompoundDrawablePadding(30);
                    } else if (Integer.parseInt(value) >= 161 && Integer.parseInt(value) <= 170) {
                        bpm = 100;
                        txtbpm.setText("Your Heart Rate is very high");
                        txtbpm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                        txtbpm.setCompoundDrawablePadding(30);
                    } else if (Integer.parseInt(value) >= 0 && Integer.parseInt(value) <= 69){
                        bpm = 0;
                        txtbpm.setText("Your Heart Rate is very low");
                        txtbpm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                        txtbpm.setCompoundDrawablePadding(30);
                    }
                }
                // GET GSR
                if (holdgsr >= 221 && holdgsr <= 512 ) {
                    gsr = 10.00;
                    txtgsr.setText("Your GSR is normal");
                    txtgsr.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checked, 0, 0, 0);
                    txtgsr.setCompoundDrawablePadding(30);
                } else  if (holdgsr >= 200 && holdgsr <= 220){
                    gsr = 14.28;
                    txtgsr.setText("Your GSR is slightly high");
                    txtgsr.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                    txtgsr.setCompoundDrawablePadding(30);
                } else  if (holdgsr >= 180 && holdgsr <= 199){
                    gsr = 28.56;
                    txtgsr.setText("Your GSR is slightly high");
                    txtgsr.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                    txtgsr.setCompoundDrawablePadding(30);
                } else  if (holdgsr >= 160 && holdgsr <= 179){
                    gsr = 42.84;
                    txtgsr.setText("Your GSR is slightly high");
                    txtgsr.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                    txtgsr.setCompoundDrawablePadding(30);
                } else  if (holdgsr >= 140 && holdgsr <= 159){
                    gsr = 57.12;
                    txtgsr.setText("Your GSR is high");
                    txtgsr.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                    txtgsr.setCompoundDrawablePadding(30);
                } else  if (holdgsr >= 120 && holdgsr <= 139){
                    gsr = 71.4;
                    txtgsr.setText("Your GSR is high");
                    txtgsr.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                    txtgsr.setCompoundDrawablePadding(30);
                } else  if (holdgsr >= 100 && holdgsr <= 119){
                    gsr = 85.68;
                    txtgsr.setText("Your GSR is very high");
                    txtgsr.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                    txtgsr.setCompoundDrawablePadding(30);
                } else  if (holdgsr >= 1 && holdgsr <= 99){
                    gsr = 100;
                    txtgsr.setText("Your GSR is very high");
                    txtgsr.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                    txtgsr.setCompoundDrawablePadding(30);
                }
             //GET SKIN TEMP
             if (Float.parseFloat(value2) >= 20.0 && Float.parseFloat(value2) <= 36.9){
                 skin = 50.0;
                 txtskin.setText("Your Skin Temperature is normal");
                 txtskin.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checked, 0, 0, 0);
                 txtskin.setCompoundDrawablePadding(30);

             } else if (Float.parseFloat(value2) >= 37.0 & Float.parseFloat(value2) <= 39.0) {
                 skin = 100.0;
                 txtskin.setText("Your Skin Temperature is high");
                 txtskin.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                 txtskin.setCompoundDrawablePadding(30);
             } else {
                 skin = 100.0;
                 txtskin.setText("Your Skin Temperature is high");
                 txtskin.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cancel, 0, 0, 0);
                 txtskin.setCompoundDrawablePadding(30);
             }

                stresslevel = (bpm * 0.33f) + (gsr * 0.33f) + (skin * 0.33f);


                mCircleView.stopSpinning(); // stops spinning. Spinner gets shorter until it disappears.
                mCircleView.setValueAnimated((float) stresslevel); // stops spinning. Spinner spins until on top. Then fills to set value.



                if (stresslevel >= 50){
                    readData3(new MyCallback2() {
                        @Override
                        public void onCallback(String value, String value1) {
                            inputData1(value, value1);
                        }
                    });

                    b1.setVisibility(View.VISIBLE);
                    b1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                            breathe menu = new breathe();
                            FragmentManager manager3 = getFragmentManager();
                            manager3.beginTransaction().replace(R.id.contentLayout,
                                    menu,
                                    menu.getTag()).commit();






                        }
                    });

                }

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData(stresslevel);
            }

        });




    return view;
    }



    private void inputData(double stresslevel1) {

        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        Date date = new Date();
        session = new SessionManagement(getContext());
        HashMap<String, String> user = session.getUserDetails();
        String date1 = df.format(new Date());
        email = user.get(SessionManagement.KEY_EMAIL);
        String mood = "";
        if(selectIndex == 0){
             mood = "happy";
        }
        if(selectIndex == 1){
            mood =  "great";
        }

        if(selectIndex == 2){
            mood =  "frustrated";
        }
        if(selectIndex == 3){
            mood =  "sad";
        }
        if(selectIndex == 4){
            mood =  "angry";
        }
        if(selectIndex == 5){
            mood =  "neutral";
        }
        if(selectIndex == 6){
            mood =  "in love";
        }

        DecimalFormat numberFormat = new DecimalFormat("##.00");
        convert_stresslevels data = new convert_stresslevels(Double.parseDouble(numberFormat.format(stresslevel1)),date1, mood);
        mDatabase.child(email.replaceAll("[-+.^:,@]", "")).child("Stress Logs").child(UUID.randomUUID().toString()).setValue(data);


    }


    public void readData1(final getStressLevel.MyCallback myCallback) {
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
    public interface MyCallback {
        void onCallback(String value, String value1, String value2, String value3, String value4, String value5 );
    }

    private void inputData1(String emailPsych, String emailGuardian) {

        DateFormat df = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
        Date date = new Date();
        Notification data1 = new Notification("Your stress level is high!", df.format(date));

        mDatabase.child(email.replaceAll("[-+.^:,@]", "")).child("notifications").child(UUID.randomUUID().toString()).setValue(data1);
        mDatabase.child(emailPsych.replaceAll("[-+.^:,@]", "")).child("notifications").child(UUID.randomUUID().toString()).setValue(data1);
        mDatabase.child(emailGuardian.replaceAll("[-+.^:,@]", "")).child("notifications").child(UUID.randomUUID().toString()).setValue(data1);


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

    public interface MyCallback2 {
        void onCallback(String value, String value1);
    }

}
