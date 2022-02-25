package com.team_three.medicalreminder.homeScreen.view;

import android.view.View;

import com.team_three.medicalreminder.model.MedicationPOJO;

public interface OnMedicationTimeListener {
    // for medication list
    void onTakeClick(int position, boolean take, MedicationPOJO medicationPOJO);
    void onSkipClick(int position, boolean take,MedicationPOJO medicationPOJO);

}
