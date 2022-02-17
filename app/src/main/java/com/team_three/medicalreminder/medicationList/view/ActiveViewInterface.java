package com.team_three.medicalreminder.medicationList.view;

import com.team_three.medicalreminder.model.MedicationPOJO;

import java.util.List;

public interface ActiveViewInterface {
    public void getActiveMeds(List<MedicationPOJO> medications);

}
