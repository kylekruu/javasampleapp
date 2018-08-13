package com.example.hp.gall8;

/**
 * Created by HP on 1/31/2018.
 */

public class menuAdapter {

    private String message;
    private String time;

    public menuAdapter(String message) {
        this.message = message;

    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}