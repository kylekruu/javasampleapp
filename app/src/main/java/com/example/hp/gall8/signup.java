package com.example.hp.gall8;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class signup extends AppCompatActivity implements View.OnClickListener {
    DBHelper myDb;
    Button btnAdd;
    EditText email, password, cpass, name1, uname2;
    String s;
     EditText editTextEmail;
     EditText editTextPassword;
     Button buttonSignup;
    ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;
    EditText lname;
    Spinner gender;
    EditText getdate;
    String date;
    DatePickerDialog datePickerDialog;

    //defining firebaseauth object
    private FirebaseAuth mAuth;
    SessionManagement session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
       // myDb = new DBHelper(this);
         s = getIntent().getStringExtra("EXTRA_SESSION_ID");
        session = new SessionManagement(getApplicationContext());
        //uname2 = (EditText) findViewById(R.id.username);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        cpass = (EditText) findViewById(R.id.confirm);
         name1 = (EditText) findViewById(R.id.name);
         lname = (EditText) findViewById(R.id.lname);
         gender = (Spinner) findViewById(R.id.spinner);
         getdate = (EditText) findViewById(R.id.bDate);


        buttonSignup = (Button) findViewById(R.id.create);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("path");
        buttonSignup.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);




        getdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(signup.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                getdate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }


        });


        ArrayAdapter<String> adapter;
        List<String> list;

        list = new ArrayList<String>();
        list.add("Male");
        list.add("Female");


        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter);


    }
    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

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
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

       progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    String id  = mDatabase.push().getKey();
                    User user = new User(id, name1.getText().toString(),lname.getText().toString(),getdate.getText().toString(),gender.getSelectedItem().toString(), s, email, password);
                    mDatabase.child(email.replaceAll("[-+.^:,@]","")).setValue(user);

                    finish();
                    if (s.equals("User")) {
                        session.createLoginSession(s, name1.getText().toString(),lname.getText().toString(),getdate.getText().toString(),gender.getSelectedItem().toString(), email);
                        startActivity(new Intent(signup.this, MainActivity.class));
                    }
                    if (s.equals("Guardian")){
                        session.createLoginSession(s, name1.getText().toString(),lname.getText().toString(),getdate.getText().toString(),gender.getSelectedItem().toString(), email);
                        startActivity(new Intent(signup.this, guardian.class));
                    }
                    if (s.equals("Psychiatrist")){
                        session.createLoginSession(s, name1.getText().toString(),lname.getText().toString(),getdate.getText().toString(),gender.getSelectedItem().toString(), email);
                        startActivity(new Intent(signup.this, psych.class));
                    }
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        //calling register method on click
        registerUser();
    }
}