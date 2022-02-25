package com.team_three.medicalreminder.helpRequest.presenter;

import android.content.Context;

import com.team_three.medicalreminder.helpRequest.view.HelpRequestViewInterface;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.PatientPojo;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.model.RequestPojo;
import com.team_three.medicalreminder.model.TakerPOJO;
import com.team_three.medicalreminder.network.NetworkDelegation;

import java.util.List;

public class HelpRequestPresenter implements HelpRequestPresenterInterface , NetworkDelegation {
    Context context;
    Repository repository;
    HelpRequestViewInterface helpRequestViewInterface;
    String email;
    public HelpRequestPresenter(Context context, Repository repository, HelpRequestViewInterface helpRequestViewInterface) {
        this.context = context;
        this.repository = repository;
        this.helpRequestViewInterface = helpRequestViewInterface;
    }


    @Override
    public void loadHelpRequest() {
        repository.setMyDelegation(this);
        repository.loadHelpRequest(email);
        //
    }

    @Override
    public void sendEmail(String email) {
        this.email=email;
    }

    @Override
    public void onAccept(TakerPOJO takerPOJO, PatientPojo patientPojo) {
        repository.setMyDelegation(this);
        repository.onAccept(takerPOJO,patientPojo);
    }

    @Override
    public void onReject(String key,String email) {
        repository.onReject(key,email);
    }


    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(String errorMessage) {

    }

    @Override
    public void onSuccessReturn(String userName) {

    }

    @Override
    public void onSuccessRequest(List<RequestPojo> requestPojos) {
        helpRequestViewInterface.loadHelpRequest(requestPojos);
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

    @Override
    public void isUserExist(boolean existance) {

    }

    @Override
    public void onUpdateMedicationFromFirebase(List<MedicationPOJO> medications) {

    }
}
