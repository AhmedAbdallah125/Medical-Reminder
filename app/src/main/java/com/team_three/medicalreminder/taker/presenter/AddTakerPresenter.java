package com.team_three.medicalreminder.taker.presenter;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team_three.medicalreminder.model.PatientPojo;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.model.RequestPojo;
import com.team_three.medicalreminder.model.TakerPOJO;
import com.team_three.medicalreminder.network.NetworkDelegation;
import com.team_three.medicalreminder.taker.view.AddTakerViewInterface;

import java.util.List;

public class AddTakerPresenter implements AddTakerPresenerInterface, NetworkDelegation {
    Context context;
    AddTakerViewInterface addTakerViewInterface;
    Repository repository;

    public AddTakerPresenter(Context context, AddTakerViewInterface addTakerViewInterface, Repository repository) {
        this.context = context;
        this.addTakerViewInterface = addTakerViewInterface;
        this.repository = repository;
        this.repository.setMyDelegation(this);
    }

    @Override
    public void sendRequest(RequestPojo requestPojo) {
        repository.sendRequest(requestPojo);
    }

    @Override
    public void isLoggedIn() {
        repository.isSignedIn();
    }

    @Override
    public void onSuccess() {
        addTakerViewInterface.isLogedIn(true);
    }

    @Override
    public void onFailure(String errorMessage) {
        addTakerViewInterface.isLogedIn(false);
    }

    @Override
    public void onSuccessReturn(String userName) {

    }

    @Override
    public void onSuccessRequest(List<RequestPojo> requestPojos) {

    }

    @Override
    public void onSuccessTaker(List<TakerPOJO> takerPOJOS) {

    }

    @Override
    public void onSuccess(boolean response) {

    }

    @Override
    public void onSuccessPatient(List<PatientPojo> patientPojos) {

    }
}
