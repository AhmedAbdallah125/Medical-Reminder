package com.team_three.medicalreminder.addmedication.presenter;

import android.content.Context;
import android.util.Log;

import com.team_three.medicalreminder.addmedication.view.AddAndEditMedicationInterface;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.model.Utility;

public class AddMedicationPresenter implements AddMedicationPresenterInterface{
    private Repository repository;
    private AddAndEditMedicationInterface viewInterface;
    private Context context;
    public AddMedicationPresenter(Repository repository, AddAndEditMedicationInterface viewInterface, Context context) {
        this.repository = repository;
        this.viewInterface = viewInterface;
        this.context = context;
    }

    @Override
    public void updateToDatabase(MedicationPOJO medication) {
        repository.updateMedications(medication);
        checkUpdateToFirebase(medication);
    }

    @Override
    public void addToDatabase(MedicationPOJO medication) {
        repository.insertMedication(medication);
        checkUpdateToFirebase(medication);
    }

    private void checkUpdateToFirebase(MedicationPOJO medication){
        String email = Utility.checkShared(context);
        if(!email.equals("null")){
            repository.updatePatientMedicationList(email,medication);
        }
    }

//    @Override
//    public MedicationPOJO showMedication() {
//        return null;
//    }
}
