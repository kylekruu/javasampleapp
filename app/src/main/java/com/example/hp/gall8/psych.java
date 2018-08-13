package com.example.hp.gall8;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class psych extends AppCompatActivity {
    SessionManagement session;
    private BottomNavigationHelper a;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    psych_home homeFragment = new psych_home();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.container,
                            homeFragment,
                            homeFragment.getTag()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    psych_dash frag2 = new psych_dash();
                    FragmentManager manager1 = getSupportFragmentManager();
                    manager1.beginTransaction().replace(R.id.container,
                            frag2,
                            frag2.getTag()).commit();
                    return true;
                case R.id.navigation_notifications:
                    psych_notif frag3 = new psych_notif();
                    FragmentManager manager2 = getSupportFragmentManager();
                    manager2.beginTransaction().replace(R.id.container,
                            frag3,
                            frag3.getTag()).commit();
                    return true;
                case R.id.navigation_settings:
                    psych_menu frag4 = new psych_menu();
                    FragmentManager manager3 = getSupportFragmentManager();
                    manager3.beginTransaction().replace(R.id.container,
                            frag4,
                            frag4.getTag()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psych);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.setSelectedItemId(R.id.navigation_home);
        session = new SessionManagement(getApplicationContext());
        session.checkLogin();

        if(SaveSharedPreference.getUserName(psych.this).length() == 0)
        {
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
        }

        else
        {
            session = new SessionManagement(getApplicationContext());
            session.checkLogin();

        }

        a.disableShiftMode(navigation);
    }

}
