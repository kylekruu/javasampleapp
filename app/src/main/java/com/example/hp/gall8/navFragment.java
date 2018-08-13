package com.example.hp.gall8;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class navFragment extends Fragment  {
    FirebaseAuth mAuth;
    SessionManagement session;
    private ListView mainListView ;
    DatabaseReference mDatabase;
    String email;
    ArrayList<Notification> notificationList = new ArrayList<>();

    public navFragment() {
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




        mDatabase.child(email.replaceAll("[-+.^:,@]", "")).child("notifications").addChildEventListener(new ChildEventListener() {
            @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    String firstValue = (String) map.get("message");
                    String secondValue = (String) map.get("time");

                    if (getActivity() != null){
                        notificationList.add(new Notification(firstValue, secondValue));
                        NotificationAdapter adapter = new NotificationAdapter(getContext(),R.layout.single_row,notificationList);
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

public ArrayList<Notification> getNotificationList() {



        ArrayList<Notification> notificationList = new ArrayList<>();
        notificationList.add(new Notification("Bikram posted in this group", "4 hours ago"));
        notificationList.add(new Notification("Bidhan posted in this group", "4 hours ago"));
        notificationList.add(new Notification("Kumar posted in this group", "5 hours ago"));
        notificationList.add(new Notification("Bijay posted in this group", "4 hours ago"));
        notificationList.add(new Notification("Jagat posted in this group", "4 hours ago"));
        notificationList.add(new Notification("Ayush posted in this group", "6 hours ago"));

        return notificationList;

        }


public void readData1(final MyCallback myCallback){

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
