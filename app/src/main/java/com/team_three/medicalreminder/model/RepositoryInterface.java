package com.team_three.medicalreminder.model;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface RepositoryInterface {
    LiveData<List<MedicationPOJO>> getAllMedication();

    void insertMedication(MedicationPOJO medication);

    void deleteMedication(MedicationPOJO medication);

    LiveData<MedicationPOJO> getMedications(int id);
    void updateMedications(MedicationPOJO medicationPOJO);
    LiveData<List<MedicationPOJO>> getActiveMedications();
    LiveData<List<MedicationPOJO>> getInactiveMedications();

}
