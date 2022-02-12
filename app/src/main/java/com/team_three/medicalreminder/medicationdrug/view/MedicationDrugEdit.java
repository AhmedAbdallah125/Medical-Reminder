package com.team_three.medicalreminder.medicationdrug.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.databinding.ActivityMedicationDrugEditBinding;

import java.util.Objects;

public class MedicationDrugEdit extends AppCompatActivity {
    private ActivityMedicationDrugEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMedicationDrugEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
        getSupportActionBar().setTitle("Edit Medication");
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}