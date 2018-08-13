package com.example.hp.gall8;

/**
 * Created by HP on 4/12/2018.
 */

public class Suggestion {
    private String suggestion;
    private String date;


    public Suggestion() {

    }

    public Suggestion(String suggest, String date){
        this.suggestion = suggest;
        this.date = date;
    }


    public String getDate() {
        return date;
    }

    public String getSuggestion() {
        return suggestion;
    }
}
