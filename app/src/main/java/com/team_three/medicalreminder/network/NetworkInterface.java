package com.team_three.medicalreminder.network;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.PatientPojo;
import com.team_three.medicalreminder.model.RequestPojo;
import com.team_three.medicalreminder.model.TakerPOJO;

import java.util.List;

import com.team_three.medicalreminder.model.User;

public interface NetworkInterface {
    void setNetworkDelegation(NetworkDelegation networkDelegation);

    void isSignedIn();

    void registerWithEmailAndPass(Activity activity, String email, String password, String name);

    void signInWithEmailAndPass(Activity activity, String email, String password);

    void tryLoginGoogle(String email);

    void signInUsingGoogle( String idToken);

    FirebaseUser getCurrentUser();

    void addUserInDB(User user);

    void getUserFromRealDB(String email);

    void sendRequest(RequestPojo requestPojo);

    void loadHelpRequest(String email);

    void onAccept(TakerPOJO takerPOJO, PatientPojo patientPojo);

    void onReject(String key,String email);

    void loadPatients(String email);

    void loadTakers(String email);
    void loadPatientMedicationList(String email);
    void addMedicationListViaNetwork(List<MedicationPOJO> medicationPOJOS,String email);
    void UserExistance(String email);
    public void deleteTaker(String takerEmail,String patientEmail);

    void deleteInPatientMedicationList(String email, String medicationID);
}
