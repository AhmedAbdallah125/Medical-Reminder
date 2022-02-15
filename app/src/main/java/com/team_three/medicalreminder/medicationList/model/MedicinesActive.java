package com.team_three.medicalreminder.medicationList.model;

public class MedicinesActive {
    private int iconResource;
    private String name;
    private int strengthNumber;
    private String strengthWeight;
    private int numberOfUnits;
    private String medsForm;
    private String left;
    public MedicinesActive(){}
    public MedicinesActive(int iconResource, String name, int strengthNumber, String strengthWeight, int numberOfUnits, String medsForm, String left) {
        this.iconResource = iconResource;
        this.name = name;
        this.strengthNumber = strengthNumber;
        this.strengthWeight = strengthWeight;
        this.numberOfUnits = numberOfUnits;
        this.medsForm = medsForm;
        this.left = left;
    }

    public int getIconResource() {
        return iconResource;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStrengthNumber() {
        return strengthNumber;
    }

    public void setStrengthNumber(int strengthNumber) {
        this.strengthNumber = strengthNumber;
    }

    public String getStrengthWeight() {
        return strengthWeight;
    }

    public void setStrengthWeight(String strengthWeight) {
        this.strengthWeight = strengthWeight;
    }

    public int getNumberOfUnits() {
        return numberOfUnits;
    }

    public void setNumberOfUnits(int numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

    public String getMedsForm() {
        return medsForm;
    }

    public void setMedsForm(String medsForm) {
        this.medsForm = medsForm;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }
}
