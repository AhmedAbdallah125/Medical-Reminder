package com.team_three.medicalreminder.workmanger.takerManager;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.databinding.ReminderNotificationDialogBinding;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.workmanger.Window;

public class TakerReminderWindowManager {
    private Context context;
    private MedicationPOJO myMedicine;
    ReminderNotificationDialogBinding binding;
    private WindowManager windowManager;
    private View customNotificationDialogView;
    String description;
    Repository repository;
    int count;
    String key;


    public TakerReminderWindowManager(Context context, MedicationPOJO myMedicine, String description, int count) {
        this.context = context;
        this.myMedicine = myMedicine;
        this.key = key;
        this.count = count;
    }

    // check connection first
    public void setWindowManager() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customDialog = inflater.inflate(R.layout.reminder_notification_dialog, null);
        binding = ReminderNotificationDialogBinding.bind(customNotificationDialogView);
        bindViews();
        int LAYOUT_FLAG = getLayoutFlag();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = getWindowParams(LAYOUT_FLAG);
        windowManager.addView(customDialog, layoutParams);
    }

    private int getLayoutFlag() {
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }
        return LAYOUT_FLAG;
    }

    private WindowManager.LayoutParams getWindowParams(int LAYOUT_FLAG) {
        return new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                LAYOUT_FLAG,
                android.R.attr.showWhenLocked | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE,
                PixelFormat.TRANSLUCENT);
    }

    private void bindViews() {
        // must contain email
        binding.imgMedNotification.setImageResource(myMedicine.getImageID());
        binding.textView.setText(myMedicine.getMedicationName());
        binding.txtTime.setText(key);
        binding.txtMedTimeNotification.setText("Schedule for " + key + ", today");
        description = "take " + count + " " + myMedicine.getFormat() + "(s), " +
                myMedicine.getStrength() + myMedicine.getWeight() + myMedicine.getInstruction();
        binding.txtMedDoseNotification.setText(description);
//        handleButtons();
    }
}
