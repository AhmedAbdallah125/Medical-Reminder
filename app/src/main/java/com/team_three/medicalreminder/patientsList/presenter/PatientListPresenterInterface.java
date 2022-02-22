package com.team_three.medicalreminder.patientsList.presenter;

import com.team_three.medicalreminder.model.RequestPojo;

import java.util.List;

public interface PatientListPresenterInterface {
    void loadPatients();
    public void sendEmail(String email);

}

