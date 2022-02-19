package com.team_three.medicalreminder.addmedication.presenter;

import com.team_three.medicalreminder.model.MedicationPOJO;

public interface AddMedicationPresenterInterface {
    void updateToDatabase(MedicationPOJO medication);
    void addToDatabase(MedicationPOJO medication);
    MedicationPOJO showMedication();
}
