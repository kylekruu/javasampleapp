package com.example.hp.gall8;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class addPsychGuradian extends Fragment {
    EditText addPsych, addGuard;
    String txtPsych, txtGuard;
    SessionManagement session;
    Button btn;
    String email;
    DatabaseReference mDatabase;
    public addPsychGuradian() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_psych_guradian, container, false);

        session = new SessionManagement(getContext());
        addGuard = (EditText) view.findViewById(R.id.edtGuard);
        addPsych = (EditText) view.findViewById(R.id.edtPsych);
        btn = (Button) view.findViewById(R.id.btnAdd);
        txtGuard = addGuard.getText().toString();
        txtPsych = addPsych.getText().toString();

        mDatabase = FirebaseDatabase.getInstance().getReference("path");
        HashMap<String, String> user = session.getUserDetails();

        email = user.get(SessionManagement.KEY_EMAIL);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        if (snapshot.hasChild(txtPsych.replaceAll("[-+.^:,@]", ""))) {

                            addParent add1 = new addParent(email, "Do you want to confirm?");
                            mDatabase.child(txtPsych.replaceAll("[-+.^:,@]", "")).child("Confirmations").child(UUID.randomUUID().toString()).setValue(add1);

                        }
                        if (snapshot.hasChild(txtGuard.replaceAll("[-+.^:,@]", ""))) {

                                addParent add1 = new addParent(email, "Do you want to confirm?");
                                mDatabase.child(txtGuard.replaceAll("[-+.^:,@]", "")).child("Confirmations").child(UUID.randomUUID().toString()).setValue(add1);

                        } else {
                            Toast.makeText(getContext(), "User Does Not Exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });







            }
        });






        return view;
    }

}
