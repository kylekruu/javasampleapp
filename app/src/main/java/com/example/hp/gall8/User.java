package com.example.hp.gall8;

/**
 * Created by HP on 2/21/2018.
 */

public class User {
    private String userId;
    private String userLname;
    private String userFname;
    private String userBday;
    private String userGender;
    private String userLevel;
    private String userEmail;
    private String userPassword;


    public User(){

    }

    public User(String user_id, String user_lname, String user_fname, String user_bday, String user_gender, String user_level, String user_email, String user_password) {
        this.userId = user_id;
        this.userFname = user_fname;
        this.userLname = user_lname;
        this.userBday = user_bday;
        this.userGender = user_gender;
        this.userLevel = user_level;
        this.userEmail = user_email;
        this.userPassword = user_password;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserFname() {
        return userFname;
    }

    public String getUserBday() {
        return userBday;
    }

    public String getUserLname() {
        return userLname;
    }

    public String getUserGender() {
        return userGender;
    }


    public String getUserLevel() {
        return userLevel;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }
}