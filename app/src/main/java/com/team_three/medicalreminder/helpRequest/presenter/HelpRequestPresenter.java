package com.team_three.medicalreminder.helpRequest.presenter;

import android.content.Context;

import com.team_three.medicalreminder.helpRequest.view.HelpRequestViewInterface;
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
    public void onAccept(TakerPOJO takerPOJO) {
        repository.setMyDelegation(this);
        repository.onAccept(takerPOJO);
    }

    @Override
    public void onReject(String key) {

    }


    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(String errorMessage) {

    }

    @Override
    public void onSuccessRequest(List<RequestPojo> requestPojos) {
        helpRequestViewInterface.loadHelpRequest(requestPojos);
    }

    @Override
    public void onSuccessTaker(List<TakerPOJO> takerPOJOS) {

    }
}
