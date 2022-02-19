package com.team_three.medicalreminder.model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.team_three.medicalreminder.dataBase.LocalSourceInterface;
import com.team_three.medicalreminder.medicationList.view.AcrtiveMedsFragment;
import com.team_three.medicalreminder.network.NetworkDelegation;
import com.team_three.medicalreminder.network.NetworkInterface;

import java.util.List;

public class Repository implements RepositoryInterface {
    private Context context;
    LocalSourceInterface localSourceInterface;
    private static Repository repo = null;



    // for firebase
    NetworkInterface myRemote;
    private static Repository myRepositoryNetwork = null;
    private NetworkDelegation myDelegation;

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
    public void isSignedIn() {
        myRemote.isSignedIn();
    }

    @Override
    public void registerWithEmailAndPass(Activity activity, String email, String password) {
        myRemote.registerWithEmailAndPass(activity,email,password);

    }

    @Override
    public void signInWithEmailAndPass(Activity activity, String email, String password) {
                myRemote.signInWithEmailAndPass(activity,email,password);
    }

    @Override
    public LiveData<List<MedicationPOJO>> getMedicationDay(long time) {
        return localSourceInterface.getMedicationDay(time);
    }
}
