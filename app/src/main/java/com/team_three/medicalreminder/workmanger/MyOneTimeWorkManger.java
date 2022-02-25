package com.team_three.medicalreminder.workmanger;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.dataBase.ConcreteLocalClass;
import com.team_three.medicalreminder.databinding.ReminderNotificationDialogBinding;
import com.team_three.medicalreminder.model.MedicationPOJO;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MyOneTimeWorkManger extends Worker {
    MedicationPOJO myMedicine;
    String key;
    int count;

    //for dialogs
    private MaterialAlertDialogBuilder dialogBuilder;
    private View customAlertDialogView;
    private Context context;
    ReminderNotificationDialogBinding binding;
    private Drawable check;
    private Drawable skip;

    public MyOneTimeWorkManger(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {

        //test 55555
        //888

        getData();
        Log.i("zoooooz", "medicine: " + myMedicine.getId());
        Log.i("zoooooz", "The time: " + key);
        Log.i("zoooooz", "Count: " + count);
        lunchCustomDialog();

        return Result.success();
    }

    public MedicationPOJO fomStringPojo(String pojoString) {

        Gson gson = new Gson();
        return gson.fromJson(pojoString, MedicationPOJO.class);
    }

    // getting DataFirst
    private void getData() {
        Data data = getInputData();
        myMedicine = fomStringPojo(data.getString("MED"));
        key = data.getString("INDEX");
        count = data.getInt("COUNT", 1);
    }

    private void lunchCustomDialog() {
        customAlertDialogView = LayoutInflater.from(context).inflate(R.layout.reminder_notification_dialog, null, false);
        binding = ReminderNotificationDialogBinding.bind(customAlertDialogView);
        dialogBuilder = new MaterialAlertDialogBuilder(context);
        bindView();
        setDrawable();
        dialogBuilder.setView(customAlertDialogView)
                .setTitle("Medication Reminder")
                .setPositiveButtonIcon(check)
                .setPositiveButton("Take", (dialog, i) -> {
                    List<Boolean> list = myMedicine.getIsTakenList();
                    list.set(getHashMapIndex(), true);
                    myMedicine.setIsTakenList(list);
                    updateMedication(myMedicine);
                    dialog.dismiss();
                    Toast.makeText(context, "Successfully Added!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButtonIcon(skip)
                .setNegativeButton("Skip", (dialog, i) -> dialog.dismiss())
                .show();
    }

    private void setDrawable(){
        check = context.getResources().getDrawable(
                R.drawable.ic_outline_check_24);
        skip = context.getResources().getDrawable(
                R.drawable.ic_baseline_clear_24);
    }

    private void bindView() {
        binding.imgMedNotification.setImageResource(myMedicine.getImageID());
        binding.txtMedTimeNotification.setText("Schedule for " + key + ", today");
        binding.txtMedDoseNotification.setText("take " + count + " " + myMedicine.getFormat() + ", " + myMedicine.getStrength() + myMedicine.getWeight());
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
        ConcreteLocalClass concreteLocalClass = ConcreteLocalClass.getConcreteLocalClassInstance(context);
        concreteLocalClass.updateMedications(medication);
    }
}
