package com.team_three.medicalreminder.homeScreen.presenter;

import android.util.Log;
import android.widget.Toast;

import com.team_three.medicalreminder.homeScreen.view.HomeFragmentInterface;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.PatientPojo;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.model.RequestPojo;
import com.team_three.medicalreminder.model.TakerPOJO;
import com.team_three.medicalreminder.network.NetworkDelegation;

import java.nio.file.ClosedFileSystemException;
import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public class HomeScreenPresenter implements HomePresenterInterface, NetworkDelegation {

    // reference to view
//    private FavouriteViewInterface myView;
    // reference to Repo
    private Repository myRepository;
    private HomeFragmentInterface myView;
    private LiveData<List<MedicationPOJO>> storedMedications;
    static long timeSaved;
    public static int position;

    public HomeScreenPresenter(HomeFragmentInterface myView, Repository myRepository) {
        this.myView = myView;
        this.myRepository = myRepository;
        myRepository.setMyDelegation(this);

    }

    @Override
    public void deleteMedication(String email, String medicationId) {
        myRepository.deleteInPatientMedicationList(email, medicationId);
    }

    @Override
    public void getMedicationDay(LifecycleOwner owner, long time) {
        // maybe cause
        if (time == 0) {
            time = timeSaved;
        }
//        myRepository.getAllMedication().observe(owner, new Observer<List<MedicationPOJO>>() {
//            @Override
//            public void onChanged(List<MedicationPOJO> medicationPOJOS) {
//                myView.showMedications(medicationPOJOS);
//            }
//        });
        myRepository.getMedicationDay(time).observe(owner, new Observer<List<MedicationPOJO>>() {
            @Override
            public void onChanged(List<MedicationPOJO> medicationPOJOS) {
                // call to update there
                Log.i("TAG", "onChanged: " + medicationPOJOS.size());
                myView.showMedications(medicationPOJOS);
            }
        });
    }

    @Override
    public void updatePosition(int p) {
        position = p;
    }

    @Override
    public void updateTime(long time) {
        timeSaved = time;
    }

    @Override
    public void addMedicationListViaNetwork(List<MedicationPOJO> medicationPOJOS, String email) {
        myRepository.addMedicationListViaNetwork(medicationPOJOS, email);
    }

    @Override
    public void notifyMedicationChangeFromFirebase(String email) {
        myRepository.setMyDelegation(this);
        myRepository.notifyMedicationChangeFromFirebase(email);
    }

    // handling delete
    @Override
    public void deleteMedication(MedicationPOJO medicationPOJO) {
        myRepository.deleteMedication(medicationPOJO);
    }

    @Override
    public void updateMedication(MedicationPOJO medicationPOJO) {
        myRepository.updateMedications(medicationPOJO);
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

    @Override
    public void isUserExist(boolean existance) {

    }

    @Override
    public void onUpdateMedicationFromFirebase(List<MedicationPOJO> medications) {
        Log.i("ahmed", "onUpdateMedicationFromFirebase: ");
        myRepository.updateToRoomFromFirebase(medications);
    }

    @Override
    public void onSuccessReturnMedicationList(List<MedicationPOJO> medicationPOJOList) {

    }
}
