package com.team_three.medicalreminder.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import java.sql.Timestamp;  Timestamp
@SuppressLint("ParcelCreator")
@Entity(tableName = "medications")
public class MedicationPOJO implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public void setTimeAndDose(Map<String, Integer> timeAndDose) {
        this.timeAndDose = timeAndDose;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    private String medicationName;
    private int strength;
    private String weight;
    private String format;
    private int imageID;
    @NotNull
    private Long startDate;
    @NotNull
    private Long endDate;
    private String takeTimePerDay;//how often do you take
    private Map<String, Integer> timeAndDose;
    private String instruction;
    private boolean fillReminder;
    private boolean dailyReminder;
    private int leftNumber;
    private int leftNumberReminder;
    private boolean isActive;
    private List<Boolean> isTakenList;
    private String medicationReason;
    private String recurrence;

    public String getMedicationReason() {
        return medicationReason;
    }

    public void setMedicationReason(String medicationReason) {
        this.medicationReason = medicationReason;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public int getId() {
        return id;
    }

    public List<Boolean> getIsTakenList() {
        return isTakenList;
    }

    public void setIsTakenList(List<Boolean> isTakenList) {
        this.isTakenList = isTakenList;
    }

    @NotNull
    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(@NotNull String medicationName) {
        this.medicationName = medicationName;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    @NotNull
    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull Long startDate) {
        this.startDate = startDate;
    }

    @NotNull
    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(@NotNull Long endDate) {
        this.endDate = endDate;
    }

    public String getTakeTimePerDay() {
        return takeTimePerDay;
    }

    public void setTakeTimePerDay(String takeTimePerDay) {
        this.takeTimePerDay = takeTimePerDay;
    }

    public Map<String, Integer> getTimeAndDose() {
        return timeAndDose;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public boolean isFillReminder() {
        return fillReminder;
    }

    public void setFillReminder(boolean fillReminder) {
        this.fillReminder = fillReminder;
    }

    public boolean isDailyReminder() {
        return dailyReminder;
    }

    public void setDailyReminder(boolean dailyReminder) {
        this.dailyReminder = dailyReminder;
    }

    public int getLeftNumber() {
        return leftNumber;
    }

    public void setLeftNumber(int leftNumber) {
        this.leftNumber = leftNumber;
    }

    public int getLeftNumberReminder() {
        return leftNumberReminder;
    }

    public void setLeftNumberReminder(int leftNumberReminder) {
        this.leftNumberReminder = leftNumberReminder;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
