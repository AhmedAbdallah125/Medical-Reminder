package com.team_three.medicalreminder.medicationPatient.view;

import com.team_three.medicalreminder.model.MedicationPOJO;

import java.util.List;

public interface PatientMedViewInterface {

    void onSuccessReturnMedicationList(List<MedicationPOJO> medicationPOJOList);


    void onFailure(String errorMessage);


}
