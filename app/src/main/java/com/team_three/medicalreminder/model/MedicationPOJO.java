package com.team_three.medicalreminder.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

//import java.sql.Timestamp;  Timestamp

@Entity(tableName = "medications")
public class MedicationPOJO {
    @PrimaryKey
    private int id;
    @NotNull
    private String medicationName;
    private int strength;
    private String weight;
    private String format;
    private int imageID;
    @NotNull
    private String startDate;
    @NotNull
    private String endDate;
    private String takeTimePerDay;//how often do you take
    private HashMap<String, Integer> timeAndDose;
    private String instruction;
    private boolean fillReminder;
    private boolean dailyReminder;
    private int leftNumber;
    private int leftNumberReminder;
    private boolean isActive;
    private boolean isTaken;

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
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull String startDate) {
        this.startDate = startDate;
    }

    @NotNull
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(@NotNull String endDate) {
        this.endDate = endDate;
    }

    public String getTakeTimePerDay() {
        return takeTimePerDay;
    }

    public void setTakeTimePerDay(String takeTimePerDay) {
        this.takeTimePerDay = takeTimePerDay;
    }

    public HashMap<String, Integer> getTimeAndDose() {
        return timeAndDose;
    }

    public void setTimeAndDose(HashMap<String, Integer> timeAndDose) {
        this.timeAndDose = timeAndDose;
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

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }
}
