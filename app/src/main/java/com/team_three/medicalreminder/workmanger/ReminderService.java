package com.team_three.medicalreminder.workmanger;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.R.attr;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.team_three.medicalreminder.MainActivity;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.databinding.ReminderNotificationDialogBinding;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;

public class ReminderService extends Service {

    private View customAlertDialogView;

    ReminderNotificationDialogBinding binding;

    private WindowManager windowManager;

    private MedicationPOJO myMedicine;

    String key;
    int count;
    final static int CHANNEL_ID = 14;
    final static int FOREGROUND_ID = 7;
    NotificationManager notificationManager;
    String description;
    ReminderWindowManger reminderWindowManger;

    public ReminderService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        myMedicine = fomStringPojo(intent.getStringExtra(MyOneTimeWorkManger.MEDICINE_TAG));
        key = intent.getStringExtra(MyOneTimeWorkManger.KEY_TAG);
        count = intent.getIntExtra(MyOneTimeWorkManger.VALUE_TAG, 1);
        reminderWindowManger = new ReminderWindowManger(getApplicationContext(),myMedicine,key,count,this);

//        lunchCustomDialog();

        notificationChannel();
        startForeground(FOREGROUND_ID, makeNotification());
        reminderWindowManger.setMyWindowManger();

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

/*
    private void lunchCustomDialog() {
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customAlertDialogView = inflater.inflate(R.layout.reminder_notification_dialog, null);
        binding = ReminderNotificationDialogBinding.bind(customAlertDialogView);
        bindView();
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }
        windowManager = (WindowManager) getBaseContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                LAYOUT_FLAG,
                attr.showWhenLocked | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE,
                PixelFormat.TRANSLUCENT);
        windowManager.addView(customAlertDialogView, params);
        Log.i("zoooooz", "getCurrentAlarms: " + count);
    }
*/

    public MedicationPOJO fomStringPojo(String pojoString) {
        Gson gson = new Gson();
        return gson.fromJson(pojoString, MedicationPOJO.class);
    }

    /*private void bindView() {
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
        binding.imgClose.setOnClickListener(v -> windowManager.removeView(customAlertDialogView));
        binding.imgAccept.setOnClickListener(v -> {
            List<Boolean> list = myMedicine.getIsTakenList();
            list.set(getHashMapIndex(), true);
            int n = myMedicine.getLeftNumber() - count;
            if (n < 0)
                n = 0;
            myMedicine.setLeftNumber(n);
            myMedicine.setIsTakenList(list);
            updateMedication(myMedicine);
            stopSelf();
            windowManager.removeView(customAlertDialogView);
        });

        binding.imgSkip.setOnClickListener(v -> {
            stopSelf();
            windowManager.removeView(customAlertDialogView);
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
    }*/

    private Notification makeNotification() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0, intent, 0);
        return new NotificationCompat.Builder(getApplicationContext(),
                String.valueOf(CHANNEL_ID))
                .setSmallIcon(myMedicine.getImageID())
                .setContentTitle("Medication Reminder")
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true).build();
    }

    private void notificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(String.valueOf(CHANNEL_ID),
                    name, importance);
            channel.setDescription(description);

            notificationManager =
                    this.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}