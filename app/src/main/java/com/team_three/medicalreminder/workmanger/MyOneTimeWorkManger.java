package com.team_three.medicalreminder.workmanger;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyOneTimeWorkManger extends Worker {

    public MyOneTimeWorkManger(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.i("zoooooz", "doWork: Doneeeeeee");
        return Result.success();
    }
}
