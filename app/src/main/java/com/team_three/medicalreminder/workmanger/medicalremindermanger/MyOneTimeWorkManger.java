package com.team_three.medicalreminder.workmanger.medicalremindermanger;

import static androidx.core.content.ContextCompat.startForegroundService;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


public class MyOneTimeWorkManger extends Worker {
    String key;
    int count;
    Data data;
    public final static String KEY_TAG = "INDEX";
    public final static String VALUE_TAG = "COUNT";
    public final static String MEDICINE_TAG = "MED";


    public MyOneTimeWorkManger(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        getData();
//        lunchCustomDialog();
        startMyService();
        return Result.success();
    }

    // getting DataFirst
    private void getData() {
        data = getInputData();
        key = data.getString(KEY_TAG);
        count = data.getInt(VALUE_TAG, 1);
        Log.i("ahmaaaad", "setOnTimeWorkManger: "+key);
    }


    private void startMyService() {
        Intent intent = new Intent(getApplicationContext(), ReminderService.class);
        intent.putExtra(MEDICINE_TAG, data.getString(MEDICINE_TAG));
        intent.putExtra(KEY_TAG, key);
        intent.putExtra(VALUE_TAG, count);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(getApplicationContext(), intent);
        } else {
            getApplicationContext().startService(intent);
        }
    }
}
