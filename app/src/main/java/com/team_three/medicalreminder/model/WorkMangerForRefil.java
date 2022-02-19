package com.team_three.medicalreminder.model;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class WorkMangerForRefil extends Worker {

    public WorkMangerForRefil(@NonNull Context context, @NonNull WorkerParameters workerParams) {

        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data data = getInputData();
        int number = data.getInt("number",-1);
        Log.d("TAG", "doWork: "+number);
        for (int i=number;i>0;i--){
            Log.d("TAG", "doWork: "+i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return Result.failure();
            }
        }
        return Result.success();
    }
}
