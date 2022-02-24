package com.team_three.medicalreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.team_three.medicalreminder.dataBase.ConcreteLocalClass;
import com.team_three.medicalreminder.databinding.ActivityMainBinding;
import com.team_three.medicalreminder.homeScreen.view.HomeActivity;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.workmanger.MyPeriodicWorkManger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    private Handler handler;
    private Thread thread;

    // for work manager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
//        Data data = new Data.Builder().putInt("number",10).build();
//        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
//                .setRequiresCharging(true).build();
//        OneTimeWorkRequest oneTimeWorkRequest =new OneTimeWorkRequest.Builder(WorkMangerForRefil.class).
//                setInputData(data)
////                    .setConstraints(constraints)
////                    .setInitialDelay(5, TimeUnit.SECONDS)
//                .addTag("download")
//                .build();
//        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);
        Intent intent = new Intent(this, HomeActivity.class);
        handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                thread.interrupt();
                startActivity(intent);
                finish();
            }
        };
//        MediaPlayer ring= MediaPlayer.create(MainActivity.this,R.raw.ring);
//        ring.start();
        SplashScreen();
//        remind(1);
//        setWorkTimer();
    }

    private void SplashScreen(){
        mainBinding.lottie.setAnimation(R.raw.splash);
        thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2900);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        };
        thread.start();
    }


    // for work manager
}