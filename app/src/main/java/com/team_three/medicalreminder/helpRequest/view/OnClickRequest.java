package com.team_three.medicalreminder.helpRequest.view;

import com.team_three.medicalreminder.model.PatientPojo;
import com.team_three.medicalreminder.model.TakerPOJO;

public interface OnClickRequest {
    void onClickAccept(TakerPOJO takerPOJO, PatientPojo patientPojo);
    void onClickReject(String key,String email);
}
