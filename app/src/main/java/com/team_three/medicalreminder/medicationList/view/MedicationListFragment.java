package com.team_three.medicalreminder.medicationList.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.databinding.FragmentMedicationListBinding;


public class MedicationListFragment extends Fragment {
    private FragmentMedicationListBinding binding;
    public MedicationListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMedicationListBinding.inflate(inflater, container, false);
        binding.btnAddMed.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.action_fragment_medication_list_to_fragment_add_Medication);
        });
        return binding.getRoot();
    }
}