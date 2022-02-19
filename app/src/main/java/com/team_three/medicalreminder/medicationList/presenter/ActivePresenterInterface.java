package com.team_three.medicalreminder.medicationList.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.team_three.medicalreminder.model.MedicationPOJO;

import java.util.List;

public interface ActivePresenterInterface {
    public void getActiveMeds(LifecycleOwner owner);
}
