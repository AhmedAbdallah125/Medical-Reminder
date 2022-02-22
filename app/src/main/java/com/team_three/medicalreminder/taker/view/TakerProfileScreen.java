package com.team_three.medicalreminder.taker.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team_three.medicalreminder.databinding.FragmentTakerProfileScreenBinding;
import com.team_three.medicalreminder.model.TakerPOJO;

public class TakerProfileScreen extends Fragment {
    FragmentTakerProfileScreenBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =FragmentTakerProfileScreenBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            TakerPOJO taker = bundle.getParcelable("profileData");
            binding.txtFirstNameProfileTaker.setText(taker.getName());
        }

    }
}