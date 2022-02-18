package com.team_three.medicalreminder.medicationList.presenter;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.team_three.medicalreminder.medicationList.view.ActiveViewInterface;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.RepositoryInterface;

import java.util.List;

public class ActivePresenter implements ActivePresenterInterface{
    private ActiveViewInterface _ActiveView;
    private RepositoryInterface _repo;
    public  ActivePresenter(ActiveViewInterface _ActiveView,RepositoryInterface _repo){
        this._ActiveView = _ActiveView;
        this._repo = _repo;
    }

    @Override
    public void getActiveMeds(LifecycleOwner owner) {
        _repo.getActiveMedications().observe(owner, new Observer<List<MedicationPOJO>>() {
            @Override
            public void onChanged(List<MedicationPOJO> movieDAOS) {
                _ActiveView.getActiveMeds(movieDAOS);
            }
        });
    }
}
