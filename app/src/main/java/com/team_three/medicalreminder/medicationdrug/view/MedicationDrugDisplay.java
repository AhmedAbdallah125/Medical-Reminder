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
            intent.putExtra("name",binding.txtDrugName.getText());
            startActivity(intent);
        });
        binding.txtDrugName.setText("Panadol");
        binding.txtDrugDose.setText("50 g");
        binding.txtReminderDuration.setText("Every day, take for 10 days");
        binding.txtReminderDoseTime.setText("8:00 AM\n12:00 AM\n4:00 AM");
        binding.txtReminderPills.setText("take 1 Pill(s)\ntake 2 Pill(s)\ntake 3 Pill(s)");
        binding.txtPrescriptionRefill.setText("3 pills left\nRefill reminder: When I have 3 meds remaining");
        binding.txtHowToUse.setText("After Lunch");
    }
}