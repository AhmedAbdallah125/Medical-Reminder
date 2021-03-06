package com.team_three.medicalreminder.workmanger.medicalremindermanger;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.team_three.medicalreminder.MainActivity;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.model.MedicationPOJO;

public class ReminderService extends Service {

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

        notificationChannel();
        startForeground(FOREGROUND_ID, makeNotification());
        if (Settings.canDrawOverlays(this)) {
            reminderWindowManger = new ReminderWindowManger(getApplicationContext(), myMedicine, key, count);
            reminderWindowManger.setMyWindowManger();
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public MedicationPOJO fomStringPojo(String pojoString) {
        Gson gson = new Gson();
        return gson.fromJson(pojoString, MedicationPOJO.class);
    }

    private Notification makeNotification() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), myMedicine.getImageID());

//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
//                0, intent, 0);
        description = "take " + count + " " + myMedicine.getFormat() + "(s), " +
                myMedicine.getStrength() + myMedicine.getWeight() + myMedicine.getInstruction();
        return new NotificationCompat.Builder(getApplicationContext(),
                String.valueOf(CHANNEL_ID))
                .setSmallIcon(myMedicine.getImageID())
                .setContentText("Schedule for " + key + ", today")

                .setContentTitle("Medication Reminder")
                .setLargeIcon(bitmap)

                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(description))
//                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

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