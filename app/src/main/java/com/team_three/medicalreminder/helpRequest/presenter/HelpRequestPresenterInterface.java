package com.team_three.medicalreminder.helpRequest.presenter;

import com.team_three.medicalreminder.model.PatientPojo;
import com.team_three.medicalreminder.model.RequestPojo;
import com.team_three.medicalreminder.model.TakerPOJO;

import java.util.List;

public interface HelpRequestPresenterInterface {

    void  loadHelpRequest();
    void sendEmail(String email);
    void onAccept(TakerPOJO takerPOJO, PatientPojo patientPojo);
    void  onReject(String key);
}
