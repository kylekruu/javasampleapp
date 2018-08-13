package com.example.hp.gall8;


import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class Menu extends Fragment {
    private  ListView mainListView;
    SessionManagement session;
    TextView name;

    public Menu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_menu, container, false);
        mainListView = (ListView) view.findViewById(R.id.mainListView);
        name = (TextView) view.findViewById(R.id.name);
        session = new SessionManagement(getContext());

        HashMap<String, String> user = session.getUserDetails();

        String name1 = user.get(SessionManagement.KEY_LNAME);

        name.setText(name1);



        menuList adapter = new menuList(getContext(),R.layout.menurow,getMenuList());
        mainListView.setAdapter(adapter);


        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == 0) {
                    Intent myIntent = new Intent(view.getContext(), ScreenSlidePagerActivity.class);
                    startActivity(myIntent);
                }

                if (position == 1) {
                    suggestionFrag menu = new suggestionFrag();
                    FragmentManager manager3 = getFragmentManager();
                    manager3.beginTransaction().replace(R.id.contentLayout,
                            menu,
                            menu.getTag()).commit();
                }

                if (position == 2) {

                    addPsychGuradian menu = new addPsychGuradian();
                    FragmentManager manager3 = getFragmentManager();
                    manager3.beginTransaction().replace(R.id.contentLayout,
                            menu,
                            menu.getTag()).commit();
                }
                if (position == 3) {

                    musicPlayer menu = new musicPlayer();
                    FragmentManager manager3 = getFragmentManager();
                    manager3.beginTransaction().replace(R.id.contentLayout,
                            menu,
                            menu.getTag()).commit();

                }
                if (position == 4) {

                    stress_log menu = new stress_log();
                    FragmentManager manager3 = getFragmentManager();
                    manager3.beginTransaction().replace(R.id.contentLayout,
                            menu,
                            menu.getTag()).commit();
                }
                if (position == 5) {

                    Intent myIntent = new Intent(view.getContext(), Instructions.class);
                    startActivity(myIntent);
                }
                if (position == 6) {

                    session.logoutUser();
                }

            }
        });


        return view;
    }
    public ArrayList<menuAdapter> getMenuList() {
        ArrayList<menuAdapter>  menuList2 =  new ArrayList<>();
        menuList2.add(new menuAdapter("Health Profile"));
        menuList2.add(new menuAdapter("Suggestion"));
        menuList2.add(new menuAdapter("Add Psychiatrist/Guardian"));
        menuList2.add(new menuAdapter("Music Player"));
        menuList2.add(new menuAdapter("Stress Log"));
        menuList2.add(new menuAdapter("Instructions"));
        menuList2.add(new menuAdapter("Logout"));


        return menuList2;

    }
}
