package com.team_three.medicalreminder.workmanger;

import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


public class RefileReminderWorkManagerForOneTime extends Worker {
Context context;
    public RefileReminderWorkManagerForOneTime(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context=context;
    }

    @NonNull
    @Override
    public Result doWork() {
        Data data = getInputData();

        Log.i("Reminder", "doWork: momomom");
        startMyService(data.getString("MedReminderList"));
        return Result.success();
    }

    public void startMyService(String data){
        Intent intent = new Intent(context, ForeGroundService.class);
        intent.putExtra("refill",data);
        if(Settings.canDrawOverlays(context)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent);
            } else {
                context.startService(intent);
            }
        }
    }
}
