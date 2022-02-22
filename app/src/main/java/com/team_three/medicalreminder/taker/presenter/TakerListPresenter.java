package com.team_three.medicalreminder.taker.presenter;

import android.content.Context;

import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.model.RequestPojo;
import com.team_three.medicalreminder.model.TakerPOJO;
import com.team_three.medicalreminder.network.NetworkDelegation;
import com.team_three.medicalreminder.taker.view.AddTakerViewInterface;
import com.team_three.medicalreminder.taker.view.TakerListViewInterface;

import java.util.List;

public class TakerListPresenter implements TakerLIstPresenterInterface , NetworkDelegation {
    Context context;
    Repository repository;
    TakerListViewInterface takerListViewInterface;
    String email;
    public TakerListPresenter(Context context, Repository repository, TakerListViewInterface takerViewInterface) {
        this.context = context;
        this.repository = repository;
        this.takerListViewInterface = takerViewInterface;
    }

    @Override
    public void loadTakers() {
        repository.setMyDelegation(this);
        repository.loadTakers(email);
    }

    @Override
    public void sendEmail(String email) {
        this.email=email;
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(String errorMessage) {

    }

    @Override
    public void onSuccessRequest(List<RequestPojo> requestPojos) {

    }

    @Override
    public void onSuccessTaker(List<TakerPOJO> takerPOJOS) {
        takerListViewInterface.loadTakers(takerPOJOS);
    }

}
