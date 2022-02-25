package com.team_three.medicalreminder.medicationPatient.presenter;

import androidx.lifecycle.LiveData;

import com.team_three.medicalreminder.homeScreen.view.HomeFragmentInterface;
import com.team_three.medicalreminder.medicationPatient.view.PatientMedViewInterface;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.PatientPojo;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.model.RequestPojo;
import com.team_three.medicalreminder.model.TakerPOJO;
import com.team_three.medicalreminder.network.NetworkDelegation;

import java.util.List;

public class PatientMedPresenter implements PatientMedPresenterInterface, NetworkDelegation {
    // reference to view
//    private FavouriteViewInterface myView;
    // reference to Repo
    private Repository myRepository;
    private PatientMedViewInterface myView;
    //    private LiveData<List<MedicationPOJO>> storedMedications;
    static long timeSaved;
    public static int position;

    public PatientMedPresenter(PatientMedViewInterface myView, Repository myRepository) {
        this.myView = myView;
        this.myRepository = myRepository;
    }


    @Override
    public void getPatientMedicationList(String email) {
        myRepository.loadMedicationListOFPatient(email);
    }


    @Override
    public void onSuccessReturnMedicationList(List<MedicationPOJO> medicationPOJOList) {
        myView.onSuccessReturnMedicationList(medicationPOJOList);
    }

    @Override
    public void onFailure(String errorMessage) {
        myView.onFailure(errorMessage);
    }

    @Override
    public void onSuccess() {

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

    @Override
    public void isUserExist(boolean existance) {

    }


}
