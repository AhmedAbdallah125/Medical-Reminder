package com.team_three.medicalreminder.medicationdrug.view;

import com.team_three.medicalreminder.model.MedicationPOJO;

public interface DisplayMedicationDrugViewInterface {
    void deleteMedication(MedicationPOJO medication);

    void updateMedication(MedicationPOJO medication);
}
