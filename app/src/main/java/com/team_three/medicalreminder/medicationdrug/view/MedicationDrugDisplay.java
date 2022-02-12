package com.team_three.medicalreminder.medicationdrug.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.team_three.medicalreminder.databinding.ActivityMedicationDrugDisplayBinding;

import java.util.Objects;

public class MedicationDrugDisplay extends AppCompatActivity {
    private ActivityMedicationDrugDisplayBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMedicationDrugDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
}