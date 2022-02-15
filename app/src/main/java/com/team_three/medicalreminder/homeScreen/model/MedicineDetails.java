package com.team_three.medicalreminder.homeScreen.model;

public class MedicineDetails {
    private String time;
    private int iconResource;
    private String medicinePart;
    private String medicineDetails;
    public MedicineDetails(){

    }

    public MedicineDetails(String time, int iconResource, String medicinePart, String medicineDetails) {
        this.time = time;
        this.iconResource = iconResource;
        this.medicinePart = medicinePart;
        this.medicineDetails = medicineDetails;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIconResource() {
        return iconResource;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }

    public String getMedicinePart() {
        return medicinePart;
    }

    public void setMedicinePart(String medicinePart) {
        this.medicinePart = medicinePart;
    }

    public String getMedicineDetails() {
        return medicineDetails;
    }

    public void setMedicineDetails(String medicineDetails) {
        this.medicineDetails = medicineDetails;
    }
}
