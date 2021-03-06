package com.team_three.medicalreminder.homeScreen.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.team_three.medicalreminder.model.MedicationPOJO;

import java.util.List;

public interface HomePresenterInterface {
    void getMedicationDay(LifecycleOwner lifecycleOwner, long time);
    void updatePosition(int position);
    void updateTime(long time);
    void addMedicationListViaNetwork(List<MedicationPOJO> medicationPOJOS, String email);
    void deleteMedication(MedicationPOJO medicationPOJO);
    void updateMedication(MedicationPOJO medicationPOJO);
    void notifyMedicationChangeFromFirebase(String email);

    void deleteMedication(String email, String medicationId);

    void deleteInPatientMedicationList(String email, String medicationID);
    void updatePatientMedicationList(String email,MedicationPOJO medicationPOJO);

}
