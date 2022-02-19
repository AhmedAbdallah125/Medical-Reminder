package com.team_three.medicalreminder.medicationdrug.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.databinding.FragmentDisplayMedicationDrugBinding;
import com.team_three.medicalreminder.model.MedicationPOJO;

public class DisplayMedicationDrug extends Fragment implements DisplayMedicationDrugViewInterface{
    private FragmentDisplayMedicationDrugBinding binding;
    private MedicationPOJO medication;

    public DisplayMedicationDrug() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDisplayMedicationDrugBinding.inflate(inflater, container, false);
//        binding.imageEdit.setOnClickListener(v->{
//            Navigation.findNavController(v).navigate(R.id.action_displayMedicationDrug_to_fragment_add_Medication);
//        });
//        binding.imageBack.setOnClickListener(v->{
//            Navigation.findNavController(v).navigate(R.id.action_displayMedicationDrug_to_fragment_home);
//        });
//        binding.txtDrugName.setText("Panadol");
//        binding.txtDrugDose.setText("50 g");
//        binding.txtReminderDuration.setText("Every day, take for 10 days");
//        binding.txtReminderDoseTime.setText("8:00 AM\n12:00 AM\n4:00 AM");
//        binding.txtReminderPills.setText("take 1 Pill(s)\ntake 2 Pill(s)\ntake 3 Pill(s)");
//        binding.txtPrescriptionRefill.setText("3 pills left\nRefill reminder: When I have 3 meds remaining");
//        binding.txtHowToUse.setText("After Lunch");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        medication = ConfirmationFragmentArgs
    }

    @Override
    public void showMedication(MedicationPOJO medication) {

    }

    @Override
    public void editMedication(MedicationPOJO medication) {

    }

    @Override
    public void deleteMedication(MedicationPOJO medication) {

    }
}