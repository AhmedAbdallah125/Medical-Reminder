package com.team_three.medicalreminder.model;

public class PatientPojo {
    private String email;
    private String patientEmail;
    private int image;
    private String name;
    private  String  id;

    public PatientPojo(String email, String patientEmail, int image ,String name) {
        this.email = email;
        this.patientEmail = patientEmail;
        this.image = image;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
