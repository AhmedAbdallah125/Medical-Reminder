package com.team_three.medicalreminder.medicationList.presenter;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.team_three.medicalreminder.medicationList.view.InactiveViewInterface;

import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.RepositoryInterface;

import java.util.List;

public class InactivePresenter implements InactivePresenterInterface{
    private InactiveViewInterface _inactiveView;
    private RepositoryInterface _repo;
    public InactivePresenter(InactiveViewInterface _inactiveView , RepositoryInterface _repo){
        this._inactiveView =_inactiveView;
        this._repo = _repo;
    }
    @Override
    public void getInactiveMeds(LifecycleOwner owner) {
        _repo.getInactiveMedications().observe(owner, new Observer<List<MedicationPOJO>>() {
            @Override
            public void onChanged(List<MedicationPOJO> movieDAOS) {
                _inactiveView.getInactiveMeds(movieDAOS);
            }
        });
    }
}
