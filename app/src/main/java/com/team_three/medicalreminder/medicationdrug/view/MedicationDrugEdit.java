package com.team_three.medicalreminder.medicationdrug.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.team_three.medicalreminder.databinding.ActivityMedicationDrugEditBinding;


public class MedicationDrugEdit extends AppCompatActivity {
    private ActivityMedicationDrugEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMedicationDrugEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        binding.edtTxtMedicationName.setText(intent.getStringExtra("name"));
        binding.txtDoseTimes.setText("3 Times a day ");
        binding.txtReminderDoseTime2.setText("8:00 AM\n12:00 AM\n4:00 AM");
        binding.txtReminderPills2.setText("take 1 Pill(s)\ntake 2 Pill(s)\ntake 3 Pill(s)");
        binding.txtReminderDuration2.setText("Every day, take for 10 days");
        binding.txtDoseStrength.setText("50 g");
        binding.txtDrugInstructions.setText("After Lunch");
    }

}