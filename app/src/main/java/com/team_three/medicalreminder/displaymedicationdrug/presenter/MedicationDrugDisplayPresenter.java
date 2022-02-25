package com.team_three.medicalreminder.displaymedicationdrug.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.team_three.medicalreminder.Registeration.view.RegisterFragment;
import com.team_three.medicalreminder.dataBase.ConcreteLocalClass;
import com.team_three.medicalreminder.displaymedicationdrug.view.DisplayMedicationDrugViewInterface;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.PatientPojo;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.model.RequestPojo;
import com.team_three.medicalreminder.model.TakerPOJO;
import com.team_three.medicalreminder.network.FireBaseNetwork;
import com.team_three.medicalreminder.network.NetworkDelegation;

import java.util.List;

public class MedicationDrugDisplayPresenter implements MedicationDrugDisplayPresenterInterface, NetworkDelegation {

    private Repository repository;
    private DisplayMedicationDrugViewInterface viewInterface;
    private Context context;
    SharedPreferences sharedPref;
    String name = "";
    String email = "";

    public MedicationDrugDisplayPresenter(Repository repository, DisplayMedicationDrugViewInterface viewInterface,Context context) {
        this.viewInterface = viewInterface;
        this.repository = repository;
        this.context=context;
    }

    @Override
    public void deleteMedication(MedicationPOJO medication) {
        repository.deleteMedication(medication);
        if(!isSharedPrefNull()) {
            repository.setMyDelegation(this);
            repository.deleteInPatientMedicationList(email, String.valueOf(medication.getId()));
        }
    }

    @Override
    public void updateMedication(MedicationPOJO medication) {
        repository.updateMedications(medication);
    }

    @Override
    public void deleteMedicationInFireBase(MedicationPOJO medication,String email) {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(String errorMessage) {

    }

    @Override
    public void onSuccessReturn(String userName) {

    }

    @Override
    public void onSuccessRequest(List<RequestPojo> requestPojos) {

    }

    @Override
    public void onSuccessTaker(List<TakerPOJO> takerPOJOS) {

    }

    @Override
    public void onSuccess(boolean response) {

    }

    @Override
    public void onSuccessPatient(List<PatientPojo> patientPojos) {

    }

    private boolean isSharedPrefNull() {
        sharedPref = context.getSharedPreferences(RegisterFragment.SHAREDfILE, Context.MODE_PRIVATE);
        email = sharedPref.getString(RegisterFragment.USER_EMAIL, "null");
        name = sharedPref.getString(RegisterFragment.USER_NAME, "null");
        Log.i("TAG", "checkShared: " + name);
        Log.i("TAG", "checkShared: " + email);
        return (name.equals("null") && email.equals("null"));
    }

}
