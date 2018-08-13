package com.example.hp.gall8;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
private BottomNavigationHelper a;
   SessionManagement session;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    HomeFragment homeFragment = new HomeFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.contentLayout,
                            homeFragment,
                            homeFragment.getTag()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    dashFragment dashFrag = new dashFragment();
                    FragmentManager manager1 = getSupportFragmentManager();
                    manager1.beginTransaction().replace(R.id.contentLayout,
                            dashFrag,
                            dashFrag.getTag()).commit();
                    return true;
                case R.id.navigation_notifications:
                    navFragment navFrag = new navFragment();
                    FragmentManager manager2 = getSupportFragmentManager();
                    manager2.beginTransaction().replace(R.id.contentLayout,
                            navFrag,
                            navFrag.getTag()).commit();
                    return true;

                case R.id.navigation_settings:
                    Menu menu = new Menu();
                    FragmentManager manager3 = getSupportFragmentManager();
                    manager3.beginTransaction().replace(R.id.contentLayout,
                            menu,
                            menu.getTag()).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
        session = new SessionManagement(getApplicationContext());
        session.checkLogin();

        if(SaveSharedPreference.getUserName(MainActivity.this).length() == 0)
        {
            Toast.makeText(getApplicationContext(), SaveSharedPreference.getUserName(MainActivity.this), Toast.LENGTH_SHORT).show();
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
