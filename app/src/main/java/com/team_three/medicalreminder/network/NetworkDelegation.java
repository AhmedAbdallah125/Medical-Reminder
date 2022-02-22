package com.team_three.medicalreminder.network;

public interface NetworkDelegation {
    void onSuccess();
    void onFailure(String errorMessage);

    // for response
    void onSuccessReturn(String userName);
}
