package com.example.hp.gall8;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class menuList extends ArrayAdapter<menuAdapter> {

    private Context context;
    private ArrayList<menuAdapter> menuArrayList;
    private int resourceId;

    public menuList(Context context, int resource, ArrayList<menuAdapter> list) {
        super(context, resource, list);
        this.context = context;
        this.menuArrayList = list;
        this.resourceId = resource;
    }

    @Override
    public int getCount() {
        return menuArrayList.size();
    }

    @Nullable
    @Override
    public menuAdapter getItem(int position) {
        return menuArrayList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mLayoutInflater= LayoutInflater.from(context);
        View view= mLayoutInflater.inflate(R.layout.menurow, parent, false);

        TextView txtMessage= (TextView) view.findViewById(R.id.textView);
        TextView txtTime=(TextView)view.findViewById(R.id.textView2);

        menuAdapter mNotification = getItem(position);
        txtMessage.setText(mNotification.getMessage());



        return view;
    }



}