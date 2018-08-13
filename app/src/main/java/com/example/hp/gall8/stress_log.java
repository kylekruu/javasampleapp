package com.example.hp.gall8;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class stress_log extends Fragment {
    FirebaseAuth mAuth;
    SessionManagement session;
    private ListView mainListView ;
    DatabaseReference mDatabase;
    String email;
    ArrayList<convert_stresslevels> a = new ArrayList<>();
    DateFormat df;
    Date date;
    public stress_log() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav, container, false);
        mAuth = FirebaseAuth.getInstance();
        session = new SessionManagement(getContext());
        mDatabase = FirebaseDatabase.getInstance().getReference("data");
        mainListView = (ListView) view.findViewById(R.id.listView);
        HashMap<String, String> user = session.getUserDetails();

        email = user.get(SessionManagement.KEY_EMAIL);


        mDatabase.child(email.replaceAll("[-+.^:,@]", "")).child("Stress Logs").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                Double firstValue = (Double) map.get("stresslevel");
                String secondValue = (String) map.get("time1");
                String thirdValue = (String) map.get("mood");
                if (getActivity()!=null) {
                    a.add(new convert_stresslevels(firstValue.floatValue(), secondValue, thirdValue));
                    logAdapter adapter = new logAdapter(getContext(), R.layout.single_row, a);
                    mainListView.setAdapter(adapter);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;

    }

    public void readData1(final navFragment.MyCallback myCallback){

        mDatabase.child(email.replaceAll("[-+.^:,@]", "")).child("notifications").orderByKey().addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                    String firstValue = (String) map.get("message");
                    String secondValue = (String) map.get("time");


                    myCallback.onCallback(firstValue, secondValue);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }


        });
    }

    public interface MyCallback {
        void onCallback(String value, String value1 );
    }
}
