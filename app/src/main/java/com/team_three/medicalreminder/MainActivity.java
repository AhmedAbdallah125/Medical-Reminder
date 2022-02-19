package com.team_three.medicalreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;

import com.team_three.medicalreminder.databinding.ActivityMainBinding;
import com.team_three.medicalreminder.homeScreen.view.HomeActivity;
import com.team_three.medicalreminder.model.WorkMangerForRefil;

public class MainActivity extends AppCompatActivity {
private ActivityMainBinding mainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        Data data = new Data.Builder().putInt("number",10).build();
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true).build();
        OneTimeWorkRequest oneTimeWorkRequest =new OneTimeWorkRequest.Builder(WorkMangerForRefil.class).
                setInputData(data)
//                    .setConstraints(constraints)
//                    .setInitialDelay(5, TimeUnit.SECONDS)
                .addTag("download")
                .build();
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);
        mainBinding.button.setOnClickListener(v->{
            Intent intent =new Intent(this, HomeActivity.class);
            startActivity(intent);
        });
    }
}