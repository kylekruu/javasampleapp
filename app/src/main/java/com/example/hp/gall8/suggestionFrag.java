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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class suggestionFrag extends Fragment {

    FirebaseAuth mAuth;
    SessionManagement session;
    private ListView mainListView ;
    DatabaseReference mDatabase;
    String email;
    ArrayList<Suggestion> suggestionArrayList = new ArrayList<>();
    public suggestionFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_suggestion, container, false);

        mAuth = FirebaseAuth.getInstance();
        session = new SessionManagement(getContext());
        mDatabase = FirebaseDatabase.getInstance().getReference("data");
        mainListView = (ListView) view.findViewById(R.id.listView);
        HashMap<String, String> user = session.getUserDetails();

        email = user.get(SessionManagement.KEY_EMAIL);




        mDatabase.child(email.replaceAll("[-+.^:,@]", "")).child("Suggestion").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                String firstValue = (String) map.get("suggestion");
                String secondValue = (String) map.get("date");

                if (getActivity() != null){
                    suggestionArrayList.add(new Suggestion(firstValue, secondValue));
                    SuggestionAdapter adapter = new SuggestionAdapter(getContext(),R.layout.single_row,suggestionArrayList);
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

}
