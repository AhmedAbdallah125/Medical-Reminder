package com.team_three.medicalreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.team_three.medicalreminder.Home_Screen.view.HomeActivity;
import com.team_three.medicalreminder.databinding.ActivityMainBinding;
import com.team_three.medicalreminder.medicationdrug.view.MedicationDrugDisplay;
import com.team_three.medicalreminder.medicationdrug.view.MedicationDrugEdit;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mainBinding.button.setOnClickListener(v -> {
            Intent intent = new Intent(this, MedicationDrugDisplay.class);
            startActivity(intent);
        });
    }
}