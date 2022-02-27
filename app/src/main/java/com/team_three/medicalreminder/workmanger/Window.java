package com.team_three.medicalreminder.workmanger;

import android.content.Context;
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
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.gson.Gson;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.dataBase.ConcreteLocalClass;
import com.team_three.medicalreminder.databinding.RefillReminderDialogBinding;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;

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


    public Window(Context context ,String data ){
        this.medicationPOJO = fromStringPojo(data);
        this.context=context;
        this.data =data;
        Log.i("Reminder", "Window: "+ medicationPOJO.getMedicationName());
       setWindowManager();
        setBinding();
    }

    public void open() {
        try {
            if(mView.getWindowToken()==null) {
                if(mView.getParent()==null) {
                    mWindowManager.addView(mView, mParams);
                }
            }
        } catch (Exception e) {
            Log.d("Error1",e.toString());
        }
    }

    public void close() {
        try {
            ((WindowManager)context.getSystemService(WINDOW_SERVICE)).removeView(mView);
            mView.invalidate();
            ((ViewGroup)mView.getParent()).removeAllViews();
        } catch (Exception e) {
            Log.d("Error2",e.toString());
        }
    }

    public MedicationPOJO fromStringPojo(String pojoString) {
        Gson gson = new Gson();
        return gson.fromJson(pojoString, MedicationPOJO.class);
    }

    private void callOneTimeRefillReminder(String pojo){

        Data data = new Data.Builder()
                .putString("MedReminderList",pojo).build();
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(RefileReminderWorkManagerForOneTime.class)
                .setInputData(data)
                .setConstraints(constraints)
                .setInitialDelay(1, TimeUnit.HOURS)
                .addTag("downloadReminder")
                .build();
        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest);
    }

    private void setBinding(){
        binding = RefillReminderDialogBinding.bind(mView);

        binding.titleText.setText("Refill "+medicationPOJO.getMedicationName() +" before finish!");
        binding.windowClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });
        binding.windowRefill.setOnClickListener(view -> {
            int leftNumber = medicationPOJO.getLeftNumber()  +  Integer.parseInt( binding.refilNumber.getEditableText().toString());
            medicationPOJO.setLeftNumber(leftNumber);
            repository = Repository.getInstance(ConcreteLocalClass.getConcreteLocalClassInstance(context),context);
            repository.updateMedications(medicationPOJO);
            close();
        });

        binding.windowSnoze.setOnClickListener(view -> {
            callOneTimeRefillReminder(data);
            close();
        });
    }

    private void setWindowManager(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutFlage =  WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;

        }else{
            layoutFlage = WindowManager.LayoutParams.TYPE_PHONE;
        }
        mParams = new WindowManager.LayoutParams(

                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,
                layoutFlage,
                WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE|WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = layoutInflater.inflate(R.layout.refill_reminder_dialog, null);



        mParams.gravity = Gravity.CENTER;
        mWindowManager = (WindowManager)context.getSystemService(WINDOW_SERVICE);
    }


}
