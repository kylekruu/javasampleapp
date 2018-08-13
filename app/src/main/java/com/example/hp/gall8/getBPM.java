package com.example.hp.gall8;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skyfishjy.library.RippleBackground;
import com.txusballesteros.SnakeView;

import java.util.Map;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class getBPM extends Fragment {
    FirebaseOptions secondaryAppConfig;
    FirebaseDatabase secondaryDatabase;
    FirebaseApp app;
    View view;
    SnakeView sn;
    public getBPM() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_get_bpm, container, false);

        sn = (SnakeView) view.findViewById(R.id.snake);
        secondaryAppConfig = new FirebaseOptions.Builder().setApplicationId("1:686755681032:android:1834a810397fd8dd").setApiKey("AIzaSyCa2ZuymenMZtGuUUyM812t_pewjr9l_iE ")
                .setDatabaseUrl("https://stress-c2014.firebaseio.com/") // Required for RTDB.
                .build();



        app = FirebaseApp.initializeApp(getContext(), secondaryAppConfig, UUID.randomUUID().toString());
        secondaryDatabase = FirebaseDatabase.getInstance(app);


        //sn.setMinValue(40);
        sn.setMaxValue(170);


        secondaryDatabase.getReference("Pasay").orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                    String firstValue = (String) map.get("BPM");


                    sn.addValue(Float.parseFloat(firstValue));





                }



            }




            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }


        });





        return view;
    }


}
