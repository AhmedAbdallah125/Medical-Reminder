package com.team_three.medicalreminder.dataBase;

import androidx.lifecycle.LiveData;

import com.team_three.medicalreminder.model.MedicationPOJO;

import java.util.List;

public interface LocalSourceInterface {
    LiveData<List<MedicationPOJO>> getAllMedication();

    void insertMedication(MedicationPOJO medication);

    void deleteMedication(MedicationPOJO medication);
    LiveData<MedicationPOJO> getMedications(int id);
    void updateMedications(MedicationPOJO medicationPOJO);
    LiveData<List<MedicationPOJO>> getActiveMedications();
    LiveData<List<MedicationPOJO>> getInactiveMedications();


    LiveData<List<MedicationPOJO>> getMedicationDay();

}
