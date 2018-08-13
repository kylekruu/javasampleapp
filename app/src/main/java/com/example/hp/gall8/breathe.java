package com.example.hp.gall8;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class breathe extends Fragment {


    public breathe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view  = inflater.inflate(R.layout.fragment_breathe, container, false);

        Button buttonOne = (Button) view.findViewById(R.id.start);
        buttonOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                ImageView imageView = (ImageView) view.findViewById(R.id.bubble);
                Animation pulse = AnimationUtils.loadAnimation(getContext(), R.anim.pulse);
                imageView.startAnimation(pulse);



            }
        });




        return view;
    }

}
