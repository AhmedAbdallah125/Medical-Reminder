package com.team_three.medicalreminder.taker.presenter;

import com.team_three.medicalreminder.model.TakerPOJO;

import java.util.List;

public interface TakerLIstPresenterInterface {
    void loadTakers();
    void sendEmail(String email);

}
