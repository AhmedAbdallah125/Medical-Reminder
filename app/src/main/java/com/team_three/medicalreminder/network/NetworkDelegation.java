package com.team_three.medicalreminder.network;

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
}
