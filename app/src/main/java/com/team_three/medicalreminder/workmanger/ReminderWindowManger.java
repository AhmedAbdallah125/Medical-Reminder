package com.team_three.medicalreminder.workmanger;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.dataBase.ConcreteLocalClass;
import com.team_three.medicalreminder.databinding.ReminderNotificationDialogBinding;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;

import java.util.ArrayList;
import java.util.List;

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

    public ReminderWindowManger(Context context, MedicationPOJO medication, String key, int count) {
        this.context = context;
        this.myMedicine = medication;
        this.key = key;
        this.count = count;
        repository = Repository.getInstance(ConcreteLocalClass.getConcreteLocalClassInstance(context), context);
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
        binding.imgClose.setOnClickListener(v -> windowManager.removeView(customNotificationDialogView));
        binding.imgAccept.setOnClickListener(v -> {
            List<Boolean> list = myMedicine.getIsTakenList();
            list.set(getHashMapIndex(), true);
            int n = myMedicine.getLeftNumber() - count;
            if (n < 0)
                n = 0;
            myMedicine.setLeftNumber(n);
            myMedicine.setIsTakenList(list);
            updateMedication(myMedicine);
            context.stopService(new Intent(context, ReminderService.class));
            windowManager.removeView(customNotificationDialogView);
        });

        binding.imgSkip.setOnClickListener(v -> {
            context.stopService(new Intent(context, ReminderService.class));
            windowManager.removeView(customNotificationDialogView);
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
}
