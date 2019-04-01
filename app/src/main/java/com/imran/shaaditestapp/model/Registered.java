package com.imran.shaaditestapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Registered {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("age")
    @Expose
    private int age;

    public String getDate() {
        return date;
    }

    public int getAge() {
        return age;
    }
}
