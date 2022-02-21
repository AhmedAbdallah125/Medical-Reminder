package com.team_three.medicalreminder.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class Taker implements Parcelable {
    private int img;
   private String name;
   private  String email;
   private String id;

    public Taker(int img, String name) {
        this.img = img;
        this.name = name;
    }

    public Taker(String name, String email) {
        this.name = name;
        this.email = email;
    }


    public Taker() {
    }

    public Taker(String email, int img, String name, String id) {
        this.img = img;
        this.name = name;
        this.email = email;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
