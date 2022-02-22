package com.team_three.medicalreminder.patientsList.presenter;

import android.content.Context;

import com.team_three.medicalreminder.helpRequest.view.HelpRequestViewInterface;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.model.RequestPojo;
import com.team_three.medicalreminder.model.TakerPOJO;
import com.team_three.medicalreminder.network.NetworkDelegation;
import com.team_three.medicalreminder.patientsList.view.PatientListViewInterface;

import java.util.List;

public class PatientListPresenter implements  PatientListPresenterInterface , NetworkDelegation {
    Context context;
    Repository repository;
    PatientListViewInterface listViewInterface;
    String email;
    public PatientListPresenter(Context context, Repository repository, PatientListViewInterface listViewInterface){
        this.context = context;
        this.repository=repository;
        this.listViewInterface = listViewInterface;
    }
    @Override
    public void loadPatients() {
        repository.setMyDelegation(this);
        repository.loadTakers(email);
    }

    @Override
    public void sendEmail(String email) {
        email=this.email;
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(String errorMessage) {

    }

    @Override
    public void onSuccessRequest(List<RequestPojo> requestPojos) {
        listViewInterface.loadPatients(requestPojos);
    }

    @Override
    public void onSuccessTaker(List<TakerPOJO> takerPOJOS) {

    }
}
