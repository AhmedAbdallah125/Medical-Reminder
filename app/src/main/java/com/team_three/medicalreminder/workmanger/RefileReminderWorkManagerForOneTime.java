package com.team_three.medicalreminder.workmanger;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.dataBase.ConcreteLocalClass;
import com.team_three.medicalreminder.databinding.ReminderNotificationDialogBinding;
import com.team_three.medicalreminder.model.MedicationPOJO;

import java.util.List;

public class RefileReminderWorkManagerForOneTime extends Worker {
    private Context context;
    MedicationPOJO medicationPOJO;
    private MaterialAlertDialogBuilder dialogBuilder;
    private View customAlertDialogView;
    ReminderNotificationDialogBinding binding;
    private Drawable check;
    private Drawable skip;
    public RefileReminderWorkManagerForOneTime(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        getData();
     //   lunchCustomDialog();
        return Result.success();
    }

    public MedicationPOJO fomStringPojo(String pojoString) {

        Gson gson = new Gson();
        return gson.fromJson(pojoString, MedicationPOJO.class);
    }

    private void getData() {
        Data data = getInputData();
        medicationPOJO = fomStringPojo(data.getString("MedReminderList"));
       // Toast.makeText(context, "you must refill the "+medicationPOJO.getMedicationName(), Toast.LENGTH_SHORT).show();
        Log.i("Reminder", "getData: "+medicationPOJO.getMedicationName());
    }

    private void lunchCustomDialog() {
        customAlertDialogView = LayoutInflater.from(context).inflate(R.layout.reminder_notification_dialog, null, false);
        binding = ReminderNotificationDialogBinding.bind(customAlertDialogView);
        dialogBuilder = new MaterialAlertDialogBuilder(context);
        bindView();

        dialogBuilder.setView(customAlertDialogView)
                .setTitle("Medication Reminder")
                .setPositiveButtonIcon(check)
                .setPositiveButton("Take", (dialog, i) -> {
                    List<Boolean> list = medicationPOJO.getIsTakenList();
                   // list.set(getHashMapIndex(), true);
                    medicationPOJO.setIsTakenList(list);
                    updateMedication(medicationPOJO);
                    dialog.dismiss();
                    Toast.makeText(context, "Successfully Added!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButtonIcon(skip)
                .setNegativeButton("Skip", (dialog, i) -> dialog.dismiss())
                .show();
    }
    private void bindView() {
        binding.imgMedNotification.setImageResource(medicationPOJO.getImageID());
//        binding.txtMedTimeNotification.setText("Schedule for " + key + ", today");
//        binding.txtMedDoseNotification.setText("take " + count + " " + medicationPOJO.getFormat() + ", " + medicationPOJO.getStrength() + medicationPOJO.getWeight());
    }
    private void updateMedication(MedicationPOJO medication) {
        ConcreteLocalClass concreteLocalClass = ConcreteLocalClass.getConcreteLocalClassInstance(context);
        concreteLocalClass.updateMedications(medication);
    }
}
