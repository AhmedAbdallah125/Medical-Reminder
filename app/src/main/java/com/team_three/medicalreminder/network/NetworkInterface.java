package com.team_three.medicalreminder.network;

import android.app.Activity;
import android.content.Context;

import com.google.firebase.auth.FirebaseUser;

public interface NetworkInterface {
    void setNetworkDelegation(NetworkDelegation networkDelegation);
    void isSignedIn();

    void registerWithEmailAndPass(Activity activity, String email, String password);

    void signInWithEmailAndPass(Activity activity, String email, String password);
}
