package com.team_three.medicalreminder.workmanger.takerManager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.team_three.medicalreminder.MainActivity;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.workmanger.ReminderWindowManger;

public class TakerReminderService extends Service {
    private MedicationPOJO myMedicine;

    String key;
    int count;
    String email;
    final static int CHANNEL_ID = 123;
    final static int FOREGROUND_ID = 10;
    NotificationManager notificationManager;
    String description;

    public TakerReminderService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        myMedicine = fomStringPojo(intent.getStringExtra(TakerOneTmeWorkManager.MEDICINE_TAG));
        key = intent.getStringExtra(TakerOneTmeWorkManager.KEY_TAG);
        count = intent.getIntExtra(TakerOneTmeWorkManager.VALUE_TAG, 1);
        email = intent.getStringExtra(TakerOneTmeWorkManager.EMAIL_TAG);
        createNotificationChannel();
        startForeground(FOREGROUND_ID, makeNotification());
        if (Settings.canDrawOverlays(this)) {
            // call window manager
            TakerReminderWindowManager myWorkManager = new TakerReminderWindowManager(
                    this, myMedicine, description, count
            );
            myWorkManager.setWindowManager();

        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

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

    private void createNotificationChannel() {
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

    private MedicationPOJO fomStringPojo(String pojoString) {
        Gson gson = new Gson();
        return gson.fromJson(pojoString, MedicationPOJO.class);
    }
}