package com.team_three.medicalreminder.Registeration.presenter;

import android.app.Activity;

public interface NetworkPresenterInterface {
    // fireBase
    void isSignedIn();

    void registerWithEmailAndPass(Activity activity, String email, String password, String name);

    void signInWithEmailAndPass(Activity activity, String email, String password);

    void isAlreadySignedWithGoogle(String email);
    void signWithGoogle(String idToken);

    void getUserFromDB(String email);

}
