package com.team_three.medicalreminder.model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;
import com.team_three.medicalreminder.dataBase.LocalSourceInterface;
import com.team_three.medicalreminder.network.NetworkDelegation;
import com.team_three.medicalreminder.network.NetworkInterface;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class Repository implements RepositoryInterface {
    private Context context;
    LocalSourceInterface localSourceInterface;
    private static Repository repo = null;

    // for firebase
    NetworkInterface myRemote;
    private static Repository myRepositoryNetwork = null;
    private NetworkDelegation myDelegation;
    // for two
    private static Repository myRepositoryAll = null;

    private Repository(LocalSourceInterface localSourceInterface, Context context) {
        this.context = context;
        this.localSourceInterface = localSourceInterface;
    }

    public static Repository getInstance(LocalSourceInterface localSourceInterface, Context context) {
        if (repo == null) {
            repo = new Repository(localSourceInterface, context);
        }
        return repo;
    }

    // for fireBase
    private Repository(NetworkInterface myRemote, Context context) {
        this.context = context;
        this.myRemote = myRemote;
    }

    public static Repository getInstance(NetworkInterface myRemote, Context context) {
        if (myRepositoryNetwork == null) {
            myRepositoryNetwork = new Repository(myRemote, context);
        }
        return myRepositoryNetwork;
    }

    // for two DataSources
    // for fireBase
    private Repository(LocalSourceInterface localSourceInterface, NetworkInterface myRemote, Context context) {
        this.localSourceInterface = localSourceInterface;
        this.context = context;
        this.myRemote = myRemote;
    }

    public static Repository getInstance(LocalSourceInterface localSourceInterface, NetworkInterface myRemote, Context context) {
        if (myRepositoryAll == null) {
            myRepositoryAll = new Repository(localSourceInterface, myRemote, context);
        }
        return myRepositoryAll;
    }

    public void setMyDelegation(NetworkDelegation myDelegation) {
        this.myDelegation = myDelegation;
        myRemote.setNetworkDelegation(myDelegation);

    }

    @Override
    public LiveData<List<MedicationPOJO>> getAllMedication() {

        return localSourceInterface.getAllMedication();
    }

    @Override
    public void insertMedication(MedicationPOJO medication) {
        localSourceInterface.insertMedication(medication);
        Log.i("TAG", "insertMedication: ");
    }

    @Override
    public void deleteMedication(MedicationPOJO medication) {
        localSourceInterface.deleteMedication(medication);
    }

    @Override
    public LiveData<MedicationPOJO> getMedications(int id) {
        return localSourceInterface.getMedications(id);
    }

    @Override
    public void updateMedications(MedicationPOJO medicationPOJO) {
        localSourceInterface.updateMedications(medicationPOJO);
    }

    @Override
    public LiveData<List<MedicationPOJO>> getActiveMedications() {
        return localSourceInterface.getActiveMedications();
    }

    @Override
    public LiveData<List<MedicationPOJO>> getInactiveMedications() {
        return localSourceInterface.getInactiveMedications();
    }

    @Override
    public LiveData<List<MedicationPOJO>> getMedicationDay(long time) {
        return localSourceInterface.getMedicationDay(time);
    }

    // for firebase
    @Override
    public void isSignedIn() {
        myRemote.isSignedIn();
    }


    @Override
    public void registerWithEmailAndPass(Activity activity, String email, String password, String name) {
        myRemote.registerWithEmailAndPass(activity, email, password, name);

    }

    @Override
    public void signInWithEmailAndPass(Activity activity, String email, String password) {
        myRemote.signInWithEmailAndPass(activity, email, password);
    }


    @Override
    public void isSignedWithGoogle(String email) {
        myRemote.tryLoginGoogle(email);
    }

    @Override
    public void signInUsingGoogle(String idToken) {
        myRemote.signInUsingGoogle(idToken);
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return myRemote.getCurrentUser();
    }

    @Override
    public void getUserName(String email) {
        myRemote.getUserFromRealDB(email);

    }

    @Override
    public Single<List<MedicationPOJO>> getMedicationDayWorkManger(long time) {
        return localSourceInterface.getMedicationDayWorkManger(time);
    }

    @Override
    public Single<List<MedicationPOJO>> getRefilReminderList(long time) {
        return localSourceInterface.getRefilReminderList(time);
    }

    @Override
    public void UserExistance(String email) {
        myRemote.UserExistance(email);
    }

    @Override
    public void deleteTaker(String takerEmail, String patientEmail) {
        myRemote.deleteTaker(takerEmail, patientEmail);
    }

    @Override
    public void updateToRoomFromFirebase(List<MedicationPOJO> medications) {
        for (MedicationPOJO medication : medications) {
            localSourceInterface.updateMedications(medication);
        }
    }


    @Override
    public void sendRequest(RequestPojo requestPojo) {
        myRemote.sendRequest(requestPojo);
    }

    @Override
    public void loadHelpRequest(String email) {
        myRemote.loadHelpRequest(email);
    }

    @Override
    public void onAccept(TakerPOJO takerPOJO, PatientPojo patientPojo) {
        myRemote.onAccept(takerPOJO, patientPojo);
    }

    @Override
    public void onReject(String key, String email) {
        myRemote.onReject(key, email);
    }

    @Override
    public void loadPatients(String email) {
        myRemote.loadPatients(email);
    }

    @Override
    public void loadTakers(String email) {

        myRemote.loadTakers(email);
    }

    //add med to firebase
    @Override
    public void addMedicationListViaNetwork(List<MedicationPOJO> medicationPOJOS, String email) {
// try one
        myRemote.addMedicationListViaNetwork(medicationPOJOS, email);
    }

    @Override
    public void deleteInPatientMedicationList(String email, String medicationID) {
        myRemote.deleteInPatientMedicationList(email, medicationID);
    }
}
