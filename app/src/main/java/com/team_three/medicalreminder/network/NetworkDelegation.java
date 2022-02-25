package com.team_three.medicalreminder.network;

import com.team_three.medicalreminder.model.PatientPojo;
import com.team_three.medicalreminder.model.RequestPojo;
import com.team_three.medicalreminder.model.TakerPOJO;

import java.util.List;

public interface NetworkDelegation {
    void onSuccess();
    void onFailure(String errorMessage);

    // for response
    void onSuccessReturn(String userName);
    void onSuccessRequest(List<RequestPojo> requestPojos);
    void onSuccessTaker(List<TakerPOJO> takerPOJOS);
    void onSuccess(boolean response);
    void onSuccessPatient(List<PatientPojo> patientPojos);
    void isUserExist(boolean existance);

}
