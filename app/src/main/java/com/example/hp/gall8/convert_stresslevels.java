package com.example.hp.gall8;

import java.util.Date;

/**
 * Created by HP on 4/9/2018.
 */

public class convert_stresslevels {

    private double stresslevel;
    private String time;
    private String mood;


    public convert_stresslevels (double stress, String time1, String mood1) {
        this.stresslevel = stress;
        this.time = time1;
        this.mood = mood1;

    }

    public String getTime1() {
        return time;
    }

    public double getStresslevel() {
        return stresslevel;
    }

    public String getMood() {
        return mood;
    }
}
