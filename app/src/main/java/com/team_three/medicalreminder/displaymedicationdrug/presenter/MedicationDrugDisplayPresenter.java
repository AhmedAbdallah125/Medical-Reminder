package com.team_three.medicalreminder.displaymedicationdrug.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.team_three.medicalreminder.Registeration.view.RegisterFragment;
import com.team_three.medicalreminder.displaymedicationdrug.view.DisplayMedicationDrugViewInterface;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;

public class MedicationDrugDisplayPresenter implements MedicationDrugDisplayPresenterInterface {

    private Repository repository;
    private DisplayMedicationDrugViewInterface viewInterface;
    private Context context;
    SharedPreferences sharedPref;
    String name = "";
    String email = "";

    public MedicationDrugDisplayPresenter(Repository repository, DisplayMedicationDrugViewInterface viewInterface, Context context) {
        this.viewInterface = viewInterface;
        this.repository = repository;
        this.context = context;
    }

    @Override
    public void deleteMedication(MedicationPOJO medication) {
        repository.deleteMedication(medication);
        if (!isSharedPrefNull()) {
            repository.deleteInPatientMedicationList(email, String.valueOf(medication.getId()));
        }
    }

    @Override
    public void updateMedication(MedicationPOJO medication) {
        repository.updateMedications(medication);
        if(!isSharedPrefNull()){
            repository.updatePatientMedicationList(email,medication);
        }
    }

    @Override
    public void deleteMedicationInFireBase(MedicationPOJO medication, String email) {

    }

    private boolean isSharedPrefNull() {
        sharedPref = context.getSharedPreferences(RegisterFragment.SHAREDfILE, Context.MODE_PRIVATE);
        email = sharedPref.getString(RegisterFragment.USER_EMAIL, "null");
        name = sharedPref.getString(RegisterFragment.USER_NAME, "null");
        return (name.equals("null") && email.equals("null"));
    }

}
