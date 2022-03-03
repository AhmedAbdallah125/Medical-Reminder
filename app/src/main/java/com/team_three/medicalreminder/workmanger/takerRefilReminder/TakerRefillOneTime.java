package com.team_three.medicalreminder.workmanger.takerRefilReminder;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


public class TakerRefillOneTime extends Worker {
    String email;
    Context context;
    public TakerRefillOneTime(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context=context;
    }

    @NonNull
    @Override
    public Result doWork() {
        Data data = getInputData();
        email =data.getString("EMAIL");
        startMyService(data.getString("MedReminderList"));
        return Result.success();
    }

    public void startMyService(String data){
        Intent intent = new Intent(context, TakerRefillService.class);
        intent.putExtra("refill",data);
        intent.putExtra("EMAIL",email);
        if(Settings.canDrawOverlays(context)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent);
            } else {
                context.startService(intent);
            }
        }
    }

}
