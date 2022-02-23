package com.team_three.medicalreminder.workmanger;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team_three.medicalreminder.model.MedicationPOJO;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

public class MyOneTimeWorkManger extends Worker {
    MedicationPOJO myMedicine;
    String key;
    int count;

    public MyOneTimeWorkManger(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        getData();
        Log.i("ZO", "medicine: " + myMedicine.getId());
        Log.i("ZO", "The time: " + key);
        Log.i("ZO", "Count: " + count);


        return Result.success();
    }

    public MedicationPOJO fomStringPojo(String pojoString) {

        Gson gson = new Gson();
        return gson.fromJson(pojoString, MedicationPOJO.class);
    }

    // getting DataFirst
    private void getData() {
        Data data = getInputData();
        myMedicine = fomStringPojo(data.getString("MED"));
        key = data.getString("INDEX");
        count = data.getInt("COUNT", 1);
    }
}
