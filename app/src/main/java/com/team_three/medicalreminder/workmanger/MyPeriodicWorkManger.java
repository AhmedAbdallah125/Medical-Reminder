package com.team_three.medicalreminder.workmanger;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.team_three.medicalreminder.dataBase.ConcreteLocalClass;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class MyPeriodicWorkManger extends Worker {
    long timeNow;
    long timeNowPlusThreeHours;
    Repository repository;
    Single<List<MedicationPOJO>> medicationSingleList;
    Single<List<MedicationPOJO>> medicationSingleListForRefillReminder;
    Calendar calendar;
    long alarmTimePeriod;
    // period before running
    long periodBeforeRunning;
    List<MedicationPOJO> medicationListForMedicationReminder;
    List<MedicationPOJO> medicationListForRefillReminder;
    Context context;

    public MyPeriodicWorkManger(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        repository = Repository.getInstance(ConcreteLocalClass.getConcreteLocalClassInstance(context), context);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        calendar = Calendar.getInstance();
        medicationSingleList = repository.getMedicationDayWorkManger(calendar.getTimeInMillis());
        medicationSingleListForRefillReminder = repository.getRefilReminderList(calendar.getTimeInMillis());
        subscribeOnSingleForMedicationReminder();
        subscribeOnSingleForRefillReminder();
        getTimePeriod();
        getCurrentAlarms();
        return Result.success();
    }

    // for periodic
    private void getTimePeriod() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        if (hour == 0) {
            hour += 12;
        }

        timeNow = hour;
        timeNow = hour * 60;
        timeNow = (timeNow + minute) * 60 * 1000;
        timeNowPlusThreeHours = timeNow + (3 * 60 * 60 * 1000);

    }

    //setting for period before running alarm
    private void setDurationTimes(long timeNow, long alarmPeriod) {
        periodBeforeRunning = (alarmPeriod - timeNow) / 60000;
    }

    private void subscribeOnSingleForMedicationReminder() {
        medicationSingleList.subscribe(new SingleObserver<List<MedicationPOJO>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<MedicationPOJO> medicationPOJOS) {
                medicationListForMedicationReminder = medicationPOJOS;
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    private void subscribeOnSingleForRefillReminder() {
        medicationSingleListForRefillReminder.subscribe(new SingleObserver<List<MedicationPOJO>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<MedicationPOJO> medicationPOJOS) {
                medicationListForRefillReminder = medicationPOJOS;
                loopOnRefileMedicationList();
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    private void getCurrentAlarms() {
        if (medicationListForMedicationReminder != null) {
            for (int i = 0; i < medicationListForMedicationReminder.size(); i++) {
                if (medicationListForMedicationReminder.get(i).getTimeAndDose() != null) {
                    for (Map.Entry<String, Integer> entry : medicationListForMedicationReminder.get(i).getTimeAndDose().entrySet()) {
                        if (checkPeriod(entry.getKey())) {
                            setDurationTimes(timeNow, alarmTimePeriod);
                            setOnTimeWorkManger(periodBeforeRunning, medicationListForMedicationReminder.get(i), entry.getKey(), entry.getValue());
                        }
                    }
                }
            }
        }
    }

    private boolean checkPeriod(String time) {
        long t = setTime(time);
        if (t >= timeNow && t <= timeNowPlusThreeHours) {
            alarmTimePeriod = t;
            return true;
        }
        return false;
    }

    private long setTime(String time) {
        long t = 0;
        t = Long.parseLong(time.split(" ")[0].split(":")[0]) * 60;
        t = (t + Long.parseLong(time.split(" ")[0].split(":")[1])) * 60;
        Log.i("zoooooz", "setTime1: " + t * 1000);
        if (time.split(" ")[1].equals("PM")) {
            t = t + (12 * 60 * 60);
        }
        Log.i("zoooooz", "setTime2: " + t * 1000);
        return t * 1000;
    }

    private void setOnTimeWorkManger(long time, MedicationPOJO medicationPOJO, String index, int pillsCount) {
        Log.i("zoooooz", "setOnTimeWorkManger: " + time);
        // pass medication POJO
        Data data = new Data.Builder()
                .putString("MED", serializeToJason(medicationPOJO))
//                .putString("Medication",medicationPOJO.getMedicationName())
                .putString("INDEX", index)
                .putInt("COUNT", pillsCount)
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

    private String serializeToJason(MedicationPOJO pojo) {
        Gson gson = new Gson();
        return gson.toJson(pojo);
    }

    private void callOneTimeRefillReminder(MedicationPOJO medicationPOJO) {

        Data data = new Data.Builder()
                .putString("MedReminderList", serializeToJason(medicationPOJO)).build();
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();
        String tag = medicationPOJO.getMedicationName()+medicationPOJO.getId();
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(RefileReminderWorkManagerForOneTime.class)
                .setInputData(data)
                .setConstraints(constraints)
                .setInitialDelay(10, TimeUnit.SECONDS)
                .addTag(tag)
                .build();
        WorkManager.getInstance(context).enqueueUniqueWork(tag, ExistingWorkPolicy.REPLACE,oneTimeWorkRequest);
    }

    private void loopOnRefileMedicationList() {
        for (MedicationPOJO medicationPOJO : medicationListForMedicationReminder) {
            if (medicationPOJO.getLeftNumber() <= medicationPOJO.getLeftNumberReminder()) {
                callOneTimeRefillReminder(medicationPOJO);
            }
        }
    }

}
