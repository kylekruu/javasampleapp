package com.example.hp.gall8;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by HP on 2/11/2018.
 */

public class SessionManagement {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_LNAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_FNAME = "email";
    // Email address (make variable public to access from outside)
    public static final String KEY_BDAY = "email";
    // Email address (make variable public to access from outside)
    public static final String KEY_GENDER = "email";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // Userlevel (make variable public to access from outside)
    public static final String KEY_USERLEVEL = "userlevel";
    // Constructor
    public SessionManagement(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void createLoginSession(String userlevel, String lname, String fname, String bday,String gender, String email){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_USERLEVEL, userlevel);

        // Storing email in pref
        editor.putString(KEY_LNAME, lname);

        editor.putString(KEY_FNAME, fname);

        editor.putString(KEY_BDAY, bday);

        editor.putString(KEY_GENDER, gender);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
    }
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, login.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user LAST NAME
        user.put(KEY_LNAME, pref.getString(KEY_LNAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // user BIRTHDAY
        user.put(KEY_BDAY, pref.getString(KEY_BDAY, null));

        // user GENDER
        user.put(KEY_GENDER, pref.getString(KEY_GENDER, null));

        // user FIRST NAME
        user.put(KEY_FNAME, pref.getString(KEY_FNAME, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, login.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}