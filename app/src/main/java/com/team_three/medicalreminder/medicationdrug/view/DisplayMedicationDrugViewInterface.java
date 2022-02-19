package com.team_three.medicalreminder.medicationdrug.view;

import com.team_three.medicalreminder.model.MedicationPOJO;

public interface DisplayMedicationDrugViewInterface {
    void showMedication(MedicationPOJO medication);

    void editMedication(MedicationPOJO medication);

    void deleteMedication(MedicationPOJO medication);
}
