package com.team_three.medicalreminder.workmanger.takerManager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.team_three.medicalreminder.model.MedicationPOJO;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class TakerPeriodicWorkManager extends Worker {
    long timeNow;
    long timeNowPlusThreeHours;
    String email;
    List<MedicationPOJO> medicationSingleList;
    Context context;
    long alarmTimePeriod;
    // period before running
    long periodBeforeRunning;

    public TakerPeriodicWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    public void setMedicationSingleList(List<MedicationPOJO> medicationSingleList) {
        this.medicationSingleList = medicationSingleList;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    @Override
    public Result doWork() {

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

    private void getCurrentAlarms() {
        if (medicationSingleList != null) {
            for (int i = 0; i < medicationSingleList.size(); i++) {
                if (medicationSingleList.get(i).getTimeAndDose() != null) {
                    for (Map.Entry<String, Integer> entry : medicationSingleList.get(i).getTimeAndDose().entrySet()) {
                        if (checkPeriod(entry.getKey())) {
                            setDurationTimes(timeNow, alarmTimePeriod);
                            setOnTimeWorkManger(periodBeforeRunning, medicationSingleList.get(i), entry.getKey(), entry.getValue());
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
        if (time.split(" ")[1].equals("PM")) {
            t = t + (12 * 60 * 60);
        }
        return t * 1000;
    }

    private void setOnTimeWorkManger(long time, MedicationPOJO medicationPOJO, String index, int pillsCount) {
        // pass medication POJO
        Data data = new Data.Builder()
                .putString("MED", serializeToJason(medicationPOJO))
//                .putString("Medication",medicationPOJO.getMedicationName())
                .putString("INDEX", index)
                .putInt("COUNT", pillsCount)
                .putString("EMAIL",email)
                .build();
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(TakerOneTmeWorkManager.class).
                setInputData(data)
                .setConstraints(constraints)
                .setInitialDelay(time, TimeUnit.MINUTES)
                .addTag("download")
                .build();
        //
        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest);
    }

    private String serializeToJason(MedicationPOJO pojo) {
        Gson gson = new Gson();
        return gson.toJson(pojo);
    }

}
