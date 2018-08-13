package com.example.hp.gall8;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity implements View.OnClickListener{

    Button login;
    Button back;
    private static final String TAG = "login";
    public String userlevel;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    EditText editTextEmail, editTextPassword;
    String email, password;
    ProgressBar progressBar;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDatabase = FirebaseDatabase.getInstance().getReference("path");
           // db = new DBHelper(this);
            session = new SessionManagement(getApplicationContext());
             mAuth = FirebaseAuth.getInstance();
            //email1 = (EditText) findViewById(R.id.email);
             editTextEmail = (EditText) findViewById(R.id.username);
             editTextPassword = (EditText) findViewById(R.id.pass);
            login = (Button) findViewById(R.id.login);
            progressBar = (ProgressBar) findViewById(R.id.progressBar2);


        login.setOnClickListener(this);

            Button GoToNewActivity1 = (Button) findViewById(R.id.signup);
            GoToNewActivity1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    // Intent code for open new activity through intent.

                    Intent intent = new Intent(login.this, userlevel.class);
                    startActivity(intent);

                }
            });
        }

    private void userLogin() {
         email = editTextEmail.getText().toString().trim();
         password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Minimum lenght of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

       progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {

                    readData(new MyCallback() {
                        @Override
                        public void onCallback(String value, String value1, String value2, String value3, String value4, String value5) {

                            if (value.equals("User")){

                                session.createLoginSession( value,  value1,  value2,  value3,  value4,  value5);

                                SaveSharedPreference.setUserName(getApplicationContext(), value1);
                                Intent intent = new Intent(login.this, MainActivity.class);
                                startActivity(intent);

                            }
                            if (value.equals("Guardian")) {

                                session.createLoginSession(value,  value1,  value2,  value3,  value4,  value5);
                                SaveSharedPreference.setUserName(getApplicationContext(), value1);

                                Intent intent = new Intent(login.this, guardian.class);
                                startActivity(intent);
                            }

                            if (value.equals("Psychiatrist")) {
                                session.createLoginSession(value,  value1,  value2,  value3,  value4,  value5);
                                SaveSharedPreference.setUserName(getApplicationContext(), value1);

                                Intent intent = new Intent(login.this, psych.class);
                                startActivity(intent);
                            }
                        }
                    });

                    //finish();


                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void readData(final login.MyCallback myCallback){
        mDatabase.child(email.replaceAll("[-+.^:,@]", "")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User profile = dataSnapshot.getValue(User.class);
                String userlevel = profile.getUserLevel();
                String user_lname = profile.getUserLname();
                String user_fname = profile.getUserFname();
                String bday = profile.getUserBday();
                String gender = profile.getUserGender();
                String userEmail = profile.getUserEmail();
                myCallback.onCallback(userlevel, user_lname, user_fname, bday, gender, userEmail);
               
            }

            //@Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public interface MyCallback {
        void onCallback(String value, String value1, String value2, String value3, String value4, String value5);
    }

    @Override
    public void onClick(View view) {
                userLogin();

        }

}
