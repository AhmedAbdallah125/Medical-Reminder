package com.team_three.medicalreminder.homeScreen.presenter;

import android.util.Log;
import android.widget.Toast;

import com.team_three.medicalreminder.homeScreen.view.HomeFragmentInterface;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;

import java.nio.file.ClosedFileSystemException;
import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public class HomeScreenPresenter implements HomePresenterInterface {

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
                Log.i("TAG", "onChanged: "+medicationPOJOS.size());
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
        timeSaved=time;
    }

    @Override
    public void addMedicationListViaNetwork(List<MedicationPOJO> medicationPOJOS, String email) {
        myRepository.addMedicationListViaNetwork(medicationPOJOS,email);
    }
    // handling delete
    @Override
    public void deleteMedication(MedicationPOJO medicationPOJO) {
        myRepository.deleteMedication(medicationPOJO);
    }


}
