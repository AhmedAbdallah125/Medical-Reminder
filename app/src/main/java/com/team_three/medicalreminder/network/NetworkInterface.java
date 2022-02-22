package com.team_three.medicalreminder.network;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;
import com.team_three.medicalreminder.model.User;

public interface NetworkInterface {
    void setNetworkDelegation(NetworkDelegation networkDelegation);

    void isSignedIn();

    void registerWithEmailAndPass(Activity activity, String email, String password,String name);

    void signInWithEmailAndPass(Activity activity, String email, String password);

    void signInUsingGoogle(String idToken);

    FirebaseUser getCurrentUser();

     void addUserInDB(User user);

     void getUserFromRealDB(String email);

}
