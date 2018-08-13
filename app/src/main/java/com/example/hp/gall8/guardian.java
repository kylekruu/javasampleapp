package com.example.hp.gall8;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class guardian extends AppCompatActivity {
    private static final int CHANNEL_ID = 12345;

    private BottomNavigationHelper a;
    SessionManagement session;
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    guardian_home homeFragment = new guardian_home();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.contentLayout1,
                            homeFragment,
                            homeFragment.getTag()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    guardian_dashboard dashboard = new guardian_dashboard();
                    FragmentManager manager1 = getSupportFragmentManager();
                    manager1.beginTransaction().replace(R.id.contentLayout1,
                            dashboard,
                            dashboard.getTag()).commit();
                    return true;
                case R.id.navigation_settings:
                    guardian_menu menu = new guardian_menu();
                    FragmentManager manager2 = getSupportFragmentManager();
                    manager2.beginTransaction().replace(R.id.contentLayout1,
                            menu,
                            menu.getTag()).commit();
                    return true;
                case R.id.navigation_notifications:
                    guardian_notf menu1 = new guardian_notf();
                    FragmentManager manager3 = getSupportFragmentManager();
                    manager3.beginTransaction().replace(R.id.contentLayout1,
                            menu1,
                            menu1.getTag()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian);
        session = new SessionManagement(getApplicationContext());
        session.checkLogin();
        if(SaveSharedPreference.getUserName(guardian.this).length() == 0)
        {

            Intent intent = new Intent(this, login.class);
            startActivity(intent);

        }

            else
        {

            session = new SessionManagement(getApplicationContext());
            session.checkLogin();


        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
        mBuilder.setSmallIcon(R.drawable.icon_logo);
        mBuilder.setContentTitle("notif");
        mBuilder.setContentText("Hi");
        mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(this, guardian_dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        mBuilder.setSmallIcon(R.drawable.icon_logo);
        mBuilder.setContentTitle("My notification");
        mBuilder.setContentText("Hello World!");
        mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                // Set the intent that will fire when the user taps the notification
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(12, mBuilder.build());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);


        a.disableShiftMode(navigation);
    }

}
