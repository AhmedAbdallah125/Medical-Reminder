package com.team_three.medicalreminder.network;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;
import com.team_three.medicalreminder.model.RequestPojo;
import com.team_three.medicalreminder.model.TakerPOJO;

import java.util.List;

public interface NetworkInterface {
    void setNetworkDelegation(NetworkDelegation networkDelegation);

    void isSignedIn();

    void registerWithEmailAndPass(Activity activity, String email, String password);

    void signInWithEmailAndPass(Activity activity, String email, String password);

    void signInUsingGoogle(String idToken);

    FirebaseUser getCurrentUser();

    void sendRequest(RequestPojo requestPojo);

    void loadHelpRequest(String email);

    void onAccept(TakerPOJO takerPOJO);
    void  onReject(String key);
    void loadPatients(String email);
    void loadTakers(String email);



}
