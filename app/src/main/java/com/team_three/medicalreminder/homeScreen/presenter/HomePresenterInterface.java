package com.team_three.medicalreminder.homeScreen.presenter;

import androidx.lifecycle.LifecycleOwner;

public interface HomePresenterInterface {
    void getMedicationDay(LifecycleOwner lifecycleOwner, long time);
    void updatePosition(int position);

}
