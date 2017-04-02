package com.vuforia.samples.Books.ui.ActivityList;

/**
 * Created by Michael on 3/21/2017.
 */

public class Company {
    private String name;
    private Boolean match;

    public Company(String name, Boolean match){
        this.name = name;
        this.match = match;
    }

    public String getName(){
        return name;
    }

    public Boolean isMatch(){
        return match;
    }
}
