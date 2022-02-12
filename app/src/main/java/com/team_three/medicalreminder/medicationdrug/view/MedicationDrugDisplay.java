package com.team_three.medicalreminder.medicationdrug.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        binding.imageEdit.setOnClickListener(view->{
            Intent intent = new Intent(this,MedicationDrugEdit.class);
            startActivity(intent);
        });
    }
}