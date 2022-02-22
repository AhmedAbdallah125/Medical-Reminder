package com.team_three.medicalreminder.patientsList.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.Registeration.view.RegisterFragment;
import com.team_three.medicalreminder.databinding.FragmentPatientListBinding;
import com.team_three.medicalreminder.helpRequest.view.HelpRequistList;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.model.RequestPojo;
import com.team_three.medicalreminder.network.FireBaseNetwork;

import java.util.ArrayList;
import java.util.List;


public class PatientList extends Fragment implements PatientListViewInterface{
    FragmentPatientListBinding binding;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPatientListBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Repository repository =  Repository.getInstance(FireBaseNetwork.getInstance(this.getActivity()),this.getContext());
        ArrayList<RequestPojo> patients = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(PatientList.this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        binding.patientRecylerView.setLayoutManager(layoutManager);
//        sharedPreferences = this.getContext().getSharedPreferences(RegisterFragment.SHAREDfILE, Context.MODE_PRIVATE);

    }

    @Override
    public void loadPatients(List<RequestPojo> patients) {

    }
}