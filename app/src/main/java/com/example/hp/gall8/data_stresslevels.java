package com.example.hp.gall8;

/**
 * Created by HP on 3/29/2018.
 */

public class data_stresslevels {

    private float pulse_rate;
    private float gsr;
    private float skin;
    private float Xaccelerometer;
    private float Yaccelerometer;
    private float Zaccelerometer;
    private String time;



    public data_stresslevels(){

    }
    public data_stresslevels(float pulse, float gsr,float skin, float Xacce,float Yacce,float Zacce, String time1){
        this.pulse_rate = pulse;
        this.gsr = gsr;
        this.skin = skin;
        this.Xaccelerometer = Xacce;
        this.Yaccelerometer = Yacce;
        this.Zaccelerometer = Zacce;
        this.time = time1;


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
    public float getXAccelerometer() {
        return Xaccelerometer;
    }
    public float getYAccelerometer() {
        return Yaccelerometer;
    }
    public float getZAccelerometer() {
        return Zaccelerometer;
    }
    public String getTime() {
        return time;
    }







}
