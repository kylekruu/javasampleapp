package com.example.hp.gall8;

/**
 * Created by HP on 4/8/2018.
 */

public class addParent {
    private String email;
    private String message;


    public addParent (String email1, String mess) {
        this.email = email1;
        this.message = mess;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }
}
