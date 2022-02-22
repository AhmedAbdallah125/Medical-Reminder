package com.team_three.medicalreminder.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class RequestPojo implements Parcelable {
    private int img;
   private String name;
   private  String email;
   private String id;
   private String myEmail;
   private int acceptance;

    public RequestPojo(int img, String name, String email,  String myEmail, int acceptance) {
        this.img = img;
        this.name = name;
        this.email = email;
        this.myEmail = myEmail;
        this.acceptance = acceptance;
    }

    public RequestPojo() {
    }

    public int getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(int acceptance) {
        this.acceptance = acceptance;
    }

    public String getMyEmail() {
        return myEmail;
    }

    public void setMyEmail(String myEmail) {
        this.myEmail = myEmail;
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
