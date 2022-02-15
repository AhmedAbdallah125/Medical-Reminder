package com.team_three.medicalreminder.medicationList.view;

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
import com.team_three.medicalreminder.databinding.FragmentAcrtiveMedsBinding;
import com.team_three.medicalreminder.medicationList.model.MedicinesActive;

import java.util.ArrayList;


public class AcrtiveMedsFragment extends Fragment {
    private FragmentAcrtiveMedsBinding fragmentAcrtiveMedsBinding;
    MedsListAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAcrtiveMedsBinding = FragmentAcrtiveMedsBinding.inflate(inflater,container,false);
        return fragmentAcrtiveMedsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<MedicinesActive> medicinesActiveList = new ArrayList<>();
        medicinesActiveList.add(new MedicinesActive(R.drawable.ic_baseline_add_24,"Panadol",1000,"g",4,"pills","left"));
        medicinesActiveList.add(new MedicinesActive(R.drawable.ic_baseline_add_24,"Parastmol",500,"g",6,"pills","left"));
        medicinesActiveList.add(new MedicinesActive(R.drawable.ic_baseline_add_24,"Parastmol",500,"g",6,"pills","left"));
        medicinesActiveList.add(new MedicinesActive(R.drawable.ic_baseline_add_24,"Parastmol",500,"g",6,"pills","left"));
        medicinesActiveList.add(new MedicinesActive(R.drawable.ic_baseline_add_24,"Parastmol",500,"g",6,"pills","left"));
        medicinesActiveList.add(new MedicinesActive(R.drawable.ic_baseline_add_24,"Parastmol",500,"g",6,"pills","left"));
        medicinesActiveList.add(new MedicinesActive(R.drawable.ic_baseline_add_24,"Parastmol",400,"g",6,"pills","left"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(AcrtiveMedsFragment.this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        adapter=new MedsListAdapter(this.getContext(),medicinesActiveList);
        fragmentAcrtiveMedsBinding.activeRecyclerVeiw.setLayoutManager(layoutManager);
        fragmentAcrtiveMedsBinding.activeRecyclerVeiw.setAdapter(adapter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentAcrtiveMedsBinding =null;
    }
}