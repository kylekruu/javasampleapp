package com.example.hp.gall8;

import java.util.Date;

/**
 * Created by HP on 3/10/2018.
 */

public class data_sensors {

    private float pulse_rate;
    private float gsr;
    private float skin;
    private float accelerometer;
    private String date;
    private String email;


    public data_sensors(){

    }
    public data_sensors(float pulse, float gsr,float skin, float acce, String date, String email){
        this.pulse_rate = pulse;
        this.gsr = gsr;
        this.skin = skin;
        this.accelerometer = acce;
        this.date = date;
        this.email = email;

    }

    public float getPulse() {
        return pulse_rate;
    }
    public float getGsr() {
        return gsr;
    }
    public float getSkin() {
        return skin;
    }
    public float getAccelerometer() {
        return accelerometer;
    }
    public String getDate() {
        return date;
    }
    public String getEmail() {
        return email;
    }
}
