package com.team_three.medicalreminder.workmanger.medicalremindermanger;

import static android.content.Context.WINDOW_SERVICE;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.gson.Gson;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.dataBase.ConcreteLocalClass;
import com.team_three.medicalreminder.databinding.ReminderNotificationDialogBinding;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.model.Utility;
import com.team_three.medicalreminder.network.FireBaseNetwork;
import com.team_three.medicalreminder.workmanger.MyPeriodicWorkManger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReminderWindowManger {
    private Context context;
    private MedicationPOJO myMedicine;
    ReminderNotificationDialogBinding binding;
    private WindowManager windowManager;
    private View customNotificationDialogView;
    String description;
    Repository repository;
    int count;
    String key;
    String email;
    public ReminderWindowManger(Context context, MedicationPOJO medication, String key, int count) {
        this.context = context;
        this.myMedicine = medication;
        this.key = key;
        this.count = count;
        repository = Repository.getInstance(ConcreteLocalClass.getConcreteLocalClassInstance(context), FireBaseNetwork.getInstance(), context);
    }

    public void setMyWindowManger() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customNotificationDialogView = inflater.inflate(R.layout.reminder_notification_dialog, null);
        binding = ReminderNotificationDialogBinding.bind(customNotificationDialogView);
        bindView();
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                LAYOUT_FLAG,
                android.R.attr.showWhenLocked | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE,
                PixelFormat.TRANSLUCENT);
        windowManager.addView(customNotificationDialogView, params);
    }

    private void bindView() {
        binding.imgMedNotification.setImageResource(myMedicine.getImageID());
        binding.textView.setText(myMedicine.getMedicationName());
        binding.txtTime.setText(key);
        binding.txtMedTimeNotification.setText("Schedule for " + key + ", today");
        description = "take " + count + " " + myMedicine.getFormat() + "(s), " +
                myMedicine.getStrength() + myMedicine.getWeight() + myMedicine.getInstruction();
        binding.txtMedDoseNotification.setText(description);
        handleButtons();
    }

    private void handleButtons() {
        binding.imgClose.setOnClickListener(v -> {
            stopMyService();
            close();
        });
        binding.imgAccept.setOnClickListener(v -> {
            List<Boolean> list = myMedicine.getIsTakenList();
            list.set(getHashMapIndex(), true);
            int n = myMedicine.getLeftNumber() - count;
            if (n < 0)
                n = 0;
            myMedicine.setLeftNumber(n);
            myMedicine.setIsTakenList(list);
            updateMedication(myMedicine);
            email = Utility.checkShared(context);
            if (!email.equals("null")){
                repository.updatePatientMedicationList(email,myMedicine);

            }
            setPeriodicWorkManger();
            stopMyService();
            close();
        });

        binding.imgSkip.setOnClickListener(v -> {
            context.stopService(new Intent(context, ReminderService.class));
            stopMyService();
            close();
        });

        binding.imgSnooze.setOnClickListener(v -> {
            setOnTimeWorkManger(myMedicine, key, count);
            Toast.makeText(context, "Snoozed for 1 hour", Toast.LENGTH_SHORT).show();
            stopMyService();
            close();
        });
    }

    private int getHashMapIndex() {
        List keys = new ArrayList(myMedicine.getTimeAndDose().keySet());
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i) == key) {
                return i;
            }
        }
        return 0;
    }

    private void updateMedication(MedicationPOJO medication) {
        repository.updateMedications(medication);
    }

    private void setOnTimeWorkManger(MedicationPOJO medicationPOJO, String index, int pillsCount) {
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
        String tag = medicationPOJO.getId() + medicationPOJO.getMedicationName() + index;
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyOneTimeWorkManger.class).
                setInputData(data)
                .setConstraints(constraints)
                .setInitialDelay(1, TimeUnit.HOURS)
                .addTag(tag)
                .build();
        WorkManager.getInstance(context).enqueueUniqueWork(tag, ExistingWorkPolicy.REPLACE, oneTimeWorkRequest);
    }

    private void setPeriodicWorkManger(){
            Constraints constraints = new Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .build();

            PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MyPeriodicWorkManger.class,
                    3, TimeUnit.HOURS)
                    .setConstraints(constraints)
                    .build();

            WorkManager.getInstance(context).enqueueUniquePeriodicWork("Counter", ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest);
//        WorkManager.getInstance(this.getContext()).enqueue(periodicWorkRequest);
    }

    private String serializeToJason(MedicationPOJO pojo) {
        Gson gson = new Gson();
        return gson.toJson(pojo);
    }

    private void close() {
        try {
            ((WindowManager) context.getSystemService(WINDOW_SERVICE)).removeView(customNotificationDialogView);
            customNotificationDialogView.invalidate();
            ((ViewGroup) customNotificationDialogView.getParent()).removeAllViews();
        } catch (Exception e) {
            Log.d("Error2", e.toString());
        }
    }

    private void stopMyService() {
        context.stopService(new Intent(context, ReminderService.class));
    }
}
