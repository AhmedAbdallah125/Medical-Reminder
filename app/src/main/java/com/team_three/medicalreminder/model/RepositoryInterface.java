package com.team_three.medicalreminder.model;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface RepositoryInterface {
    LiveData<List<MedicationPOJO>> getAllMedication();

    void insertMedication(MedicationPOJO medication);

    void deleteMedication(MedicationPOJO medication);
}
