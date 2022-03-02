package com.team_three.medicalreminder.workmanger.takerManager;

import static androidx.core.content.ContextCompat.startForegroundService;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class TakerOneTmeWorkManager extends Worker {
    String key;
    int count;
    Data data;
    String email;
    public final static String KEY_TAG = "INDEX";
    public final static String VALUE_TAG = "COUNT";
    public final static String MEDICINE_TAG = "MED";
    public final static String EMAIL_TAG = "EMAIL";
    public static final String NAME_TAG = "NAMe";

    private String name;


    public TakerOneTmeWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        getData();
        startMyService();
        return Result.success();
    }

    // getting DataFirst
    private void getData() {
        data = getInputData();
        key = data.getString(KEY_TAG);
        count = data.getInt(VALUE_TAG, 1);
        email = data.getString("EMAIL");
//        name=data.getString("NAME");
    }


    private void startMyService() {
        Intent intent = new Intent(getApplicationContext(), TakerReminderService.class);
        intent.putExtra(MEDICINE_TAG, data.getString(MEDICINE_TAG));
        intent.putExtra(KEY_TAG, key);
        intent.putExtra(VALUE_TAG, count);
        intent.putExtra(EMAIL_TAG, email);
//        intent.putExtra(NAME_TAG,name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(getApplicationContext(), intent);
        } else {
            getApplicationContext().startService(intent);
        }
    }
}
