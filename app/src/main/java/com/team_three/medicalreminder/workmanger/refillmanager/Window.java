package com.team_three.medicalreminder.workmanger.refillmanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import static android.content.Context.WINDOW_SERVICE;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.gson.Gson;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.dataBase.ConcreteLocalClass;
import com.team_three.medicalreminder.databinding.RefillReminderDialogBinding;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.model.Utility;
import com.team_three.medicalreminder.network.FireBaseNetwork;
import com.team_three.medicalreminder.workmanger.refillmanager.ForeGroundService;
import com.team_three.medicalreminder.workmanger.refillmanager.RefileReminderWorkManagerForOneTime;

import java.util.concurrent.TimeUnit;

public class Window {

    // declaring required variables
    private Context context;
    private View mView;
    private WindowManager.LayoutParams mParams;
    private WindowManager mWindowManager;
    private LayoutInflater layoutInflater;
    private int layoutFlage;
    RefillReminderDialogBinding binding;
    MedicationPOJO medicationPOJO;
    Repository repository;
    String data;

    String email;
    public Window(Context context, String data) {
        this.medicationPOJO = fromStringPojo(data);
        this.context = context;
        this.data = data;

        repository = Repository.getInstance(ConcreteLocalClass.getConcreteLocalClassInstance(context),FireBaseNetwork.getInstance(), context);

        Log.i("Reminder", "Window: " + medicationPOJO.getMedicationName());
        setWindowManager();
        setBinding();
    }

    public void open() {
        try {
            if (mView.getWindowToken() == null) {
                if (mView.getParent() == null) {
                    mWindowManager.addView(mView, mParams);
                }
            }
        } catch (Exception e) {
            Log.d("Error1", e.toString());
        }
    }

    public void close() {
        try {
            ((WindowManager) context.getSystemService(WINDOW_SERVICE)).removeView(mView);
            mView.invalidate();
            ((ViewGroup) mView.getParent()).removeAllViews();
        } catch (Exception e) {
            Log.d("Error2", e.toString());
        }
    }

    public MedicationPOJO fromStringPojo(String pojoString) {
        Gson gson = new Gson();
        return gson.fromJson(pojoString, MedicationPOJO.class);
    }

    private void callOneTimeRefillReminder(String pojo) {

        Data data = new Data.Builder()
                .putString("MedReminderList", pojo).build();
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();
        String tag = medicationPOJO.getMedicationName()+medicationPOJO.getId();
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(RefileReminderWorkManagerForOneTime.class)
                .setInputData(data)
                .setConstraints(constraints)
                .setInitialDelay(1, TimeUnit.HOURS)
                .addTag(tag)
                .build();
        WorkManager.getInstance(context).enqueueUniqueWork(tag, ExistingWorkPolicy.REPLACE,oneTimeWorkRequest);
    }

    private void setBinding() {

        binding = RefillReminderDialogBinding.bind(mView);
        binding.titleText.setText("Refill " + medicationPOJO.getMedicationName() + " before finish!");
        binding.medIcon.setImageResource(medicationPOJO.getImageID());
        binding.refillNumber.setText(String.valueOf(medicationPOJO.getLeftNumber()));

        binding.decreaseRefill.setOnClickListener(view -> {
            if (Integer.parseInt(binding.refillNumber.getText().toString()) > medicationPOJO.getLeftNumber()) {
                int number = Integer.parseInt(binding.refillNumber.getText().toString()) - 1;
                binding.refillNumber.setText(String.valueOf(number));
            }

        });

        binding.increaseRefill.setOnClickListener(view -> {
            int number = Integer.parseInt(binding.refillNumber.getText().toString()) + 1;
            binding.refillNumber.setText(String.valueOf(number));
        });

        binding.windowClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicationPOJO.setFillReminder(false);
                repository.updateMedications(medicationPOJO);


                stopMyService();
                close();
            }
        });
        binding.windowRefill.setOnClickListener(view -> {
            int leftNumber = Integer.parseInt(binding.refillNumber.getText().toString());
            medicationPOJO.setLeftNumber(leftNumber);
            repository.updateMedications(medicationPOJO);
            email = Utility.checkShared(context);
            if (!email.equals("null")){
                repository.updatePatientMedicationList(email,medicationPOJO);
            }
            stopMyService();
            close();
        });

        binding.windowSnoze.setOnClickListener(view -> {
            callOneTimeRefillReminder(data);
            stopMyService();
            close();
        });
    }

    private void stopMyService() {
        context.stopService(new Intent(context, ForeGroundService.class));
    }

    private void setWindowManager() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutFlage = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;

        } else {
            layoutFlage = WindowManager.LayoutParams.TYPE_PHONE;
        }
        mParams = new WindowManager.LayoutParams(

                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,
                layoutFlage,
                android.R.attr.showWhenLocked | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE,
                PixelFormat.TRANSLUCENT);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = layoutInflater.inflate(R.layout.refill_reminder_dialog, null, false);


        mParams.gravity = Gravity.CENTER;
        mWindowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
    }


}
