package com.team_three.medicalreminder.workmanger;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.team_three.medicalreminder.dataBase.ConcreteLocalClass;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class MyPeriodicWorkManger extends Worker {
    long timeNow;
    long timeNowPlusSixHours;
    Repository repository;
    Single<List<MedicationPOJO>> medicationSingleList;
    Calendar calendar;
    long alarmTimePeriod;
    // period before running
    long periodBeforeRunning;
    List<MedicationPOJO> medicationList;
    Context context;

    public MyPeriodicWorkManger(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        repository = Repository.getInstance(ConcreteLocalClass.getConcreteLocalClassInstance(context), context);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.i("zoooooz", "doWork: firsttttttt");
        calendar = Calendar.getInstance();
        medicationSingleList = repository.getMedicationDayWorkManger(calendar.getTimeInMillis());
        subscribeOnSingle();
        getTimePeriod();
        getCurrentAlarms();
        return Result.success();
    }

    // for periodic
    private void getTimePeriod() {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.AM_PM) == 1) {
            timeNow = 12;
        }
        timeNow = (timeNow + calendar.get(Calendar.HOUR)) * 60;
        timeNow = (timeNow + calendar.get(Calendar.MINUTE)) * 60 * 1000;
        timeNowPlusSixHours = timeNow + (6 * 60 * 60 * 1000);

        Log.i("zoooooz", "getTimeNow: "+timeNow);
        Log.i("zoooooz", "getTimeNow+6: "+timeNowPlusSixHours);

//        getTimeDose(times, "12:05 AM");
//        setDurationTimes(timeNow, times, time);
    }

    //1
   /* private void getTimeDose(int times, String timeDose) {
        time = new ArrayList<>(times);
//        while (timeDose.entrySet().iterator().hasNext()) {
        time.add(setTime(timeDose));
    }

    private long setTime(String time) {
        long t = 0;
        t = Long.parseLong(time.split(" ")[0].split(":")[0]) * 60;
        t = (t + Long.parseLong(time.split(" ")[0].split(":")[1])) * 60;
        if (time.split(" ")[1].equals("PM")) {
            t = t + (12 * 60 * 60);
            Log.i("TAG", "time is: "+t);
        }
        return t;
    }
*/
    //setting for period before running alarm
    private void setDurationTimes(long timeNow, long alarmPeriod) {
        periodBeforeRunning = (alarmPeriod - timeNow) / 60000;
    }

    private void subscribeOnSingle() {
        medicationSingleList.subscribe(new SingleObserver<List<MedicationPOJO>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<MedicationPOJO> medicationPOJOS) {
                medicationList = medicationPOJOS;
                Log.i("zoooooz", "onSuccess: 5555555555");
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    private void getCurrentAlarms() {
        if (medicationList != null) {
            for (int i = 0; i < medicationList.size(); i++) {
//                while (medicationList.get(i).getTimeAndDose().entrySet().iterator().hasNext()) {
                for(Map.Entry<String,Integer> entry: medicationList.get(i).getTimeAndDose().entrySet()) {
                    Log.i("zoooooz", "getCurrentAlarms: " + medicationList.get(i).getMedicationName());
                    if (checkPeriod(entry.getKey())){

                        Log.i("zoooooz", "getCurrentAlarmsperiod: " + alarmTimePeriod);
                        setDurationTimes(timeNow, alarmTimePeriod);
                        setOnTimeWorkManger(periodBeforeRunning, medicationList.get(i),entry.getKey(),entry.getValue());
                    }
                }
//                }

            }
        }
    }

    private boolean checkPeriod(String time) {
        long t = setTime(time);
        if (t >= timeNow && t <= timeNowPlusSixHours) {
            alarmTimePeriod = t;
            return true;
        }
        return false;
    }

    private long setTime(String time) {
        long t = 0;
        t = Long.parseLong(time.split(" ")[0].split(":")[0]) * 60;
        t = (t + Long.parseLong(time.split(" ")[0].split(":")[1])) * 60;
        if (time.split(" ")[1].equals("PM")) {
            t = t + (12 * 60 * 60);
        }
        return t * 1000;
    }

    private void setOnTimeWorkManger(long time, MedicationPOJO medicationPOJO,String index,int pillsCount) {
        Log.i("zoooooz", "setOnTimeWorkManger: "+time);
        // pass medication POJO
        Data data = new Data.Builder()
                .putString("MED",serializeToJason(medicationPOJO))
//                .putString("Medication",medicationPOJO.getMedicationName())
                .putString("INDEX",index)
                .putInt("COUNT",pillsCount)
                .build();
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyOneTimeWorkManger.class).
                setInputData(data)
                .setConstraints(constraints)
                .setInitialDelay(time, TimeUnit.MINUTES)
                .addTag("download")
                .build();
        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest);
    }
    public String serializeToJason(MedicationPOJO pojo) {
        Gson gson = new Gson();
        return gson.toJson(pojo);
    }


}
