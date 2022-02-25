package com.team_three.medicalreminder.model;

import android.app.Activity;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

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

    void registerWithEmailAndPass(Activity activity, String email, String password,String name);

    void signInWithEmailAndPass(Activity activity, String email, String password);

    void signInUsingGoogle(String idToken);
    void isSignedWithGoogle(String email);
    FirebaseUser getCurrentUser();
    void getUserName(String email);

     void sendRequest(RequestPojo requestPojo);
     void loadHelpRequest(String email);
    void onAccept(TakerPOJO takerPOJO,PatientPojo patientPojo);
    void  onReject(String key,String email);
    void loadPatients(String email);
    void loadTakers(String email);
//add med to fireBase
    void addMedicationListViaNetwork(List<MedicationPOJO> medicationPOJOS,String email);



    Single<List<MedicationPOJO>> getMedicationDayWorkManger(long time);

    Single<List<MedicationPOJO>> getRefilReminderList(long time);

    void UserExistance(String email);



}
