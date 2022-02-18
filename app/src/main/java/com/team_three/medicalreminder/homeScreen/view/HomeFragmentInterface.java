package com.team_three.medicalreminder.homeScreen.view;

import com.team_three.medicalreminder.model.MedicationPOJO;

import java.util.List;

public interface HomeFragmentInterface {

    void showMedications(List<MedicationPOJO> storedMedications);
}
