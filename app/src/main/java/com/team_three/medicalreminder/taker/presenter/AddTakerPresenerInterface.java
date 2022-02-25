package com.team_three.medicalreminder.taker.presenter;

import com.team_three.medicalreminder.model.RequestPojo;

public interface AddTakerPresenerInterface {
    void sendRequest(RequestPojo requestPojo);
    void  isLoggedIn();
    void  userExistance(String email);
}
