package com.team_three.medicalreminder.network;

public interface NetworkDelegation {
    void onSuccess(boolean response);
    void onFailure(boolean response);
}
