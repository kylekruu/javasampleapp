package com.example.hp.gall8;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class psych_menu extends Fragment {
    private  ListView mainListView;
    SessionManagement session;

    public psych_menu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_psych_menu, container, false);
        mainListView = (ListView) view.findViewById(R.id.mainListView);
        session = new SessionManagement(getContext());
        TextView welc = (TextView) view.findViewById(R.id.name);
        session = new SessionManagement(getContext());
        HashMap<String, String> user = session.getUserDetails();

        // name
        String name = user.get(SessionManagement.KEY_LNAME);

        // email
        String email = user.get(SessionManagement.KEY_EMAIL);

        welc.setText(email);



        menuList adapter = new menuList(getContext(),R.layout.menurow,getMenuList());
        mainListView.setAdapter(adapter);


        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                if (position == 0) {
                    Activity menu = new Activity();
                    FragmentManager manager3 = getFragmentManager();
                    manager3.beginTransaction().replace(R.id.frameLayout,
                            menu,
                            menu.getTag()).commit();
                }

                if (position == 1) {

                    Intent myIntent = new Intent(view.getContext(), MyPreferenceActivity.class);
                    startActivity(myIntent);
                }
                if (position == 2) {

                    session.logoutUser();
                }

            }
        });


        return view;
    }
    public ArrayList<menuAdapter> getMenuList() {
        ArrayList<menuAdapter>  menuList2 =  new ArrayList<>();
        menuList2.add(new menuAdapter("Activity"));
        menuList2.add(new menuAdapter("Settings"));
        menuList2.add(new menuAdapter("Logout"));


        return menuList2;

    }

}
