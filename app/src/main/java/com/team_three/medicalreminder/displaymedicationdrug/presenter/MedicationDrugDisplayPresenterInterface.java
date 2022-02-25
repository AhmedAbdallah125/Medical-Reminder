package com.team_three.medicalreminder.displaymedicationdrug.presenter;

import com.team_three.medicalreminder.model.MedicationPOJO;

public interface MedicationDrugDisplayPresenterInterface {
    void deleteMedication(MedicationPOJO medication);

    void updateMedication(MedicationPOJO medication);

    void deleteMedicationInFireBase(MedicationPOJO medication,String email);
}
