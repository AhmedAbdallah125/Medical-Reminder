package com.team_three.medicalreminder.medicationPatient.view;

import com.team_three.medicalreminder.model.MedicationPOJO;

import java.util.List;

public interface PatientMedViewInterface {

    void onSuccessReturnMedicationList(List<MedicationPOJO> medicationPOJOList);


    void onFailure(String errorMessage);
    // for deleting AND Update
    default void deleteMedPatientFromFireBase(MedicationPOJO medicationPOJO){

    }
    default void updateMedPatientFromFireBase(MedicationPOJO medicationPOJO){

    }


}
