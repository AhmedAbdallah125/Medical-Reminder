package com.team_three.medicalreminder.taker.presenter;

import android.content.Context;

import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.model.TakerPOJO;

public class TakerProfilePresenter implements TakerProfilePresenterInterface{
    Context context;
    Repository repository;

    public TakerProfilePresenter(Context context, Repository repository) {
        this.context = context;
        this.repository = repository;
    }

    @Override
    public void deleteTaker(String takerEmail,String patientEmail) {
        repository.deleteTaker(takerEmail,patientEmail);
    }
}
