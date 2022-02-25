package com.team_three.medicalreminder.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class TakerPOJO implements Parcelable {
    private String patientEmail;
    private String name;
    private String email;
    private  int img;
    private String requestId;

    public TakerPOJO(String patientEmail, String name, String email, int img) {
        this.patientEmail = patientEmail;
        this.name = name;
        this.email = email;
        this.img = img;
        this.requestId = requestId;
    }

    public TakerPOJO() {
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
