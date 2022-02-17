package com.team_three.medicalreminder.addmedication.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.databinding.FragmentAddMedicationBinding;

public class AddMedication extends Fragment {
    private FragmentAddMedicationBinding binding;

    public AddMedication() {
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
        binding = FragmentAddMedicationBinding.inflate(inflater, container, false);
        //must check
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_add_Medication_to_fragment_home);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Medication_Type,android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//        binding.spinneMedicationType.setAdapter(adapter);
//        binding.spinneMedicationType.getSelectedItem().toString();
    }
}