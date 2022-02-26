package com.team_three.medicalreminder.medicationPatient.presenter;

import com.team_three.medicalreminder.model.MedicationPOJO;

public interface PatientMedPresenterInterface {
    void getPatientMedicationList(String email);
    void deleteMedication(MedicationPOJO medicationPOJO);
}
