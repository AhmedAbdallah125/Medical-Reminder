package com.team_three.medicalreminder.medicationPatient.presenter;

import com.team_three.medicalreminder.model.MedicationPOJO;

public interface PatientMedPresenterInterface {
    void getPatientMedicationList(String email);
    void deleteMedication(String email,String medicationId);
    void updateMedication(String email,MedicationPOJO medicationPOJO);
}
