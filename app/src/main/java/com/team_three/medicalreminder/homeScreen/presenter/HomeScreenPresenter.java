package com.team_three.medicalreminder.homeScreen.presenter;

import com.team_three.medicalreminder.homeScreen.view.HomeFragmentInterface;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;

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
    private static long timeSaved;
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
        myRepository.getMedicationDay().observe(owner, new Observer<List<MedicationPOJO>>() {
            @Override
            public void onChanged(List<MedicationPOJO> medicationPOJOS) {
                // call to update there
                myView.showMedications(medicationPOJOS);
            }
        });
    }

    @Override
    public void updatePosition(int p) {
        position = p;
    }


}
