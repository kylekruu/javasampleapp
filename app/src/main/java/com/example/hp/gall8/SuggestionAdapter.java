package com.example.hp.gall8;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HP on 4/12/2018.
 */

public class SuggestionAdapter extends ArrayAdapter<Suggestion> {

    private Context context;
    private ArrayList<Suggestion> suggestionArrayList;
    private int resourceId;

    public SuggestionAdapter(Context context, int resource, ArrayList<Suggestion> list) {
        super(context, resource, list);
        this.context = context;
        this.suggestionArrayList = list;
        this.resourceId = resource;
    }

    @Override
    public int getCount() {
        return suggestionArrayList.size();
    }

    @Nullable
    @Override
    public Suggestion getItem(int position) {
        return suggestionArrayList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mLayoutInflater= LayoutInflater.from(context);
        View view= mLayoutInflater.inflate(R.layout.single_row, parent, false);

        TextView txtMessage= (TextView) view.findViewById(R.id.textView);
        TextView txtTime=(TextView)view.findViewById(R.id.textView2);

        Suggestion mNotification = getItem(position);
        txtMessage.setText(mNotification.getSuggestion());
        txtTime.setText(mNotification.getDate());


        return view;
    }


}


