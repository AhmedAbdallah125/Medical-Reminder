package com.team_three.medicalreminder.Registeration.presenter;

import android.app.Activity;

import com.team_three.medicalreminder.Registeration.view.NetworkViewInterface;
import com.team_three.medicalreminder.model.PatientPojo;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.model.RequestPojo;
import com.team_three.medicalreminder.model.TakerPOJO;
import com.team_three.medicalreminder.network.NetworkDelegation;

import java.util.List;

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
    public void registerWithEmailAndPass(Activity activity, String email, String password, String name) {
        myRepository.registerWithEmailAndPass(activity, email, password, name);
    }

    @Override
    public void signInWithEmailAndPass(Activity activity, String email, String password) {
        myRepository.signInWithEmailAndPass(activity, email, password);
    }

    @Override
    public void isAlreadySignedWithGoogle(String email) {
        myRepository.isSignedWithGoogle(email);
    }

    @Override
    public void signWithGoogle(String idToken) {
        myRepository.signInUsingGoogle(idToken);
    }

    @Override
    public void getUserFromDB(String email) {
        myRepository.getUserName(email);
    }


    @Override
    public void onSuccess() {
        myView.setSuccessfulResponse();
    }

    @Override
    public void onFailure(String errorMessage) {
        myView.setFailureResponse(errorMessage);

    }

    @Override
    public void onSuccessRequest(List<RequestPojo> requestPojos) {

    }

    @Override
    public void onSuccessTaker(List<TakerPOJO> takerPOJOS) {

    }

    @Override
    public void onSuccess(boolean response) {
      myView.setResponse(response);
    }

    @Override
    public void onSuccessPatient(List<PatientPojo> patientPojos) {

    }

    @Override
    public void onSuccessReturn(String userName) {
        myView.setSuccessfulReturnResponse(userName);
    }


}
