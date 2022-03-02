package com.team_three.medicalreminder.workmanger.takerManager;

import static android.content.Context.WINDOW_SERVICE;

import android.annotation.SuppressLint;
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
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.gson.Gson;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.databinding.ReminderNotificationDialogBinding;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.network.FireBaseNetwork;
import com.team_three.medicalreminder.network.NetworkInterface;



import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TakerReminderWindowManager {
    private String name;
    private Context context;
    private MedicationPOJO myMedicine;
    ReminderNotificationDialogBinding binding;
    View customDialog;
    String description;
    Repository repository;
    int count;
    String key;
    String email;


    public TakerReminderWindowManager(Context context, MedicationPOJO myMedicine, String description, int count, String email) {
        this.context = context;
        this.myMedicine = myMedicine;
        this.key = key;
        this.count = count;
        this.email = email;
//        this.name = name;
        NetworkInterface myRemote = FireBaseNetwork.getInstance();
        repository = Repository.getInstance(myRemote, context);
    }

    // check connection first
    public void setWindowManager() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customDialog = inflater.inflate(R.layout.reminder_notification_dialog, null);
        binding = ReminderNotificationDialogBinding.bind(customDialog);
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

    @SuppressLint("SetTextI18n")
    private void bindViews() {
        // must contain email
        binding.imgMedNotification.setImageResource(myMedicine.getImageID());
        binding.textView.setText("Patient is : " + email + "\n" +
                myMedicine.getMedicationName());
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
            stopMyService();
            close();
        });

        binding.imgSkip.setOnClickListener(v -> {
            context.stopService(new Intent(context, TakerReminderService.class));
            stopMyService();
            close();
        });

        binding.imgSnooze.setOnClickListener(v -> {
            String tag = setTag(myMedicine, key);
            setOnTimeWorkManger(tag, myMedicine, key, count);
            Toast.makeText(context, "Snoozed for 1 hour", Toast.LENGTH_SHORT).show();
            stopMyService();
            close();
        });
    }

    private String setTag(MedicationPOJO medicationPOJO, String key) {
        return email + medicationPOJO.getId() + medicationPOJO.getMedicationName() + key;
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
        repository.updatePatientMedicationList(email, medication);
    }

    private void setOnTimeWorkManger(String tag, MedicationPOJO medicationPOJO, String index, int pillsCount) {
        // pass medication POJO
        Data data = new Data.Builder()
                .putString("MED", serializeToJason(medicationPOJO))
//                .putString("Medication",medicationPOJO.getMedicationName())
                .putString("INDEX", index)
                .putInt("COUNT", pillsCount)
                .putString("EMAIL", email)
//                .putString("NAME", name)
                .build();
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(TakerOneTmeWorkManager.class).
                setInputData(data)
                .setConstraints(constraints)
                .setInitialDelay(60, TimeUnit.MINUTES)
                .addTag(tag)
                .build();
        //
    }

    private String serializeToJason(MedicationPOJO pojo) {
        Gson gson = new Gson();
        return gson.toJson(pojo);
    }

    private void close() {
        try {
            ((WindowManager) context.getSystemService(WINDOW_SERVICE)).removeView(customDialog);
            customDialog.invalidate();
            ((ViewGroup) customDialog.getParent()).removeAllViews();
        } catch (Exception e) {
            Log.d("Error", e.toString());
        }
    }

    private void stopMyService() {
        context.stopService(new Intent(context, TakerReminderService.class));
    }
}
