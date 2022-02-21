package com.team_three.medicalreminder.network;


import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.team_three.medicalreminder.R;

public class FireBaseNetwork implements NetworkInterface {

    Activity _activity;
    public static FirebaseAuth mAuth;
    private static FireBaseNetwork myFireBase;
    private NetworkDelegation myDelegation;

    private FireBaseNetwork(Activity myActivity) {
        _activity = myActivity;
    }

    public static FireBaseNetwork getInstance(Activity myActivity) {
        if (myFireBase == null) {
            mAuth = FirebaseAuth.getInstance();
            myFireBase = new FireBaseNetwork(myActivity);
        }
        return myFireBase;
    }


    @Override
    public void setNetworkDelegation(NetworkDelegation networkDelegation) {
        myDelegation = networkDelegation;
    }

    @Override
    public void isSignedIn() {
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            reload();
//        }
        if (currentUser != null) {

            myDelegation.onSuccess();
        } else {
            myDelegation.onFailure("no available");
        }
    }


    @Override
    public void registerWithEmailAndPass(Activity myActivity, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(myActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            myDelegation.onSuccess();
                        } else {
                            String errorMessage = handleFireBaseException(task);
                            myDelegation.onFailure(errorMessage);
                        }
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            myDelegation.onSuccess(true);
////                            updateUI(user);
//                        }
                    }
                });

    }


    @Override
    public void signInWithEmailAndPass(Activity activity, String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            myDelegation.onSuccess();
                        } else {
                            String errorMessage = handleFireBaseException(task);
                            myDelegation.onFailure(errorMessage);
                        }
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            FirebaseUser user = mAuth.getCurrentUser();
////                            updateUI(user);
//                            myDelegation.onSuccess(true);
//
//                        }
                    }
                });
    }

    @Override
    public void signInUsingGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(_activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            myDelegation.onSuccess();
                            // Sign in success, update UI with the signed-in user's information
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            updateUI(null);
                        }
                    }
                });
    }

    @Override
    public FirebaseUser getCurrentUser() {
        // check if null or not
        if (mAuth == null) {
            // check
            mAuth = FirebaseAuth.getInstance();
        }
        return mAuth.getCurrentUser();
    }

    private String handleFireBaseException(Task task) {
        String errorMessage = "";

        try {
            throw task.getException();
        } catch (FirebaseAuthWeakPasswordException e) {
            errorMessage = "weak Password";
        } catch (FirebaseAuthUserCollisionException e) {
            errorMessage = "user exists already";
        } catch (FirebaseAuthInvalidUserException e) {
            errorMessage = "problem in e-mail or password";
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }


        return errorMessage;
    }


}
