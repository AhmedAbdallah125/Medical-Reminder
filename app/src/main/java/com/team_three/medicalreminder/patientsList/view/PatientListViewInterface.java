package com.team_three.medicalreminder.patientsList.view;

import com.team_three.medicalreminder.model.PatientPojo;
import com.team_three.medicalreminder.model.RequestPojo;

import java.util.List;

public interface PatientListViewInterface {
    void loadPatients(List<PatientPojo> patients);

}
