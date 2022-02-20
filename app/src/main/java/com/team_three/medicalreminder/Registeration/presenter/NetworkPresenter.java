package com.team_three.medicalreminder.Registeration.presenter;

import android.app.Activity;

import com.team_three.medicalreminder.Registeration.view.NetworkViewInterface;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.network.NetworkDelegation;

public class NetworkPresenter implements NetworkPresenterInterface, NetworkDelegation {

    private Activity myActivity;
    private NetworkViewInterface myView;
    private Repository myRepository;

    public NetworkPresenter(Activity myActivity, NetworkViewInterface myView, Repository myRepository) {
        this.myActivity = myActivity;
        this.myView = myView;
        this.myRepository = myRepository;
        myRepository.setMyDelegation(this);
    }


    @Override
    public void isSignedIn() {
        myRepository.isSignedIn();
    }

    @Override
    public void registerWithEmailAndPass(Activity activity, String email, String password) {
        myRepository.registerWithEmailAndPass(activity, email, password);
    }

    @Override
    public void signInWithEmailAndPass(Activity activity, String email, String password) {
        myRepository.signInWithEmailAndPass(activity, email, password);
    }


    @Override
    public void onSuccess() {
        myView.setSuccessfulResponse();
    }

    @Override
    public void onFailure(String errorMessage) {
        myView.setFailureResponse(errorMessage);

    }
}
