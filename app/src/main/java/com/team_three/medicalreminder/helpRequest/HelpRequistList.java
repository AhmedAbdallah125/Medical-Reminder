package com.team_three.medicalreminder.helpRequest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team_three.medicalreminder.databinding.FragmentHelpRequistListBinding;
import com.team_three.medicalreminder.medicationList.view.ActiveListAdapter;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Taker;

import java.util.ArrayList;


public class HelpRequistList extends Fragment {

FragmentHelpRequistListBinding fragmentHelpRequistListBinding;
helpRequestAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHelpRequistListBinding = FragmentHelpRequistListBinding.inflate(inflater,container,false);
        return fragmentHelpRequistListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Taker> medicinesActiveList = new ArrayList<>();
        medicinesActiveList.add(new Taker(1,"das"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(HelpRequistList.this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        fragmentHelpRequistListBinding.recyclerView.setLayoutManager(layoutManager);
        adapter=new helpRequestAdapter(this.getContext(),medicinesActiveList);
        fragmentHelpRequistListBinding.recyclerView.setAdapter(adapter);


    }
}