package com.team_three.medicalreminder.addmedication.view;

import com.team_three.medicalreminder.model.MedicationPOJO;

public interface AddAndEditMedicationInterface {
    void updateMedication(MedicationPOJO medication);

    void showMedication();

    void addMedication(MedicationPOJO medication);
}
