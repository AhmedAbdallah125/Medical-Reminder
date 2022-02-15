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
import com.team_three.medicalreminder.databinding.FragmentInactiveMedsBinding;
import com.team_three.medicalreminder.medicationList.model.MedicinesActive;

import java.util.ArrayList;


public class InactiveMedsFragment extends Fragment {
    FragmentInactiveMedsBinding fragmentInactiveMedsBinding;
    InactiveListAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentInactiveMedsBinding = FragmentInactiveMedsBinding.inflate(inflater,container,false);
        return fragmentInactiveMedsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<MedicinesActive> medInactiveList =new ArrayList<>();
        medInactiveList.add(new MedicinesActive(R.drawable.ic_baseline_add_24,"Panadol",
                1000,"g",4,"pills","left"));
        medInactiveList.add(new MedicinesActive(R.drawable.ic_baseline_add_24,"Panadol",
                1000,"g",4,"pills","left"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(InactiveMedsFragment.this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        adapter =new InactiveListAdapter(this.getContext(),medInactiveList);
        fragmentInactiveMedsBinding.inActiveRecyclerView.setLayoutManager(layoutManager);
        fragmentInactiveMedsBinding.inActiveRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentInactiveMedsBinding=null;
    }
}