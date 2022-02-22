package com.team_three.medicalreminder.patientsList.presenter;

import android.content.Context;

import com.team_three.medicalreminder.helpRequest.view.HelpRequestViewInterface;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.model.RequestPojo;
import com.team_three.medicalreminder.patientsList.view.PatientListViewInterface;

import java.util.List;

public class PatientListPresenter implements  PatientListPresenterInterface{
    Context context;
    Repository repository;
    PatientListViewInterface listViewInterface;
   // String email;
    public PatientListPresenter(Context context, Repository repository, PatientListViewInterface listViewInterface){
        this.context = context;
        this.repository=repository;
        this.listViewInterface = listViewInterface;
    }
    @Override
    public void loadPatients(List<RequestPojo> patients) {

    }
}
