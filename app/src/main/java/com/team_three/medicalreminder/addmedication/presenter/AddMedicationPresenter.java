package com.team_three.medicalreminder.addmedication.presenter;

import android.util.Log;

import com.team_three.medicalreminder.addmedication.view.AddAndEditMedicationInterface;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;

public class AddMedicationPresenter implements AddMedicationPresenterInterface{
    private Repository repository;
    private AddAndEditMedicationInterface viewInterface;

    public AddMedicationPresenter(Repository repository, AddAndEditMedicationInterface viewInterface) {
        this.repository = repository;
        this.viewInterface = viewInterface;
    }

    @Override
    public void updateToDatabase(MedicationPOJO medication) {
        repository.updateMedications(medication);
    }

    @Override
    public void addToDatabase(MedicationPOJO medication) {
        repository.insertMedication(medication);
        Log.i("TAG", "addToDatabase: ");
    }

    @Override
    public MedicationPOJO showMedication() {
        return null;
    }
}
