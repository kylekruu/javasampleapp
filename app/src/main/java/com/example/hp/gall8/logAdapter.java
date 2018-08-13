package com.example.hp.gall8;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by HP on 4/9/2018.
 */

public class logAdapter extends ArrayAdapter<convert_stresslevels> {
    private Context context;
    private ArrayList<convert_stresslevels> stress;
    private int resourceId;

    public logAdapter(Context context, int resource, ArrayList<convert_stresslevels> list) {
        super(context, resource, list);
        this.context = context;
        this.stress = list;
        this.resourceId = resource;
    }

    @Override
    public int getCount() {
        return stress.size();
    }

    @Nullable
    @Override
    public convert_stresslevels getItem(int position) {
        return stress.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mLayoutInflater= LayoutInflater.from(context);
        View view= mLayoutInflater.inflate(R.layout.single_row, parent, false);

        TextView txtMessage= (TextView) view.findViewById(R.id.textView);
        TextView txtTime=(TextView)view.findViewById(R.id.textView2);
        DecimalFormat numberFormat = new DecimalFormat("##.00");
        convert_stresslevels a = getItem(position);
        txtMessage.setText(Double.toString(Double.parseDouble(numberFormat.format(a.getStresslevel()))));
        txtTime.setText(a.getMood());


        return view;
    }


}
