package com.example.hp.gall8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class userlevel extends AppCompatActivity {
    Button user, guardian, doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlevel);

        user = (Button) findViewById(R.id.button2);
        guardian = (Button) findViewById(R.id.button3);
        doc = (Button) findViewById(R.id.button4);

        user.setOnClickListener(new View.OnClickListener() {
            public void onClick (View a) {
                String user = "User";
                Intent intent = new Intent(getBaseContext(), signup.class);
                intent.putExtra("EXTRA_SESSION_ID", user);
                startActivity(intent);
            }
        });

        guardian.setOnClickListener(new View.OnClickListener() {
            public void onClick (View a) {
                String user = "Guardian";
                Intent intent = new Intent(getBaseContext(), signup.class);
                intent.putExtra("EXTRA_SESSION_ID", user);
                startActivity(intent);
            }
        });

        doc.setOnClickListener(new View.OnClickListener() {
            public void onClick (View a) {
                String user = "Psychiatrist";
                Intent intent = new Intent(getBaseContext(), signup.class);
                intent.putExtra("EXTRA_SESSION_ID", user);
                startActivity(intent);
            }
        });

    }

}
