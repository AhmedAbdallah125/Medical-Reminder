package com.team_three.medicalreminder.login.presenter;

import android.app.Activity;

public interface LoginPresenterInterface {
    // fireBase
    void isSignedIn();

    void registerWithEmailAndPass(Activity activity, String email, String password);

    void signInWithEmailAndPass(Activity activity, String email, String password);
}
