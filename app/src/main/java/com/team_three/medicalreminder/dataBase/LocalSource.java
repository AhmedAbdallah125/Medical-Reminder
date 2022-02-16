package com.team_three.medicalreminder.dataBase;

import androidx.lifecycle.LiveData;

import com.team_three.medicalreminder.model.MedicationPOJO;

import java.util.List;

public interface LocalSource {
    LiveData<List<MedicationPOJO>> getAllMedication();

    void insertMedication(MedicationPOJO medication);

    void deleteMedication(MedicationPOJO medication);
}
