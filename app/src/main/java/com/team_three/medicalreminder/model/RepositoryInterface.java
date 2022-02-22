package com.team_three.medicalreminder.model;

import android.app.Activity;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public interface RepositoryInterface {
    LiveData<List<MedicationPOJO>> getAllMedication();

    void insertMedication(MedicationPOJO medication);

    void deleteMedication(MedicationPOJO medication);

    LiveData<List<MedicationPOJO>> getMedicationDay(long time);


    LiveData<MedicationPOJO> getMedications(int id);

    void updateMedications(MedicationPOJO medicationPOJO);

    LiveData<List<MedicationPOJO>> getActiveMedications();

    LiveData<List<MedicationPOJO>> getInactiveMedications();

    // fireBase
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
