package com.team_three.medicalreminder.login.presenter;

import android.app.Activity;

import com.team_three.medicalreminder.login.view.LoginViewInterface;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.network.NetworkDelegation;

public class LoginPresenter implements LoginPresenterInterface, NetworkDelegation {

    private Activity myActivity;
    private LoginViewInterface myView;
    private Repository myRepository;

    public LoginPresenter(Activity myActivity, LoginViewInterface myView, Repository myRepository) {
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
    public void onSuccess(boolean response) {
        myView.setResponse(response);
    }

    @Override
    public void onFailure(boolean response) {
        myView.setResponse(response);

    }
}
