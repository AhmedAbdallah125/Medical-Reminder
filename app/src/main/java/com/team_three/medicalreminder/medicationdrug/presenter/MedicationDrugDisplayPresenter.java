package com.team_three.medicalreminder.medicationdrug.presenter;

import com.team_three.medicalreminder.medicationdrug.view.DisplayMedicationDrugViewInterface;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;

public class MedicationDrugDisplayPresenter implements MedicationDrugDisplayPresenterInterface {

    private Repository repository;
    private DisplayMedicationDrugViewInterface viewInterface;

    public MedicationDrugDisplayPresenter(Repository repository, DisplayMedicationDrugViewInterface viewInterface) {
        this.repository = repository;
        this.viewInterface = viewInterface;
    }

    @Override
    public void deleteMedication(MedicationPOJO medication) {
        repository.deleteMedication(medication);
    }

    @Override
    public void updateMedication(MedicationPOJO medication) {
        repository.updateMedications(medication);
    }
}
