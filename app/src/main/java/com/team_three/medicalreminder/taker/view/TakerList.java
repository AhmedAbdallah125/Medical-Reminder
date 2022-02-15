package com.team_three.medicalreminder.taker.view;

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
import com.team_three.medicalreminder.databinding.FragmentTakerListBinding;
import com.team_three.medicalreminder.medicationList.view.AcrtiveMedsFragment;
import com.team_three.medicalreminder.taker.model.Taker;

import java.util.ArrayList;


public class TakerList extends Fragment {

private FragmentTakerListBinding fragmentTakerListBinding;
TakerListAdabter adabter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentTakerListBinding =FragmentTakerListBinding.inflate(inflater,container,false);
        return fragmentTakerListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Taker> takers = new ArrayList<>();
        takers.add(new Taker(R.drawable.one,"abdo"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(TakerList.this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        adabter =new TakerListAdabter(this.getContext(),takers);
        fragmentTakerListBinding.takerRecyclerView.setLayoutManager(layoutManager);
        fragmentTakerListBinding.takerRecyclerView.setAdapter(adabter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentTakerListBinding =null;
    }
}